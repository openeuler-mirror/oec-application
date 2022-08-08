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
# Desc: OECH-CI Tool

import os
import sys
import time
import argparse

cur_path = os.getcwd()
cur_path = "/".join(cur_path.split("/")[:-1])
sys.path.append(cur_path)
os.putenv("PYTHONPATH", cur_path)

from scripts.oech_submit import Oech_Submit
from scripts.common import *

def main(oech_yaml_path, lab_path, card_conf_path, submit_output=False):
    group_id = time.strftime("%Y-%m-%d-%H-%M-%S", time.localtime())
    yaml_content = read_yaml(oech_yaml_path)
    card_info_hash = read_json(card_conf_path)
    
    oech_submit = Oech_Submit(card_info_hash, lab_path, yaml_content, group_id)
    card_ids_hash = oech_submit.deal_card_info(card_info_hash["card_info"])

    submit_args = oech_submit.get_submit_args()
    box_board_hash = oech_submit.read_lab_board()
    card_with_box = oech_submit.choose_box(card_ids_hash, box_board_hash)
    oech_submit.oech_task(card_with_box, submit_args, submit_output)


if __name__ == "__main__":
    parser = argparse.ArgumentParser(
        description="oech integrated compass-ci tool")
    parser.add_argument('-j', '--job_yaml', type=str,
                        required=False, help='oech job yaml')
    parser.add_argument('-l', '--lab_path', type=str,
                        required=False, help='test lab which include devices dir')
    parser.add_argument('-c', '--card_conf', type=str,
                        required=False, help='json file for all card conf')
    parser.add_argument('-o', '--submit_output', type=str, required=False,
                        help='submit job for get job yaml but not post to server')
    args = parser.parse_args()

    # oech job yaml file
    job_yaml = check_args('job_yaml', args.job_yaml)
    # lab machine path
    lab_path = check_args('lab_path', args.lab_path)
    # test card config json file
    card_conf = check_args('card_conf', args.card_conf)

    main(job_yaml, lab_path, card_conf, args.submit_output)
