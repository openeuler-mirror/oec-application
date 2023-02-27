# # !/usr/bin/python3
# # -*- coding:UTF-8 -*-

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

# a = os.system("find ./ -name Dockerfile > log.txt")
with open("log1.txt","r") as f:
    for k,i in enumerate(f.readlines()):
        #print(i)
        a = os.popen("grep FROM {}".format(i)).read()
        #print(a)
        #with open("./test.txt","a+") as f1:
            #f1.write(a.strip() + "-------------" + i)
        data = i.split("/")
        for j in data:
            sheet.write(row, cloum, j)
            cloum = cloum + 1
            #data = i.split("/blob/master/")[1]
            #sys.exit()
            # print(data)
            #data = "https://gitee.com/{}/{}/blob/master/{}".format(upper,i[2:].split("/", 1)[0],i[2:].split("/", 1)[1])
            # with open("./docker_1.txt","a+") as f1:
            #     f1.write(data + "\n")
        sheet.write(row, 12, a)
        cloum = cloum + 1
        with open("docker_link.txt","r") as f1:
            sheet.write(row, 15, f1.readlines()[k])
        row = row + 1
        cloum = 0

xl.save("docker.xls")