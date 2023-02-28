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
from collections import defaultdict
from xml.etree.ElementTree import parse

with open("./yaml_list.txt","r") as f:
    yaml_list = f.read()
    for i in yaml_list.split(", "):
        i = i.split("'")[1]
        os.system("git clone 'https://gitee.com/src-oepkgs/{0}.git';cp README.md {0}/;".format(i))
        os.chdir(os.getcwd() + "/" + i)
        os.system("git add .;git commit -m 'create';git push;")
        os.chdir(os.path.pardir)
        os.system("rm -rf {}".format(i))
