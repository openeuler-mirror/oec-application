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
# Desc: Automatically insert values into Excel files.


import xlrd
import xlwt
from openpyxl import load_workbook
from xlutils.copy import copy


suse_name = {}
book = xlrd.open_workbook("zyn1.xlsx")

# 获取第一张工作表
sh = book.sheet_by_index(0)
col_value = sh.col_values(0)
del col_value[0]

# 读取
wb = openpyxl.load_workbook("zyn1.xlsx")
sheet = wb['包清单']

a_list = []
num = 124756
for key in suse_name:
    if key.lower() not in col_value:
        sheet["A{}".format(num)] = key.lower()
        sheet["B{}".format(num)] = "SUSE"
        sheet["C{}".format(num)] = "FALSE"
        sheet["D{}".format(num)] = "FALSE"
        sheet["E{}".format(num)] = "FALSE"
        sheet["F{}".format(num)] = "FALSE"
        sheet["G{}".format(num)] = "FALSE"
        sheet["H{}".format(num)] = "FALSE"
        sheet["I{}".format(num)] = "FALSE"
        sheet["G{}".format(num)] = "FALSE"
        sheet["K{}".format(num)] = "FALSE"
        sheet["L{}".format(num)] = "FALSE"
        sheet["M{}".format(num)] = "FALSE"
        sheet["N{}".format(num)] = "FALSE"
        sheet["O{}".format(num)] = "FALSE"
        sheet["P{}".format(num)] = "FALSE"
        sheet["Q{}".format(num)] = "FALSE"
        sheet["R{}".format(num)] = "FALSE"
        sheet["S{}".format(num)] = "FALSE"
        sheet["T{}".format(num)] = "TRUE"
        num = num + 1
    else:
        sheet["T{}".format(col_value.index(key.lower())+2)] = "TRUE"
wb.save("zyn2.xlsx")