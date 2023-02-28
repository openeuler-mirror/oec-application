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
# Desc: Image Repository Configuration Library Integrating Files

import base64
import sys
import os
import yaml
import json
import copy
import re
import requests
import time
from collections import defaultdict
from xml.etree.ElementTree import parse
from txdpy import get_Bletter,get_Sletter
import logging

srcOepkgsNum = 0
allFileNum = 0
allFileList = []  # 存放 当前路径 以及当前路径的子路径 下的所有文件
sigInfolist = []
allYamlList = []
filter_char = []
allYamldata = []
rpm_info = {}
d = defaultdict(list)
d_oepkg = defaultdict(list)
dict_list = defaultdict(list)
Inyaml = []

def getAllFilesInPath(path):
    global allFileNum
    curPathDirList = []  # 当前路径下的所有文件夹
    files = os.listdir(path)  # 返回当前路径下的所有文件和文件夹
    
    for dl in curPathDirList:
        getAllFilesInPath(path + "/" + dl)  # 递归获取当前目录下的文件夹内的文件


def fileRoute(file,allFileNum,curPathDirList):
    for f in file:
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
                if path.split("/")[1] == "oepkgs-management_10":
                    allYamldata.append(os.path.abspath(path + "/" + f))
                elif path.split("/")[1] == "oepkgs-management":
                    Inyaml.append(f)


def creat_pr():
    data = {"access_token": api_token, "title": "自动化创建库", "head": "zhang-yn:master", "base": "master"}
    response = requests.post("https://gitee.com/api/v5/repos/oepkgs/oepkgs-management/pulls", params=data,headers=headers)
    pr_num = json.loads(response.text)["number"]
    logging.info("-------- waiting 10 minutes ---------")
    time.sleep(150)
    response = requests.get("https://gitee.com/api/v5/repos/oepkgs/oepkgs-management/pulls/{}/merge?access_token={}".format(pr_num,api_token),headers=headers)
    response_dict = json.loads(response.text)
    if "message" in response_dict.keys():
        if response_dict['message'] == 'Pull Request已经合并':
    else:
        commit_id = os.popen(
                "{0} 'https://gitee.com/api/v5/repos/oepkgs/oepkgs-management/pulls/{1}' -d '{{\"access_token\":\"{2}\",\"state\":\"closed\"}}'".format(
                closed_header, pr_num, api_token)).read()
        response_json = json.loads(commit_id)
        if response_json["state"] == "closed":
            creat_pr()
        else:
            logging.info("---------- pr 未关闭 ------------")
            sys.exit()
        logging.info("--- 已合入 ---")


# 监听pr
def listen_event(pr_num):
    response = requests.get(
        "https://gitee.com/api/v5/repos/oepkgs/oepkgs-management/pulls/{}/merge?access_token={}".format(pr_num,api_token),headers=headers)
    response_dict = json.loads(response.text)
    if not response_dict['message'] == 'Pull Request已经合并':
        time.sleep(300)
        listen_event(pr_num)


if __name__ == '__main__':
    # 读取rpm包名存入列表内
    headers = {"Content-Type": "application/json;charset=UTF-8"}
    rpm_pkg_path = "/srv/rpm/pub/"
    rq_header = "curl -X POST --header 'Content-Type: application/json;charset=UTF-8'"
    real_path = os.path.dirname(os.path.realpath(__file__)) + "/"
    closed_header = "curl -X PATCH --header 'Content-Type: application/json;charset=UTF-8'"
    api_token = "c4a7f2254bd58885a9c6fa80cbd0b7dc"
    robot_token = "c951fee688f4b037d27602d7461b81fc"

    a = 0
    # 创建pr
    os.system("git clone 'https://gitee.com/zhang-yn/oepkgs-management.git';")
    getAllFilesInPath("./oepkgs-management_10/sig")
    getAllFilesInPath("./oepkgs-management/sig")
    for i, item in enumerate(allYamldata):
        if len(get_Bletter(item.split("/")[-1][:-5])) != 0:
            if item.split("/")[-1][:-5].lower() + ".yaml" in Inyaml:
                continue
        dest_path = real_path + "oepkgs-management" + "/"  + "/".join(item.split("/")[-5:-1]) + "/"
        if item.split("/")[-1] not in Inyaml and len(item.split("/")[-1][:-5]) > 1 and len(item.split("/")[-1].split(".")) == 2:
            a = a + 1
            if not os.path.exists(dest_path):
                os.system("mkdir -p {0};cp -rf {1} {0}".format(dest_path,item))
            else:
                os.system("cp -rf {1} {0}".format(dest_path, item))
            if a >= 100:
                os.system("cd {0};git add .;git commit -m '自动化仓库创建';git push".format("oepkgs-management"))
                creat_pr()
                time.sleep(500)
                a = 0
logging.info("------- creat end -------")