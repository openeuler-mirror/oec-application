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
# Author: @cuixucui
# Create: 2023-06-03

import os
import re
import logging
from scripts.common import exec_cmd

logging.basicConfig(level=logging.INFO, format='%(message)s')


class GetReport:
    def __init__(self, seek_id):
        self.seek_id = seek_id
        self.card_name = ""
        self.node_roles = ""
        self.test_result = ""
        self.test_card_id = ""
        self.result_dirs = ""
        self.os = ""
        self.os_version = ""
        self.os_arch = ""
        self.card_type = ""
        self.fail_num = ""
        self.id = ""
        self.job_stage = ""
        self.job_state = ""
        self.success_num = 0
        self.fail_num = 0
        self.running_num = 0
        self.color = 0

    def get_result_by_seek_id(self):
        seek_id = self.seek_id.split("=")
        result = ""
        if seek_id[0] == "group_id":
            exitcode, output = exec_cmd("cci jobs group_id=%s" % seek_id[1])
            if exitcode == 0:
                job_ids = []
                output_list = output.split("\n")
                for value in output_list[1:]:
                    job_ids.append(re.split(r'[ ]+', value)[1])
                for index in range(len(output_list)):
                    if index == 0:
                        result += "{:10s} {:14s} {:10s} {:10s} {:20s} {:12s} {:15s} {:13s} {:12s} {:12s} {" \
                                  ":12s}\n".format("class", "board_model", "os_arch", "node_role", "card_id", "os",
                                                   "os_version", "id", "test_result", "job_state", "job_stage")
                    else:
                        self.get_card_info(job_ids[index - 1])
                        result += "{:10s} {:14s} {:10s} {:10s} {:20s} {:12s} {:15s} {:13s} \033[1;3{}m{:12s}\033[" \
                                  "0m {:12s} {:12s}\n".format(self.card_type, self.card_name, self.os_arch,
                                                              self.node_roles, self.test_card_id, self.os,
                                                              self.os_version, self.id, self.color, self.test_result,
                                                              self.job_state, self.job_stage)

            logging.info(
                "{:7s} {:<5d} {:5s} {:<5d} {:8s} {:<5d}".format("success:", self.success_num, "fail:", self.fail_num,
                                                                "running:", self.running_num))
        else:
            result = self.get_result_by_job_id(seek_id[1])
        logging.info(result.rstrip("\n"))

    def get_result_by_job_id(self, job_id):
        result = ""
        self.get_card_info(job_id)
        exitcode1, output = exec_cmd("cci jobs id=%s" % job_id)
        if exitcode1 == 0:
            output_list = output.split("\n")
            for index in range(len(output_list)):
                if index == 0:
                    result += "{:8s} {:12s} {:8s} {:10s} {:20s} {:11s} {}\n".format("class", "board_model", "os_arch",
                                                                                    "node_role", "card_id",
                                                                                    "test_result", "links")
                else:
                    result += "{:8s} {:12s} {:8s} {:10s} {:20s} \033[1;3{}m{:11s}\033[0m {}\n".format(self.card_type,
                                                                                                      self.card_name,
                                                                                                      self.os_arch,
                                                                                                      self.node_roles,
                                                                                                      self.test_card_id,
                                                                                                      self.color,
                                                                                                      self.test_result,
                                                                                                      self.result_dirs)
            return result
        return ""

    def get_card_info(self, job_id):
        exitcode, output = exec_cmd(
            "cci jobs -f \* id=%s | grep 'test_card_name\|\"id\"\|\"os\"\|os_version\|os_arch\|test_card_type"
            "\|node_roles\|test_card_id\|result_root\|job_stage\|job_state\|test_result' | awk -F: '{print $2}'" %
            job_id)
        if exitcode == 0:
            link_pre = "https://api.compass-ci.openeuler.org"
            output = output.split("\n")
            self.test_card_id = output[0][2:-2]
            self.card_name = output[1][2:-2]
            self.card_type = output[2][2:-2]
            self.os = output[3][2:-2]
            self.os_version = output[4][2:-2]
            self.os_arch = output[5][2:-2]
            self.node_roles = output[6][2:-2]
            self.id = output[7][2:-2]
            self.job_stage = output[8][2:-2]
            self.job_state = output[9][2:-2]
            self.result_dirs = os.path.join(link_pre, output[10][3:-2], "")
            result_file = os.path.join("/srv", output[10][3:-2], "oec-hardware")
            exitcode1, output1 = exec_cmd("head -n 1 %s" % result_file)
            if self.job_stage == "finished":
                if len(output) == 12:
                    test_result = re.search(r'\w+', output[11]).group()
                    if test_result == "1":
                        self.test_result = "FAIL"
                    else:
                        self.test_result = "PASS"
                else:
                    if self.node_roles == "server" and self.job_state == "finish":
                        self.test_result = "PASS"
                    else:
                        self.test_result = "FAIL"

                    if self.card_type == "raid":
                        if output1[-1] == "0":
                            self.test_result = "PASS"
                        else:
                            self.test_result = "FAIL"
            else:
                self.test_result = ""

            if self.test_result == "FAIL":
                self.color = 1
                self.fail_num += 1
            elif self.test_result == "PASS":
                self.success_num += 1
                self.color = 2
            else:
                self.running_num += 1
