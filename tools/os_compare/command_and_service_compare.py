import os
import xlwt
import json


class FormatException(Exception):
    def __init__(self, msg):
        print('%s\r Data format error, please pass in JSON file' % msg)


class Write2ExcelMixin:

    sheet_name = 'compare'
    save_path = './command_and_service.xls'

    def write2excel(self, header, content):
        # 表头，二维列表写入Excel
        wb = xlwt.Workbook(encoding='utf8')
        sheet = wb.add_sheet(self.sheet_name)
        content.insert(0, header)
        for index, i in enumerate(content):
            for j in range(len(header)):
                sheet.write(index, j, i[j])
        wb.save(self.save_path)


class CmpCollectJson(Write2ExcelMixin):

    def __init__(self, j1, j2, os1, os2):
        for f in (j1, j2):
            if not os.path.exists(f):
                raise FileNotFoundError('There is no such file or directory: "%s"' % f)
        self.os1 = os1
        self.os2 = os2
        j1_obj, j2_obj = None, None
        try:
            j1_obj = open(j1, 'r')
            j2_obj = open(j2, 'r')
            self.j1 = json.load(j1_obj)
            self.j2 = json.load(j2_obj)
        except AttributeError as attr_ex:
            raise FormatException(attr_ex)
        except Exception:
            raise Exception()
        finally:
            j1_obj.close()
            j2_obj.close()

    def cmp_data(self):
        data = []
        j1 = set(self.j1.keys())
        j2 = set(self.j2.keys())
        both_keys = j1 & j2
        only_j1 = j1 - j2
        only_j2 = j2 - j1
        for key in both_keys:
            data.append([key, self.j1[key]['rpmName'], self.j2[key]['rpmName'],
                         self.j1[key]['dir'], self.j2[key]['dir'], 1])
        for key in only_j1:
            data.append([key, self.j1[key]['rpmName'], '',
                         self.j1[key]['dir'], '', 2])
        for key in only_j2:
            data.append([key, '', self.j2[key]['rpmName'],
                         '', self.j2[key]['dir'], 3])

        if hasattr(self, 'write2excel'):
            method = getattr(self, 'write2excel')
            method(['file', '%s_rpm' % self.os1, '%s_rpm' % self.os2,
                    '%s_dir' % self.os1, '%s_dir' % self.os2, 'level'],
                   data)


if __name__ == '__main__':

    CmpCollectJson('./centos7.6_command.json', './openEuler20.03-sp1_commands.json',
                   'centos7.6', 'openEuler20.03-sp1').cmp_data()
