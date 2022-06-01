#! /usr/bin/python
# coding: utf-8
# Copyright (c) 2022 Huawei Technologies Co., Ltd.
# pci.py is licensed under the Mulan PSL v2.
# You can use this software according to the terms and conditions of the Mulan PSL v2.
# You may obtain a copy of Mulan PSL v2 at:
#     http://license.coscl.org.cn/MulanPSL2
# THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
# PURPOSE.
# See the Mulan PSL v2 for more details.
# Create: 2022-06-06

import re
import xlrd
import time
import sys


def str1(x):
    """
    四元组的值转化成字符串 3714.0 -> '3714'
    :param x:
    :return:
    """
    return str(x)[0:4]


def get_data(excel):
    """
    从兼容性清单表格中获取板卡信息
    :return:
    """
    tmp_quadruple = []
    for row in range(2, excel.nrows):
        card_types = table.cell_value(row, 8)
        card_info = [table.cell_value(row, 0), table.cell_value(row, 1),
                     table.cell_value(row, 2), table.cell_value(row, 3),
                     table.cell_value(row, 12), table.cell_value(row, 13),
                     table.cell_value(row, 14)]
        # vendorID 和 deviceID 都不能为空，数据才有效。
        if all(card_info[0:2]):
            card_info[0:4] = list(map(str1, card_info[0:4]))
            if card_types in tables:
                # 按照四元组去重
                if card_info[0:4] not in tmp_quadruple:
                    tmp_quadruple.append(card_info[0:4])
                    tables[card_types].append(card_info)
            else:
                tmp_quadruple.append(card_info[0:4])
                tables[card_types] = [card_info]
    return tables


def process_card(tables, path):
    """
    往pci.ids文件写入板卡信息
    :return:
    """
    flag = [0, 0]
    success_num = 0
    fail_num = 0
    with open(path, 'r+', encoding='utf-8', errors='ignore') as file:
        for card_types in tables:
            for card in tables[card_types]:
                # 通过四元组搜索板卡信息
                print(card_types)
                print(card)
                if not search_card(file, card, flag):
                    # 插入板卡信息
                    if write_all_card(card_types, card, file, flag):
                        print("%s 板卡插入成功" % card[5])
                        success_num += 1
                    else:
                        print("%s 板卡插入失败" % card[5])
                        fail_num += 1
        print(f"成功插入{success_num}条板卡信息")
        print(f"失败插入{fail_num}条板卡信息")


def search_card(file, card, flag):
    """
    通过四元组查找板卡
    :param file:
    :param card:
    :param flag:
    :return:
    """
    # 初始化
    file.seek(0, 0)
    flag[0:2] = [0, 0]
    while True:
        flag[1] = file.tell()
        ln = file.readline()
        if ln:
            # 查找厂商id
            if flag[0] == 0 and ln[0].isalnum():
                if re.match(card[0], ln):
                    flag[0] += 1
                elif card[0] < ln[0:4]:
                    break
            # 查找芯片id
            elif flag[0] == 1:
                if re.match("\t|#", ln):
                    if re.match("\t" + card[1], ln):
                        flag[0] += 1
                    elif ln[0] == '\t':
                        device_id = '\t' + card[1]
                        if device_id < ln[0:5]:
                            break
                else:
                    break
            # 查找svID、ssID
            elif flag[0] == 2:
                if re.match("\t\t|#", ln):
                    if re.match("\t\t" + card[2] + " " + card[3], ln):
                        print("%s 板卡信息已存在！" % card[5])
                        return True
                    elif ln[0:2] == "\t\t":
                        sid = '\t\t' + card[2] + ' ' + card[3]
                        if sid < ln[0:11]:
                            break
                else:
                    break
    return False


def set_position(pci_file, posit):
    """
    获取要插入位置后面的内容，并设置到要插入的地址。
    :param pci_file:
    :param posit:
    :return:
    """
    pci_file.seek(posit, 0)
    old_cont = pci_file.read()
    pci_file.seek(posit, 0)
    return old_cont


def write_all_card(card_type, card_info, pci_file, flag):
    """
    将板卡信息写入pci.ids文件
    :param card_type:
    :param pci_file:
    :param card_info:
    :param flag:
    :return:
    """
    if card_type == "FC":
        if write_fc_card(pci_file, card_info, flag):
            return True
    else:
        if write_other_card(pci_file, card_info, flag):
            return True
    return False


def write_fc_card(pci_file, card_info, flag):
    """
    写入FC卡
    :param pci_file:
    :param card_info:
    :param flag:
    :return:
    """
    if flag[0] == 0:
        # 等于0 表示没有找到板卡 按照字符顺序找到插入的位置
        pci_file.seek(0, 0)
        # 获取要插入位置后面的内容
        old_cont = set_position(pci_file, flag[1])
        # 组装vendorID
        card = card_info[0] + ' ' * 2 + card_info[4]
        # 组装deviceID
        card = card + '\n\t' + card_info[1] + ' ' * 2 + card_info[6]
        # 组装svID、ssID
        if card_info[0] == "19e5":
            card = card + '\n\t\t' + card_info[2] + ' ' + card_info[3] + ' ' * 2 + card_info[6] + " " + card_info[5]
        else:
            card = card + '\n\t\t' + card_info[2] + ' ' + card_info[3] + ' ' * 2 + card_info[5]
        pci_file.write(card + '\n' + old_cont)
    elif flag[0] == 1:
        # 等于1 表示只找到vendorID，需要插入deviceID、svID、ssID
        old_cont = set_position(pci_file, flag[1])
        # 组装deviceID
        card = '\t' + card_info[1] + ' ' * 2 + card_info[6]
        # 组装svID、ssID
        if card_info[0] == "19e5":
            card = card + '\n\t\t' + card_info[2] + ' ' + card_info[3] + ' ' * 2 + card_info[6] + " " + card_info[5]
        else:
            card = card + '\n\t\t' + card_info[2] + ' ' + card_info[3] + ' ' * 2 + card_info[5]
        pci_file.write(card + '\n' + old_cont)
    elif flag[0] == 2:
        old_cont = set_position(pci_file, flag[1])
        # 组装svID、ssID
        if card_info[0] == "19e5":
            card = '\t\t' + card_info[2] + ' ' + card_info[3] + ' ' * 2 + card_info[6] + " " + card_info[5]
        else:
            card = '\t\t' + card_info[2] + ' ' + card_info[3] + ' ' * 2 + card_info[5]
        pci_file.write(card + '\n' + old_cont)
    return True


def write_other_card(pci_file, card_info, flag):
    """
    写入NIC\IB\RIAD\SSD卡
    :param pci_file:
    :param card_info:
    :param flag:
    :return:
    """
    if flag[0] == 0:
        # 等于0 表示没有找到板卡 按照字符顺序找到插入的位置
        pci_file.seek(0, 0)
        # 获取要插入位置后面的内容
        old_cont = set_position(pci_file, flag[1])
        # 组装vendorID
        card = card_info[0] + ' ' * 2 + card_info[4]
        # 组装deviceID
        card = card + '\n\t' + card_info[1] + ' ' * 2 + str(card_info[6])
        # 组装svID、ssID
        card = card + '\n\t\t' + card_info[2] + ' ' + card_info[3] + ' ' * 2 + str(card_info[5])
        pci_file.write(card + '\n' + old_cont)
    if flag[0] == 1:
        # 等于1 表示只找到vendorID，需要插入deviceID、svID、ssID
        old_cont = set_position(pci_file, flag[1])
        # 组装deviceID
        card = '\t' + card_info[1] + ' ' * 2 + card_info[6]
        # 组装svID、ssID
        card = card + '\n\t\t' + card_info[2] + ' ' + card_info[3] + ' ' * 2 + card_info[5]
        pci_file.write(card + '\n' + old_cont)
    elif flag[0] == 2:
        old_cont = set_position(pci_file, flag[1])
        # 组装svID、ssID
        card = '\t\t' + card_info[2] + ' ' + card_info[3] + ' ' * 2 + card_info[5]
        print(card)
        pci_file.write(card + '\n' + old_cont)
    return True


if __name__ == '__main__':
    # 将excel表格的内容导入到列表中
    # 获取pci.ids文件
    pci_path = sys.argv[1]
    # 获取兼容性清单表格
    data = xlrd.open_workbook(sys.argv[2])
    # 从兼容性清单表格获取板卡信息
    table = data.sheets()[0]
    # 创建一个空字典，存储Excel的数据
    tables = dict()
    # 查询板卡信息
    tables = get_data(table)
    # 往pci.ids文件写入板卡信息
    process_card(tables, pci_path)

