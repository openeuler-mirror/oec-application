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
# Desc: Extracts information from the JSON file and writes the information to the Excel file.

import xlrd
import xlwt
from openpyxl import load_workbook
from xlutils.copy import copy


book = xlrd.open_workbook("oepkgs.xlsx")
# 获取第一张工作表
sh = book.sheet_by_index(3)
col_value = sh.col_values(0)
del col_value[0]
del col_value[1]
del col_value[2]

xls = xlrd.open_workbook("oepkgs.xlsx")
xls_file = copy(xls)
sheet = xls_file.get_sheet(3)

with open("test.json","r",) as f :
    openeuler_data = json.loads(f.read())
with open("oepkgs.json","r",) as f1 :
    oepkgs_data = json.loads(f1.read())

oepkgs_list = []
a_list = []
for i in col_value:
    a_list.append(i.lower())

def write(col, a):
    for key in a.keys():
        sheet.write(1, col, key)
        sheet.write(2, col, "name")
        sheet.write(2, col + 1, "group")
        sheet.write(2, col + 2, "summary")
        sheet.write(2, col + 3, "lincense")
        sheet.write(2, col + 4, "link")
        write_col(a)
        col = col + 5
    return col


def write_col(a)
    for i in a[key].keys():
        if i.lower() in a_list:
            num = a_list.index(i.lower()) + 3
            sheet.write(num, col, i)
            sheet.write(num, col + 1, a[key][i].split("-*-")[1])
            sheet.write(num, col + 2, a[key][i].split("-*-")[2])
            sheet.write(num, col + 3, a[key][i].split("-*-")[0])
            sheet.write(num, col + 4, a[key][i].split("-*-")[3])


if __name__ == '__main__':
    col = 2
    col = write(col,oepkgs_data)
    col = write(col,openeuler_data)
    xls_file.save("euler_pkg.xls")