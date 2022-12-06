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
# Author: @weijihui
# Create: 2022-06-17
# Modified: @meitingli
# Desc: Common functions

import json
import os
import sys
import yaml
from subprocess import getstatusoutput


def mkdir(path):
    os.makedirs(path, exist_ok=True)


def read_yaml(yaml_path):
    with open(yaml_path) as f:
        yaml_content = yaml.safe_load(f)
    return yaml_content


def write_yaml(yaml_content, yaml_path):
    with open(yaml_path, "w") as f:
        yaml.safe_dump(yaml_content, f)


def read_json(json_path):
    with open(json_path) as f:
        json_content = json.load(f)
    return json_content


def exec_cmd(cmd):
    exitcode, output = getstatusoutput(cmd)
    return exitcode, output


def check_args(arg_name, cmd_arg):
    if cmd_arg:
        return cmd_arg
    else:
        print("[ERROR]: {} is not entered.".format(arg_name))
        sys.exit()
