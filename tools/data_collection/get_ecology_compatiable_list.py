#!/usr/bin/env python3
# coding=utf-8
import urllib3
import json
import xlwt

class EcologyUos:
    def __init__(self):
        self.all_data = {}

    def pull_all_data(self):
        http = urllib3.PoolManager(10)
        req = http.request('GET', 'https://ecology.chinauos.com/analysis/jingpin/search?page=1&limit=10&status=done&framework=all&url=&query=')

        encoded_data = json.loads(req.data.decode('utf-8'))
        count = encoded_data["count"]
        url = "https://ecology.chinauos.com/analysis/jingpin/search?page=1&limit=" + str(count) + "&status=done&framework=all&url=&query="
        req_all_data_hash = json.loads(http.request('GET', url).data.decode('utf-8'))
        self.all_data = req_all_data_hash


    def write_to_xlsx(self):
        sheet_data = self.all_data["data"]
        workbook = xlwt.Workbook(encoding='utf-8')
        sheet1 = workbook.add_sheet("统信兼容性清单")
        for col, head in enumerate(sheet_data[0].keys()):
            sheet1.write(0, col, head)

        for row, sheet_row in enumerate(sheet_data):
            for col, row_key in enumerate(sheet_row.keys()):
                sheet1.write(row+1, col, sheet_row[row_key])

        workbook.save('./ecology_uos_compatiable_list.xls')

if __name__ == '__main__':
    eu = EcologyUos()
    eu.pull_all_data()
    eu.write_to_xlsx()
