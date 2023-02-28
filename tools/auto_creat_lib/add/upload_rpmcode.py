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
# Author: @zhangyinuo
# Create: 2023-02-27
# Desc: Submit oec-hardware job automatically on compass-ci

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
import logging
from xml.etree.ElementTree import parse

srcOepkgsNum = 0
allFileNum = 0
allFileList = []  # 存放 当前路径 以及当前路径的子路径 下的所有文件
sigInfolist = []
allYamlList = []
filter_char = []
allYamldata = []
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
    headers = {"Content-Type":"application/json;charset=UTF-8","Connection":"close"}
    rpm_pkg_path = "/srv/rpm/pub/openeuler-20.03-LTS-SP1"
    rq_header = "curl -X POST --header 'Content-Type: application/json;charset=UTF-8'"
    real_path = os.path.dirname(os.path.realpath(__file__)) + "/"
    api_token = "c4a7f2254bd58885a9c6fa80cbd0b7dc"
    robot_token = "c951fee688f4b037d27602d7461b81fc"
    with open("yaml_sp3.json", "r") as f:
        d = json.load(f)
    print(len(d))
    #sys.exit()
    tag_num = 0
    for yaml_file in d:
        module_name = d[yaml_file]
        print(module_name)
        rpm_dict = {}
        version_set = set()
        for rpm_path in module_name:
            rpm_version = shell_cmd("Version", rpm_path)
            print(rpm_version)
            version_set.add(rpm_version)
            rpm_dict[rpm_version] = rpm_path
        version_list = list(version_set)
        version_list.sort()
        print(version_list)
        print(rpm_dict)
        os.system("curl -X DELETE --header 'Content-Type: application/json;charset=UTF-8' 'https://gitee.com/api/v5/repos/src-oepkgs/{}/branches/{}/setting?access_token={}'".format(yaml_file, sys.argv[1], api_token))
        for rpm_version in version_list:
            rpm_path = rpm_dict[rpm_version]
            if sys.argv[1] == "master":
                os.system("git clone 'https://gitee.com/src-oepkgs/{0}.git';".format(yaml_file))
            else:
                os.system("git clone -b {1} 'https://gitee.com/src-oepkgs/{0}.git';".format(yaml_file,
                                                                                            sys.argv[1]))
            if not os.path.exists(real_path + yaml_file):
                print("route is {}".format(os.path.exists(real_path + yaml_file)))
                break
            os.chdir(os.getcwd() + "/" + yaml_file)
            print(os.listdir("./"))


            print(os.getcwd())
            os.system(
                "rm -rf *;rpm2cpio {0} | cpio -div;git add .;git commit -m '{1}';git push".format(rpm_path,
                                                                                                  rpm_version)))
            commit_id = os.popen("git rev-parse HEAD").read().strip()
            print(commit_id)
            os.chdir(os.path.pardir)
            os.system("rm -rf {0}".format(yaml_file))
            logging.info("-------- pwd --------")
            print(os.getcwd())
            logging.info("------- sha value ------")
            print(commit_id)
            if sys.argv[1] == "master":
                os.system(
                    "{} 'https://gitee.com/api/v5/repos/src-oepkgs/{}/tags' -d '{{\"access_token\":\"{}\",\"refs\":\"{}\",\"tag_name\":\"{}\"}}'".format(
                        rq_header, yaml_file, api_token, commit_id, "20.03-LTS-SP1" + "-v" + rpm_version.replace("^",".").replace("~",".")))
            else:
                os.system(
                    "{} 'https://gitee.com/api/v5/repos/src-oepkgs/{}/tags' -d '{{\"access_token\":\"{}\",\"refs\":\"{}\",\"tag_name\":\"{}\"}}'".format(
                        rq_header, yaml_file, api_token, commit_id, sys.argv[1][10:] + "-v" + rpm_version.replace("^",".").replace("~",".")))
            logging.info("------- 库名 ------")
            print(yaml_file)
        tag_num = tag_num + 1
        os.system("curl -X PUT --header 'Content-Type: application/json;charset=UTF-8' 'https://gitee.com/api/v5/repos/src-oepkgs/{}/branches/{}/protection' -d '{{\"access_token\":\"{}\"}}'".format(yaml_file, sys.argv[1], api_token))
        src_code_up.append(yaml_file)
        print(tag_num)
