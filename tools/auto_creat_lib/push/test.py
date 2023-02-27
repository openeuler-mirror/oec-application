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
from collections import defaultdict
from xml.etree.ElementTree import parse

with open("./yaml_list.txt","r") as f:
    #for i in f.readlines():
    yaml_list = f.read()
    for i in yaml_list.split(", "):
        i = i.split("'")[1]
        os.system("git clone 'https://gitee.com/src-oepkgs/{0}.git';cp README.md {0}/;".format(i))
        os.chdir(os.getcwd() + "/" + i)
        os.system("git add .;git commit -m 'create';git push;")
        os.chdir(os.path.pardir)
        os.system("rm -rf {}".format(i))
print("-------- end -------")

    # print(type(", ".split(i)))
