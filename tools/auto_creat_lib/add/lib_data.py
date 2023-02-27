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
src_code_up = []
src_code_is = []
yaml_error = []
rpm_info = {}
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
    rpm_pkg_path = "/srv/rpm/testing/openeuler-20.03-LTS-SP3"
    rq_header = "curl -X POST --header 'Content-Type: application/json;charset=UTF-8'"
    real_path = os.path.dirname(os.path.realpath(__file__)) + "/"
    api_token = "68a19dd4a3bd83ce89b3ce0e3ca979d1"
    robot_token = "c951fee688f4b037d27602d7461b81fc"
    # 取rpm包总数和rpm文件绝对路径
    getAllFilesInPath(rpm_pkg_path)
    print("当前路径下的总文件数 =", allFileNum)
    for rpm_path in allFileList:
        rpm_file = shell_cmd("Name", rpm_path)  # 获取rpm信息
        d[rpm_file].append(rpm_path)
    # 获取src-oepkgs上已经存在的库，通过yaml文件获取
    #getAllFilesInPath("./oepkgs-management/sig")
    print("------- yaml file ---------")
    print(len(allYamlList))
    # print(allYamlList)
    print("------- rpm dict -------")
    # json.dumps(d)
    with open("sp3_yaml.json", "w") as f:
        f.write(json.dumps(d))
