#!/usr/bin/env python3
# coding: utf-8
"""
oech任务自动提交脚本
"""

import yaml
import json
import os
import re
import sys
import time
import argparse
import subprocess
from pprint import pprint


def mkdir(path):
    os.makedirs(path, exist_ok=True)


def read_yaml(yaml_path):
    with open(yaml_path) as f:
        yaml_content = yaml.safe_load(f)
    return yaml_content


def write_yaml(yaml_content, yaml_path):
    with open(yaml_path, "w") as f:
        yaml.safe_dump(yaml_content, f)


def read_json(json_path):
    with open(json_path) as f:
        json_content = json.load(f)
    return json_content


def deal_card_info(card_info_list):
    """
    处理板卡信息，把四元组提取出来得到一个hash, 方便后面对比
        {
            vendorID-deviceID-ssID-svID: {
                'boardModel': 'SP580',
                'deviceID': '1822',
                'name': 'ethernet',
                'ssID': 'd136',
                'svID': '19e5',
                'vendorID': '19e5',
                }
            ...
        }
    """
    card_ids_hash = {}
    for card in card_info_list:
        if not card.get("name"):
            card["name"] = "no_name"
        else:
            card["name"] = card["name"].replace(" ", "-")
        if not card.get("boardModel"):
            card["boardModel"] = "no_boardModel"
        else:
            card["boardModel"] = card["boardModel"].replace(" ", "-")
        vendor_id = card.get("vendorID")
        device_id = card.get("deviceID")
        sv_id = card.get("svID")
        ss_id = card.get("ssID")

        card_id = "{}-{}-{}-{}".format(vendor_id, device_id, sv_id, ss_id)
        card_info_collect = "name:[{}] boardModel:[{}] card_id:[{}]".format(card["name"], card["boardModel"], card_id)
        if all([vendor_id, device_id, sv_id, ss_id]):
            if card_id not in card_ids_hash:
                card_ids_hash[card_id] = card
            else:
                print("[WARNING]: this card reappears {}".format(card_info_collect))
        else:
            print("[WARNING]: lack of information for this card {}".format(card_info_collect))
    return card_ids_hash


def read_lab_board(path):
    """
    把lab下所有box的信息，统计出来，找到每个box的所有四元组
    return:
    {'taishan200-2280-2s64p-128g--a116': dict_keys(['10df-e200-e280-10df', '15b3-1007-0006-15b3']),
     'taishan200-2280-2s64p-128g--a117': dict_keys(['10df-e200-e280-10df', '15b3-1007-0006-15b3']),
     'taishan200-2280-2s64p-256g--a110': dict_keys(['19e5-1822-d136-19e5', '8086-159b-0003-8086']),
     'taishan200-2280-2s64p-256g--a111': dict_keys(['19e5-1822-d136-19e5', '8086-159b-0003-8086']),
     'taishan200-2280-2s64p-256g--a119': dict_keys(['19e5-1822-d136-19e5', '8086-159b-0003-8086'])}
    """
    board_files = os.listdir(os.path.join(path, "devices"))
    box_board_hash = {}
    for board_file in board_files:
        if not re.search("^taishan200|^2288hv5", board_file):
            print("[WARNING]: Skip read lab board file: {}".format(board_file))
            continue
        board_file_path = os.path.join(path, "devices", board_file)
        board_file_content = read_yaml(board_file_path)
        board_hash = deal_card_info(board_file_content["cards"])
        box_board_hash[board_file] = board_hash.keys()
    return box_board_hash


def choose_box(card_ids_hash, box_board_hash):
    """
    return:
    {
     '19e5-1822-d136-19e5': {'client': 'taishan200-2280-2s64p-256g--a119',
                             'name': 'ethernet',
                             'boardModel': 'SP331',
                             'test_para': 'y',
                             'server': 'taishan200-2280-2s64p-256g--a111',
                             'env_ready': True}}
    """
    card_with_box = {}
    used_box = []
    usable_box = list(box_board_hash.keys())

    # 第一轮找ethernet卡相同的两个机器组集群
    for card_id, card_info in card_ids_hash.items():
        card_with_box.setdefault(card_id, {})
        card_with_box[card_id]["name"] = card_info["name"]
        card_with_box[card_id]["boardModel"] = card_info["boardModel"]
        card_with_box[card_id]["test_para"] = card_info.get("test_para", "no_para")
        card_with_box[card_id]["driverlink"] = card_info.get("driverlink")

        # 如果测试的板卡指定了两个测试机，直接使用这两个测试机
        for role in ["server", "client"]:
            if card_info.get(role):
                box = card_info.get(role)
                card_with_box[card_id][role] = box
                used_box.append(box)
                usable_box.remove(box)
        
        if card_with_box[card_id].get("server") and card_with_box[card_id].get("client"):
            continue

        if card_info["name"] != "ethernet":
            continue

        for box, box_card_ids_list in box_board_hash.items():
            if box not in used_box and card_id in box_card_ids_list:
                if not card_with_box[card_id].get("server"):
                    card_with_box[card_id]["server"] = box
                elif not card_with_box[card_id].get("client"):
                    card_with_box[card_id]["client"] = box
                else:
                    # server和client端找到，这个卡的集群完成，不必再找这个卡
                    break
                used_box.append(box)
                usable_box.remove(box)

    # 第二轮找非ethernet卡的机器组集群，只找一台给client端测试
    for card_id, card_info in card_ids_hash.items():
        if card_info["name"] == "ethernet":
            continue
        for box, box_card_ids_list in box_board_hash.items():
            # 这个测试机没有被使用，并且需要测试的板卡在这个测试机上
            if box not in used_box and card_id in box_card_ids_list:
                if not card_with_box[card_id].get("client"):
                    card_with_box[card_id]["client"] = box
                    used_box.append(box)
                    usable_box.remove(box)
                else:
                    break

    # 第三轮，非ethernet卡的机器组集群只有一台client，再找一台机器组成集群
    for card_id, cluster_info in card_with_box.items():
        if len(usable_box) == 0:
            break
        elif cluster_info["name"] == "ethernet":
            if cluster_info.get("client") and cluster_info.get("server"):
                card_with_box[card_id]["env_ready"] = True
            continue
        elif cluster_info.get("client"):
            cluster_info["env_ready"] = True
            if not cluster_info.get("server"):
                box = usable_box.pop()
                used_box.append(box)
                cluster_info["server"] = box

    return card_with_box


def exec_cmd(cmd):
    exitcode, output = subprocess.getstatusoutput(cmd)
    return exitcode, output


def get_submit_args(card_info_hash):
    """
    这里组装submit命令，关于submit命令可以参考lkp-tests
    """
    submit_args = ""
    for arg in ["os", "os_version", "os_arch"]:
        if card_info_hash.get(arg):
            submit_args += " {}={}".format(arg, card_info_hash[arg])
    return submit_args


def submit_job(job_yaml_path, now_time, yaml_name, submit_args, submit_output=False):
    print("\n")
    job_yaml_path = os.path.expanduser(job_yaml_path)
    submit_cmd = "submit " + job_yaml_path + submit_args
    if submit_output:
        submit_cmd += " -o {}".format(submit_output)
    exitcode, output = exec_cmd(submit_cmd)
    if exitcode == 0:
        job_id = re.findall('got job id=(\\w+\\.\\d+)', output)
        if job_id and len(job_id) == 2:
            print("[INFO]: submit job success {}/{}.yaml".format(now_time, yaml_name))
            print("[INFO]: >>>>>> job id: {}, {} >>>>>>".format(job_id[0], job_id[1]))
            return job_id
    if submit_output:
        print("[INFO]: submit job {}/{}.yaml".format(now_time, yaml_name))
        print("[INFO]: <<<<<< output info: {} >>>>>>".format(output))
    else:
        print("[ERROR]: submit job fail for {}/{}.yaml".format(now_time, yaml_name))
        print("[ERROR]: <<<<<< fail info: {} >>>>>>".format(output))
    return False


def oech_task(yaml_content, card_with_box, submit_args, submit_output):
    now_time = time.strftime("%Y-%m-%d-%H-%M-%S", time.localtime())
    mkdir("./{}".format(now_time))
    for card_id, cluster_info in card_with_box.items():
        card_name = cluster_info["name"]
        board_model = cluster_info["boardModel"]
        card_info_collect = "name:[{}] boardModel:[{}] card_id:[{}]".format(card_name, board_model, card_id)

        if not cluster_info.get("env_ready"):
            cluster_info["env_ready"] = "not find test env"
            print("[WARNING]: this card({}) can not find test env".format(card_info_collect))
            continue

        server = cluster_info["server"]
        client = cluster_info["client"]
        cluster = "-".join(["cs-s1", server.split("--")[-1], "c1", client.split("--")[-1]])

        yaml_name = "{}-{}-{}-{}".format(card_name, board_model, card_id, cluster)
        yaml_content["testbox"] = server
        yaml_content["group_id"] = now_time
        yaml_content["test_card_id"] = card_id
        yaml_content["test_card_name"] = card_name
        yaml_content["test_para"] = cluster_info["test_para"]
        yaml_content["driverlink"] = cluster_info["driverlink"]
        yaml_content["cluster"] = cluster
        yaml_content["cluster_spec"] = {"ip0": 1,
                                        "nodes": {server: {"roles": ["server"]},
                                                  client: {"roles": ["client"]}}}
        job_yaml_path = "./{}/{}.yaml".format(now_time, yaml_name)
        write_yaml(yaml_content, job_yaml_path)
        job_id = submit_job(job_yaml_path, now_time, yaml_name, submit_args, submit_output)
        card_with_box[card_id]["job_id"] = job_id
    write_yaml(card_with_box, "./{}/{}".format(now_time, "submit_result"))


def main(oech_yaml_path, lab_path, card_conf_path, submit_output=False):
    yaml_content = read_yaml(oech_yaml_path)
    card_info_hash = read_json(card_conf_path)
    card_ids_hash = deal_card_info(card_info_hash["card_info"])

    submit_args = get_submit_args(card_info_hash)
    box_board_hash = read_lab_board(lab_path)
    card_with_box = choose_box(card_ids_hash, box_board_hash)
    oech_task(yaml_content, card_with_box, submit_args, submit_output)


def check_args(arg_name, cmd_arg):
    if cmd_arg:
        return cmd_arg
    else:
        print("[ERROR]: {} is not entered.".format(arg_name))
        sys.exit()


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="oech任务自动提交脚本")
    parser.add_argument('-j', '--job_yaml', type=str, required=False, help='oech job yaml')
    parser.add_argument('-l', '--lab_path', type=str, required=False, help='test lab which include devices dir')
    parser.add_argument('-c', '--card_conf', type=str, required=False, help='json file for all card conf')
    parser.add_argument('-o', '--submit_output', type=str, required=False, help='submit job for get job yaml but not post to server')
    args = parser.parse_args()

    # oech的job yaml
    job_yaml = check_args('job_yaml', args.job_yaml)
    # 测试机环境的库
    lab_path = check_args('lab_path', args.lab_path)
    # 板卡文件
    card_conf = check_args('card_conf', args.card_conf)

    main(job_yaml, lab_path, card_conf, args.submit_output)
