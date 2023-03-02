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
# Desc:Read the path of the software repository and write it into the JSON file

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
from xml.etree.ElementTree import parse
import logging

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
d = defaultdict(list)
d_oepkg = defaultdict(list)
dict_list = defaultdict(list)


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
    for dl in curPathDirList:
        getAllFilesInPath(path + "/" + dl)


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
    rpm_pkg_path = "/srv/rpm/testing/openeuler-20.03-LTS-SP3"
    # 取rpm包总数和rpm文件绝对路径
    getAllFilesInPath(rpm_pkg_path)
    logging.info("当前路径下的总文件数 =", allFileNum)
    for rpm_path in allFileList:
        rpm_file = shell_cmd("Name", rpm_path)  # 获取rpm信息
        d[rpm_file].append(rpm_path)
    logging.info('--------rpm file-----------')
    # 取一个版本的的所有包的信息以字典形式存入json文件
    with open("sp3_yaml.json", "w") as fw:
        fw.write(json.dumps(d))
