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
# Desc: Read the file by line and insert it into the Excel file.

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
import xlwt
xl = xlwt.Workbook(encoding='UTF-8')
sheet = xl.add_sheet('docker', cell_overwrite_ok=True)
row = 0
cloum = 0

# 取输入文件的每一行进行excel插入
with open("输入文件.txt","r") as f:
    for k,i in enumerate(f.readlines()):
        a = os.popen("grep FROM {}".format(i)).read()
        data = i.split("/")
        for j in data:
            sheet.write(row, cloum, j)
            cloum = cloum + 1
        sheet.write(row, 12, a)
        cloum = cloum + 1
        with open("docker_link.txt","r") as f1:
            sheet.write(row, 15, f1.readlines()[k])
        row = row + 1
        cloum = 0

xl.save("docker.xls")