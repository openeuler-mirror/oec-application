# !/usr/bin/python3
# -*- coding:UTF-8 -*-

import base64
import sys
import os
import yaml
import json
import re
import requests
import time
import copy
from collections import defaultdict, OrderedDict
# import requests.adapters

from xml.etree.ElementTree import parse

srcOepkgsNum = 0
allFileNum = 0
allFileList = []  # 存放 当前路径 以及当前路径的子路径 下的所有文件
sigInfolist = []
allYamlList = []
filter_char = []
allYamldata = []
allYamldata_branch = []
allYamldata_tag = []
src_code_up = []
src_code_is = []
yaml_error = []
rpm_info = {}
d = defaultdict(list)
d_oepkg = defaultdict(list)
dict_list = defaultdict(list)
ones = {1: "one", 2: "two", 3: "three", 4: "four",
        5: "five", 6: "six", 7: "seven", 8: "eight",
        9: "nine", 0: "zero"}


def getAllFilesInPath(path):
    global allFileNum
    curPathDirList = []  # 当前路径下的所有文件夹
    files = os.listdir(path)  # 返回当前路径下的所有文件和文件夹
    for f in files:
        if os.path.isdir(path + "/" + f):
            if f[0] == ".":
                pass  # 排除隐藏文件夹
            else:
                curPathDirList.append(f)  # 添加非隐藏文件夹
        if os.path.isfile(path + "/" + f):
            if f[-8:] == ".src.rpm":
                allFileList.append(os.path.abspath(path + "/" + f))  # 添加文件
                allFileNum = allFileNum + 1
                # 总文件数+1
            if f[-5:] == ".yaml" and f != "sig-info.yaml":
                yaml_name = f[:-5]
                allYamlList.append(yaml_name)
                # allYamldata.append(os.path.abspath(path + "/" + f))

    for dl in curPathDirList:
        getAllFilesInPath(path + "/" + dl)  # 递归获取当前目录下的文件夹内的文件


# 获取rpm信息，拿到name和description
def shell_cmd(rpm_key, path):
    rpm_info = os.popen("rpm -qi {}".format(path))
    for line in rpm_info.read().splitlines():
        rpm_value = line.split(":")
        if rpm_value[0].strip() == rpm_key:
            return rpm_value[1].strip()


if __name__ == '__main__':
    # 读取rpm包名存入列表内
    # rpm_pkg_path = input("请输入要获取的rpm包目录：")
    # api_token = input("请输入api的token：")
    if len(sys.argv) != 2:
        sys.exit()

    # requests.adapters.DEFUALT_RETRYS = 10
    headers = {"Content-Type":"application/json;charset=UTF-8","connection":"close"}
    #rpm_pkg_path = "/srv/rpm/pub/openeuler-20.03-LTS-SP1"
    rq_header = "curl -X POST --header 'Content-Type: application/json;charset=UTF-8'"
    real_path = os.path.dirname(os.path.realpath(__file__)) + "/"
    api_token = "613df0cd941abfdfc90f36445e98736d"
    robot_token = "c951fee688f4b037d27602d7461b81fc"
    print("yaml文件的名字")
    getAllFilesInPath("./oepkgs-management/sig")
    print(allYamlList)
    with open("sp3_yaml.json", "r") as f:
        d = json.load(f)
    print("---------------")
    print(len(d))
    tag_num = 0
    add_yaml = 0
    for yaml_modify in d:
        tag_num = tag_num + 1
        print("------ branch {} 已添加 -----".format(tag_num))
        # if yaml_modify not in allYamlList:
        yaml_file = ""
        if yaml_modify[0].isdigit():
            for i, item in enumerate(yaml_modify):
                if not item.isdigit() and item == "-":
                    yaml_file = yaml_file + yaml_modify[i + 1:]
                    # yaml_file = yaml_modify[i + 1:] + "-" + yaml_modify[:i]
                    break
                elif not item.isdigit() and item != "-":
                    yaml_file = yaml_file + yaml_modify[i:]
                    # yaml_file = yaml_modify[i + 1:] + yaml_modify[:i]
                    break
                else:
                    yaml_file = yaml_file + ones[int(item)] + "-"
        elif "+" in yaml_modify:
            yaml_file = yaml_modify.replace("+", "plus")
        else:
            yaml_file = yaml_modify
        print("----- yaml_modify ----")
        print("*****{}****".format(yaml_modify))

        if yaml_file not in allYamlList:
            src_code_is.append(yaml_file)
            print("-------- out of yaml file --------")
            print(src_code_is)
            continue
        # elif yaml_file in allFileList and yaml_file.lower() not in allFileList:
        # elif yaml_file in allFileList and yaml_file.lower() in allFileList:
        #     yaml_file = yaml_file.lower()
        os.system("git clone 'https://gitee.com/src-oepkgs/{0}.git';".format(yaml_file))
        if not os.path.exists(real_path + yaml_file):
            yaml_file = yaml_file.lower()
            os.system("git clone 'https://gitee.com/src-oepkgs/{0}.git';".format(yaml_file))
            if not os.path.exists(real_path + yaml_file):
                allYamldata.append(yaml_file)
                #print("------ allYamldata {} 已添加 -----".format(yaml_file))
                continue
        # time.sleep()
        os.chdir(os.getcwd() + "/" + yaml_file)
        commit_id = os.popen("git tag").read().strip()
        
        repo_branch = os.popen("git branch").read().strip()
        if repo_branch == "":
            #print("----- {} branch 不存在 -----".format(yaml_file))
            allYamldata_branch.append(yaml_file)
            os.chdir(os.path.pardir)
            os.system("rm -rf {0}".format(yaml_file))
            continue
        if commit_id == "":
            d_oepkg[yaml_file]=d[yaml_modify]
            os.chdir(os.path.pardir)
            os.system("rm -rf {0}".format(yaml_file))
            add_yaml = add_yaml + 1
            print("----- tag 不存在 -----")
            print("------ {0} branch {1} 已添加 -----".format(yaml_file,add_yaml))
        else:
            tag_list = commit_id.split("\n")
            if "20.03-LTS-SP3" in [ i[:13] for i in tag_list]:
                print("----- {} tag 已存在 -----".format(yaml_file))
                allYamldata_tag.append(yaml_file)
                os.chdir(os.path.pardir)
                os.system("rm -rf {0}".format(yaml_file))
                continue
            else:
                d_oepkg[yaml_file]=d[yaml_modify]
                os.chdir(os.path.pardir)
                os.system("rm -rf {0}".format(yaml_file))
                add_yaml = add_yaml + 1
                print("----- 20.03-LTS-SP3 存在 -----")
                print("------ {0} branch {1} 已添加 -----".format(yaml_file,add_yaml))
        
    with open("yaml_sp3.json", "w") as f:
        f.write(json.dumps(d_oepkg))
    print("--------- yaml list -----------")
    print(len(src_code_is))
    print(src_code_is)
    print("--------- git list -----------")
    print(len(allYamldata))
    print(allYamldata)
    print("--------- branch list -----------")
    print(len(allYamldata_branch))
    print(allYamldata_branch)
    print("--------- tag list -----------")
    print(len(allYamldata_tag))
    print(allYamldata_tag)
    print("--------- d_oepkg list -----------")
    print(len(d_oepkg))
    #print(allYamldata_tag)
    print("---- end ----")
    sys.exit()
