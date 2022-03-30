# -*- coding: utf-8 -*-

"""
Copyright: Copyright © Huawei Technologies Co., Ltd. 2021. All rights reserved.
Create: 2021-06-16
"""

import os
import re
import csv

import xlwt
import yaml
import requests
import sqlite3
from concurrent.futures import ThreadPoolExecutor


class DbOperate(object):
    """
    操作sqlite数据库，sql语句执行失败，自动回滚
    """

    def __new__(cls, *args, **kwargs):
        if not hasattr(cls, "_instance"):
            cls._instance = super(DbOperate, cls).__new__(cls)
        return cls._instance

    def __init__(self, db_name):
        self.db_name = db_name
        self.connect = sqlite3.connect(self.db_name, check_same_thread=False)
        self.cursor = self.connect.cursor()

    def __enter__(self):
        return self

    def __exit__(self, exc_type, exc_val, exc_tb):
        self.connect.close()

    def execute_sql(self, sql):
        try:
            self.cursor.execute(sql)
            self.connect.commit()
        except Exception:
            self.connect.rollback()


def get_source_pkg(db_path, package):
    db = DbOperate(db_path)
    try:
        db.execute_sql('select rpm_sourcerpm from packages where location_href=="Packages/{}" ;'.format(package))
        data = db.cursor.fetchall()
        if data:
            return data[0][0]
        else:
            return ''
    except Exception:
        db.cursor.close()


class Compare(object):
    def __init__(self, config_file='./compare.yaml'):
        self.wb = xlwt.Workbook(encoding='utf8')
        self.sheet1 = self.wb.add_sheet('compare', True)
        self.sheet2 = self.wb.add_sheet('data', True)
        self.config_file = config_file
        self.save_dir = './'
        self.os_dict = self.get_os_dict()
        self.os1_name = self.os_dict.get('os1').get('name')
        self.os2_name = self.os_dict.get('os2').get('name')
        self.os1_rpm_count = None
        self.os2_rpm_count = None
        self.use_db = False
        self.os1_db_paths = None
        self.os2_db_paths = None
        self._judge_wheter_use_db()

    def _judge_wheter_use_db(self):
        os1_db_paths = self.os_dict.get('os1').get('db_path')
        os2_db_paths = self.os_dict.get('os2').get('db_path')
        if all([os1_db_paths, os2_db_paths]):
            self.use_db = True
            self.os1_db_paths = os1_db_paths.split()
            self.os2_db_paths = os2_db_paths.split()

    @staticmethod
    def get_source_rpm(db_paths, rpm):
        for db_path in db_paths:
            source_rpm = get_source_pkg(db_path, rpm)
            if source_rpm:
                return source_rpm
        else:
            return '-'

    def get_os_dict(self):
        with open(self.config_file, 'r') as f:
            yaml_data = yaml.safe_load(f)
            return yaml_data

    @staticmethod
    def _load_url_data(url):
        """
        从url页面中提取rpm包名称
        """
        r = requests.get(url)
        raw_list = re.compile(r'<a.*?>(.*?)</a>').finditer(r.text.strip())
        result = []
        for i in raw_list:
            x = i.group(1)
            if x.endswith('.rpm'):
                result.append(x)
        return result

    def _load_cpm_rpm(self):

        rpm1_list = []
        rpm2_list = []
        package_dir1 = self.os_dict.get('os1').get('package_dir', [])
        package_dir2 = self.os_dict.get('os2').get('package_dir', [])
        os1_urls = self.os_dict.get('os1').get('url')
        os2_urls = self.os_dict.get('os2').get('url')
        with ThreadPoolExecutor(8) as pool:
            if package_dir1:
                for package_d in package_dir1.split():
                    rpm1_list.extend(os.listdir(package_d))
            else:
                rpm1_results = pool.map(self._load_url_data, os1_urls.split())

                for rpm1_result in rpm1_results:
                    for rpm1 in rpm1_result:
                        rpm1_list.append(rpm1)
            if package_dir2:
                for package_d in package_dir2.split():
                    rpm2_list.extend(os.listdir(package_d))
            else:
                rpm2_results = pool.map(self._load_url_data, os2_urls.split())
                for rpm2_result in rpm2_results:
                    for rpm2 in rpm2_result:
                        rpm2_list.append(rpm2)

        return rpm1_list, rpm2_list

    def _compare_to_list(self):
        """
        比较两个镜像rpm包差异, 以数字表示差异性，存入list
        :return:
        """
        os1, os2 = self._load_cpm_rpm()
        self.os1_rpm_count, self.os2_rpm_count = len(os1), len(os2)

        result_list = []
        # 软件包名和版本一致处理
        for i in os1:
            row = []
            if i in os2:
                row.append(i)
                row.append(i)
                row.append('1')

            if row:
                result_list.append(row)

        # 移除包名版本一致的元素, 剩下包名和版本不一致元素
        list1_not_in_list2 = [i for i in os1 if i not in os2]

        list2_not_in_lis1 = [i for i in os2 if i not in os1]

        os1, os2 = list1_not_in_list2, list2_not_in_lis1

        os1_dict = {}
        os2_dict = {}

        # 软件包名和版本不一致处理，用字典保存一个软件包对应的多个软件包版本
        for i in os1:
            pkg_prefix = i[:i.rfind('-')][:i[:i.rfind('-')].rfind('-')]

            os1_dict.setdefault(pkg_prefix, []).append(i)

        for i in os2:
            pkg_prefix = i[:i.rfind('-')][:i[:i.rfind('-')].rfind('-')]
            os2_dict.setdefault(pkg_prefix, []).append(i)

        for k in os1_dict.keys():
            row = []
            if k in os2_dict.keys():
                # rpm命名不规范处理, 根据rpm release 值按最右边第几个'.'分割
                switch_dict = {
                    'UniKylin': os1_dict[k][0].rsplit(
                        '.',
                        3)[0] == os2_dict[k][0].rsplit(
                        '.',
                        4)[0],
                    'EulixOS': os1_dict[k][0].rsplit(
                        '.',
                        3)[0] == os2_dict[k][0].rsplit(
                        '.',
                        2)[0]}

                if switch_dict.get(
                    self.os2_name,
                    os1_dict[k][0].rsplit(
                        '.',
                        3)[0] == os2_dict[k][0].rsplit(
                        '.',
                        3)[0]):
                    row.append(os1_dict[k])
                    row.append(os2_dict[k])
                    row.append('1.1')

                elif os1_dict[k][0][:os1_dict[k][0].rfind('-')] == \
                        os2_dict[k][0][:os2_dict[k][0].rfind('-')]:
                    row.append(os1_dict[k])
                    row.append(os2_dict[k])
                    row.append('2')
                else:
                    row.append(os1_dict[k])
                    row.append(os2_dict[k])
                    row.append('3')
            else:
                row.append(os1_dict[k])
                row.append('-')
                row.append('4')
            if row:
                result_list.append(row)

        for k in os2_dict.keys():
            row = []
            if k not in os1_dict.keys():
                row.append('-')
                row.append(os2_dict[k])
                row.append('5')
            if row:
                result_list.append(row)

        result_list.sort(key=lambda x: x[2])
        if self.use_db:
            result = self._insert_source_rpm_to_result(result_list)
            return result

        return result_list

    def _insert_source_rpm_to_result(self, result_list):
        result = []
        for row in result_list:
            os1_rpms, os2_rpms = row[0], row[1]
            if isinstance(os1_rpms, list):
                os1_source_rpms = []
                for os1_rpm in os1_rpms:
                    os1_source_rpms.append(self.get_source_rpm(self.os1_db_paths, os1_rpm))
                os1_source_rpms = list(set(os1_source_rpms))
            else:
                os1_source_rpms = self.get_source_rpm(self.os1_db_paths, os1_rpms)

            if isinstance(os2_rpms, list):
                os2_source_rpms = []
                for os2_rpm in os2_rpms:
                    os2_source_rpms.append(self.get_source_rpm(self.os2_db_paths, os2_rpm))
                os2_source_rpms = list(set(os2_source_rpms))
            else:
                os2_source_rpms = self.get_source_rpm(self.os2_db_paths, os2_rpms)
            row.insert(2, os1_source_rpms)
            row.insert(3, os2_source_rpms)
            result.append(row)
        return result

    def write2excel(self, header, content):
        # rpm数据写入Excel
        content.insert(0, header)
        for index, i in enumerate(content):
            for j in range(len(header)):
                self.write_excel(self.sheet1, index, j, i[j])

        di = {}
        for i in content:
            if i[-1] not in di:
                di[i[-1]] = 1
            else:
                di[i[-1]] = di[i[-1]] + 1

        value_data = [
            self.os1_rpm_count, self.os2_rpm_count, di.get(
                '1', 0), di.get(
                '1.1', 0), di.get(
                '2', 0), di.get(
                '3', 0), di.get(
                '4', 0), di.get(
                '5', 0)]
        column_list = [
            self.os1_name,
            self.os2_name,
            "common",
            "nearly common",
            "the common prefix version",
            "different",
            "only %s" %
            self.os1_name,
            "only %s" %
            self.os2_name]

        for index in range(len(column_list)):
            self.write_excel(self.sheet2, 0, index, column_list[index])
            self.write_excel(self.sheet2, 1, index, value_data[index])

        self.wb.save(os.path.join(self.save_dir, 'package.xls'))

    def write2csv(self, result):
        """
        以表格的形式展示数据
        """
        # rpm数据写入csv
        self.write_csv(os.path.join(self.save_dir, 'compare_rpm.csv'), result)

    def write_excel(self, sheet, row, col, value):
        sheet.write(row, col, value)

    def write_csv(self, file_name, value):
        flags = os.O_WRONLY | os.O_CREAT | os.O_TRUNC
        with os.fdopen(os.open(file_name, flags, 0o600), 'w', newline='') as f:
            writer = csv.writer(f)
            writer.writerows(value)

    def run(self):
        result = self._compare_to_list()
        self.write2excel(
            [self.os1_name, self.os2_name, '%s-source_rpm' % self.os1_name,
             '%s-source_rpm' % self.os2_name,  "compare"], result)
        # self.write2csv(result)


if __name__ == '__main__':
    Compare().run()
