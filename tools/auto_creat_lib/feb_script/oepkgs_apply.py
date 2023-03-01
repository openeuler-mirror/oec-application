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
# Desc: Automatically processes the XML file that stores RPM information and writes the processed information to the JSON file.

import base64
import copy
import sys
import os
import yaml
import json
import re
import requests
from collections import defaultdict
from lxml import html
import time
from xml.etree.ElementTree import parse
import threading
import logging

script_toute = sys.path[0]
dict_list = {}
dict_oepkgs = {}
openeuler_version = ["openEuler-20.03-LTS", "openEuler-20.03-LTS-SP1", "openEuler-20.03-LTS-SP2", "openEuler-20.03-LTS-SP3", "openEuler-20.09", "openEuler-21.03", "openEuler-21.09", "openEuler-22.03-LTS", "openEuler-22.03-LTS-SP1", "openEuler-22.09"]
oepkgs_version = [ "openeuler-20.03-LTS-SP1", "openeuler-20.03-LTS-SP2", "openeuler-20.03-LTS-SP3","openeuler-22.03-LTS", "openeuler-22.03-LTS-SP1"]
requests.DEFAULT_RETRIES = 5
s = requests.session()
s.keep_alive = False

def getAllFilesInPath(path,dict_value):
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
            gz_file = path + "/" + f
            if path.split("/")[-2] == "source" and f[-15:] == "-primary.xml.gz":
                os.system("cp {0} -rf {1};gzip -d {2}".format(gz_file, script_toute, script_toute + "/"  + f))
                oepkgs_link = "https://repo.oepkgs.net/openEuler/rpm/openEuler-" + gz_file.split("-", 1)[-1].split("repodata/")[0]
                xml_file(path.split("/")[4],f[:-3],oepkgs_link,"oepkgs",dict_value)
    for dl in curPathDirList:
        getAllFilesInPath(path + "/" + dl,dict_value)  # 递归获取当前目录下的文件夹内的文件


def xml_file(version,xmlfile_path,link_str,signal,dict_value):
    Parse = parse(xmlfile_path)
    root = Parse.getroot()
    for child in root:
        a = ""
        b = ""
        c = ""
        for k in child:
            if k.tag[39:] == "summary":
                d = k.text
            if k.tag[39:] == "name":
                a = k.text
            if k.tag[39:] == "location":
                e = link_str + k.attrib["href"]
            if k.tag[39:] == "format":
                for j in k:
                    if j.tag[36:] == "license":
                        b = j.text
                    if j.tag[36:] == "group":
                        c = j.text
                if signal == "openeuler":
                    dict_list[version][a] = b + "-*-" + c + "-*-" + d + "-*-" + e
                else:
                    dict_value[a] = b + "-*-" + c + "-*-" + d + "-*-" + e


def openeuler(version):
    for i in version:
        dict_list[i] = {}
        openeuler_link = "https://repo.openeuler.org/{0}/source/".format(i)
        a = requests.get(openeuler_link + "repodata/")
        tree = html.fromstring(a.content)
        navareas = tree.xpath('//tbody/tr/td[@class ="link"]/a/@href')
        for j in navareas:
            if "-primary.xml.gz" in j:
                os.system("wget https://repo.openeuler.org/{0}/source/repodata/{1};gzip -d {1}".format(i, j))
                xml_file(i,j[:-3],openeuler_link,"openeuler",dict_list[i])
                time.sleep(25)
    with open("test.json", "w", encoding="utf-8", ) as f:
        f.write(json.dumps(dict_list))


def oepkgs(version):
    for i in version:
        dict_oepkgs[i] = {}
        getAllFilesInPath("/srv/rpm/pub/" + i,dict_oepkgs[i])
        os.system("rm -rf *-primary.xml")
    logging.info("----------- xml end  -----------")
    with open("oepkgs.json", "w", encoding="utf-8", ) as f:
        f.write(json.dumps(dict_oepkgs))


if __name__ == '__main__':
    openeuler(openeuler_version)
    oepkgs(oepkgs_version)