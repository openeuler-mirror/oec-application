#!/usr/bin/env python3
# coding: utf-8
# Copyright (c) 2022 Huawei Technologies Co., Ltd.
# oec-hardware is licensed under the Mulan PSL v2.
# You can use this software according to the terms and conditions of the Mulan PSL v2.
# You may obtain a copy of Mulan PSL v2 at:
#     http://license.coscl.org.cn/MulanPSL2
# THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
# PURPOSE.
# See the Mulan PSL v2 for more details.
# Author: @weijihui
# Create: 2022-06-17
# Modified: @meitingli
# Desc: Submit oec-hardware job automatically on compass-ci

import os
import re
from random import randint
from .common import *


class OechSubmit:
    def __init__(self, card_info_hash, lab_path, yaml_content, group_id):
        self.card_info_hash = card_info_hash
        self.lab_path = lab_path
        self.yaml_content = yaml_content
        self.group_id = group_id

    def deal_card_info(self, card_info_list):
        """
        处理板卡信息，把四元组提取出来得到一个hash, 方便后面对比
            {
                vendorID-deviceID-ssID-svID: {
                    'boardModel': 'SP580',
                    'deviceID': '1822',
                    'type': 'ethernet',
                    'ssID': 'd136',
                    'svID': '19e5',
                    'vendorID': '19e5',
                    }
                ...
            }
        """
        card_ids_hash = {}
        for card in card_info_list:
            if not card.get("type"):
                card["type"] = "no_type"
            else:
                card["type"] = card["type"].replace(" ", "-")
            if not card.get("boardModel"):
                card["boardModel"] = "no_boardModel"
            else:
                card["boardModel"] = card["boardModel"].replace(" ", "-")
            vendor_id = card.get("vendorID")
            device_id = card.get("deviceID")
            sv_id = card.get("svID")
            ss_id = card.get("ssID")

            card_id = "{}-{}-{}-{}".format(vendor_id, device_id, sv_id, ss_id)
            card_info_collect = "type:[{}] boardModel:[{}] card_id:[{}]".format(
                card["type"], card["boardModel"], card_id)
            if all([vendor_id, device_id, sv_id, ss_id]):
                if card_id not in card_ids_hash:
                    card_ids_hash[card_id] = card
                else:
                    print("[WARNING]: this card reappears {}".format(
                        card_info_collect))
        return card_ids_hash

    def read_lab_board(self):
        """
        把lab下所有box的信息，统计出来，找到每个box的所有四元组
        return:
        {'taishan200-2280-2s64p-128g--a116': dict_keys(['10df-e200-e280-10df', '15b3-1007-0006-15b3']),
        'taishan200-2280-2s64p-128g--a117': dict_keys(['10df-e200-e280-10df', '15b3-1007-0006-15b3']),
        'taishan200-2280-2s64p-256g--a110': dict_keys(['19e5-1822-d136-19e5', '8086-159b-0003-8086']),
        'taishan200-2280-2s64p-256g--a111': dict_keys(['19e5-1822-d136-19e5', '8086-159b-0003-8086']),
        'taishan200-2280-2s64p-256g--a119': dict_keys(['19e5-1822-d136-19e5', '8086-159b-0003-8086'])}
        """
        board_files = os.listdir(os.path.join(self.lab_path, "devices"))
        box_board_hash = {}
        for board_file in board_files:
            if not re.search("^taishan200|^2288hv5", board_file):
                print("[WARNING]: Skip read lab board file: {}".format(board_file))
                continue
            board_file_path = os.path.join(
                self.lab_path, "devices", board_file)
            board_file_content = read_yaml(board_file_path)
            board_hash = self.deal_card_info(board_file_content["cards"])
            box_board_hash[board_file] = board_hash.keys()
        return box_board_hash

    def _get_designated_box(self, card_id, card_info, card_with_box):
        for role in ["server", "client"]:
            box = card_info.get(role)
            if box:
                card_with_box[card_id][role] = box
                card_with_box[card_id]["env_ready"] = True
        return card_with_box

    @staticmethod
    def _get_useful_client(card_id, card_info, card_with_box, box_board_hash):
        """
        非ethernet卡测试, 只需要找一台testbox符合的板卡即可, client/server端在一台机器上运行
        如果配置文件中指定testbox机器，会使用指定机器进行测试
        """
        box = card_info.get("testbox")
        if box:
            card_with_box[card_id]["testbox"] = box
        else:
            useful_box = []
            for box, box_card_ids_list in box_board_hash.items():
                if card_id in box_card_ids_list:
                    useful_box.append(box)

            if len(useful_box) == 0:
                print("[ERROR]: The lab env cannot find corresponding machine to test!")
                card_with_box[card_id]["env_ready"] = False
                return card_with_box
            elif len(useful_box) == 1:
                num = 0
            else:
                num = randint(0, len(useful_box)-1)
            card_with_box[card_id]["testbox"] = useful_box[num]

        card_with_box[card_id]["cluster"] = "cs-localhost"
        card_with_box[card_id]["env_ready"] = True
        return card_with_box

    def choose_box(self, card_ids_hash, box_board_hash):
        """
        return:
        {
        '19e5-1822-d136-19e5': {'client': 'taishan200-2280-2s64p-256g--a119',
                                'type': 'ethernet',
                                'boardModel': 'SP331',
                                'server': 'taishan200-2280-2s64p-256g--a111',
                                'env_ready': True}
        }
        """
        card_with_box = {}
        for card_id, card_info in card_ids_hash.items():
            card_type = card_info["type"]
            card_with_box.setdefault(card_id, dict())
            card_with_box[card_id]["type"] = card_type
            card_with_box[card_id]["boardModel"] = card_info["boardModel"]
            card_with_box[card_id]["driverLink"] = card_info.get("driverLink")

            if card_type == "ethernet" or card_type == "infiniband":
                # 网卡测试需要指定server/client，所以使用指定机器测试
                card_with_box = self._get_designated_box(
                    card_id, card_info, card_with_box)
            else:
                # 非网卡测试找一台作为client端进行测试,
                card_with_box = self._get_useful_client(
                    card_id, card_info, card_with_box, box_board_hash)
        return card_with_box

    def get_submit_args(self):
        """
        这里组装submit命令，关于submit命令可以参考lkp-tests
        """
        submit_args = ""
        for arg in ["os", "os_version", "os_arch"]:
            if self.card_info_hash.get(arg):
                submit_args += " {}={}".format(arg, self.card_info_hash[arg])
        return submit_args

    def _submit_job(self, job_yaml_path, now_time, yaml_name, submit_args, submit_output=False):
        job_yaml_path = os.path.expanduser(job_yaml_path)
        submit_cmd = "submit " + job_yaml_path + submit_args

        if submit_output:
            submit_cmd += " -o {}".format(submit_output)
        exitcode, output = exec_cmd(submit_cmd)
        if exitcode == 0:
            job_id = re.findall('got job id=(\\w+\\.\\d+)', output)
            if job_id and len(job_id) > 0:
                print(
                    "[INFO]: submit job success {}/{}.yaml".format(now_time, yaml_name))
                print("[INFO]: >>>>>> job id: {} >>>>>>".format(
                    ", ".join(job_id)))
                return job_id

        if submit_output:
            print("[INFO]: submit job {}/{}.yaml".format(now_time, yaml_name))
            print("[INFO]: <<<<<< output info: {} >>>>>>".format(output))
        else:
            print("[ERROR]: submit job fail for {}/{}.yaml".format(now_time, yaml_name))
            print("[ERROR]: <<<<<< fail info: {} >>>>>>".format(output))
        return False

    def oech_task(self, card_with_box, submit_args, submit_output):
        mkdir("./{}".format(self.group_id))
        for card_id, cluster_info in card_with_box.items():
            if not cluster_info["env_ready"]:
                continue
            card_type = cluster_info["type"]
            board_model = cluster_info["boardModel"]
            card_info_collect = "type:[{}] boardModel:[{}] card_id:[{}]".format(
                card_type, board_model, card_id)

            if not cluster_info.get("env_ready"):
                cluster_info["env_ready"] = "not find test env"
                print("[WARNING]: this card({}) can not find test env".format(
                    card_info_collect))
                continue

            if not cluster_info.get('cluster'):
                server = cluster_info["server"]
                client = cluster_info["client"]
                cluster = "-".join(["cs-s1", server.split("--")[-1],
                                    "c1", client.split("--")[-1]])
                self.yaml_content["cluster"] = cluster
                self.yaml_content["cluster_spec"] = {"ip0": 1,
                                                     "nodes": {server: {"roles": ["server"]},
                                                               client: {"roles": ["client"]}}}
                self.yaml_content["testbox"] = client
            else:
                cluster = cluster_info.get('cluster')
                self.yaml_content["cluster"] = cluster
                self.yaml_content["testbox"] = cluster_info['testbox']
            yaml_name = "{}-{}-{}-{}".format(card_type,
                                             board_model, card_id, cluster)
            self.yaml_content["group_id"] = self.group_id
            self.yaml_content["test_card_id"] = card_id
            self.yaml_content["test_card_type"] = card_type
            self.yaml_content["test_card_name"] = board_model
            self.yaml_content["driverLink"] = cluster_info["driverLink"]
            job_yaml_path = "./{}/{}.yaml".format(self.group_id, yaml_name)
            write_yaml(self.yaml_content, job_yaml_path)
            job_id = self._submit_job(job_yaml_path, self.group_id,
                                      yaml_name, submit_args, submit_output)
            card_with_box[card_id]["job_id"] = job_id
        write_yaml(card_with_box, "./{}/{}".format(self.group_id, "submit_result"))