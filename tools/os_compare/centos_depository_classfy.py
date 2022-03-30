# -*- coding: utf-8 -*-

"""
Copyright: Copyright © Huawei Technologies Co., Ltd. 2021. All rights reserved.
Create: 2021-06-16
"""

import requests
import re
import xlwt


path = './collect.xls'
result_dict = dict()


def from_url_get_rpm(url):
    r = requests.get(url)
    raw_list = re.compile(r'href=".*?\.rpm"').findall(r.text.strip())
    result = []
    for i in raw_list:
        # 过滤 超链接上 'Parent directory/'
        if '/' in i:
            continue
        result.append(i.split('"')[1])

    # centos8需要添加Appstream
    if url == 'https://mirrors.huaweicloud.com/centos-vault/8.0.1905/BaseOS/x86_64/os/Packages/':
        result.extend(from_url_get_rpm(
            'https://mirrors.huaweicloud.com/centos-vault/8.0.1905/AppStream/x86_64/os/Packages/'))
        return result
    if url == 'https://mirrors.huaweicloud.com/centos-vault/8.1.1911/BaseOS/x86_64/os/Packages/':
        result.extend(from_url_get_rpm(
            'https://mirrors.huaweicloud.com/centos-vault/8.1.1911/AppStream/x86_64/os/Packages/'))
        return result

    if url == 'https://mirrors.huaweicloud.com/centos-vault/8.2.2004/BaseOS/x86_64/os/Packages/':
        result.extend(from_url_get_rpm(
            'https://mirrors.huaweicloud.com/centos-vault/8.2.2004/AppStream/x86_64/os/Packages/'))
        return result

    if url == 'https://mirrors.aliyun.com/centos/8.3.2011/BaseOS/x86_64/os/Packages/':
        result.extend(from_url_get_rpm(
            'https://mirrors.aliyun.com/centos/8.3.2011/AppStream/x86_64/os/Packages/'))
        return result

    return result


def add_mapping_result(key, value):
    result_dict.setdefault(key, value)


def multi_map_osname2rpm(rpm_url_dict):
    for os_name, url in rpm_url_dict.items():
        result = from_url_get_rpm(url)
        add_mapping_result(os_name, result)


def write_excel(path):
    wb = xlwt.Workbook(encoding='utf8')
    sheet1 = wb.add_sheet('collect', True)

    for index, os_name in enumerate(result_dict.keys()):
        sheet1.write(0, index, os_name)

    for col, rpm_list in enumerate(result_dict.values()):
        for row, rpm in enumerate(rpm_list):
            sheet1.write(row + 1, col, rpm)

    wb.save(path)


def run():
    choose = input('请选择centos6/centos7/centos8 收集rpm包: ')
    centos6_url_dict = {
        'centos6.0': 'https://mirrors.huaweicloud.com/centos-vault/6.0/os/x86_64/Packages/',
        'centos6.1': 'https://mirrors.huaweicloud.com/centos-vault/6.1/os/x86_64/Packages/',
        'centos6.2': 'https://mirrors.huaweicloud.com/centos-vault/6.2/os/x86_64/Packages/',
        'centos6.3': 'https://mirrors.huaweicloud.com/centos-vault/6.3/os/x86_64/Packages/',
        'centos6.4': 'https://mirrors.huaweicloud.com/centos-vault/6.4/os/x86_64/Packages/',
        'centos6.5': 'https://mirrors.huaweicloud.com/centos-vault/6.5/os/x86_64/Packages/',
        'centos6.6': 'https://mirrors.huaweicloud.com/centos-vault/6.6/os/x86_64/Packages/',
        'centos6.7': 'https://mirrors.huaweicloud.com/centos-vault/6.7/os/x86_64/Packages/',
        'centos6.8': 'https://mirrors.huaweicloud.com/centos-vault/6.8/os/x86_64/Packages/',
        'centos6.9': 'https://mirrors.huaweicloud.com/centos-vault/6.9/os/x86_64/Packages/',
        'centos6.10': 'https://mirrors.huaweicloud.com/centos-vault/6.10/os/x86_64/Packages/',
    }
    centos7_url_dict = {
        'centos7.0': 'https://mirrors.huaweicloud.com/centos-vault/7.0.1406/os/x86_64/Packages/',
        'centos7.1': 'https://mirrors.huaweicloud.com/centos-vault/7.1.1503/os/x86_64/Packages/',
        'centos7.2': 'https://mirrors.huaweicloud.com/centos-vault/7.2.1511/os/x86_64/Packages/',
        'centos7.3': 'https://mirrors.huaweicloud.com/centos-vault/7.3.1611/os/x86_64/Packages/',
        'centos7.4': 'https://mirrors.huaweicloud.com/centos-vault/7.4.1708/os/x86_64/Packages/',
        'centos7.5': 'https://mirrors.huaweicloud.com/centos-vault/7.5.1804/os/x86_64/Packages/',
        'centos7.6': 'https://mirrors.huaweicloud.com/centos-vault/7.6.1810/os/x86_64/Packages/',
        'centos7.7': 'https://mirrors.huaweicloud.com/centos-vault/7.7.1908/os/x86_64/Packages/',
        'centos7.8': 'https://mirrors.huaweicloud.com/centos-vault/7.8.2003/os/x86_64/Packages/',
        'centos7.9': 'https://mirrors.huaweicloud.com/centos-vault/7.9.2009/os/x86_64/Packages/'}
    centos8_url_dict = {
        'centos8.0': 'https://mirrors.huaweicloud.com/centos-vault/8.0.1905/BaseOS/x86_64/os/Packages/',
        'centos8.1': 'https://mirrors.huaweicloud.com/centos-vault/8.1.1911/BaseOS/x86_64/os/Packages/',
        'centos8.2': 'https://mirrors.huaweicloud.com/centos-vault/8.2.2004/BaseOS/x86_64/os/Packages/',
        'centos8.3': 'https://mirrors.aliyun.com/centos/8.3.2011/BaseOS/x86_64/os/Packages/',
    }
    if choose == 'centos6':
        multi_map_osname2rpm(centos6_url_dict)
    elif choose == 'centos7':
        multi_map_osname2rpm(centos7_url_dict)
    elif choose == 'centos8':
        multi_map_osname2rpm(centos8_url_dict)
    else:
        print('输入错误，请重新启动脚本')
        exit()
    write_excel(path)


if __name__ == '__main__':
    run()
