import os
import xlwt
import json


class FormatException(Exception):
    def __init__(self, msg):
        print('%s\r Data format error, please pass in JSON file' % msg)


class Write2ExcelMixin:

    sheet_name = 'compare'
    save_path = './compare.xls'

    def write2excel(self, header, content):
        # 表头，二维列表写入Excel
        wb = xlwt.Workbook(encoding='utf8')
        sheet = wb.add_sheet(self.sheet_name)
        content.insert(0, header)
        for index, i in enumerate(content):
            for j in range(len(header)):
                sheet.write(index, j, i[j])
        wb.save(self.save_path)


class CmpJavaInterfaceJson(Write2ExcelMixin):

    def __init__(self, json_file):
        if not os.path.exists(json_file):
            raise FileNotFoundError('There is no such file or directory: "%s"' % json_file)
        self.data = None
        try:
            with open(json_file, 'r') as fp:
                self.data = json.load(fp)
        except FormatException as format_ex:
            raise format_ex

    @staticmethod
    def append_column(data_dict):
        from_rpm_bin = data_dict.get('from_rpm_bin', '')
        to_rpm_bin = data_dict.get('to_rpm_bin', '')
        from_rpm_src = data_dict.get('from_rpm_src', '')
        to_rpm_src = data_dict.get('to_rpm_src', '')
        return [from_rpm_bin, to_rpm_bin, from_rpm_src, to_rpm_src]

    def cmp_data(self):
        data = []
        compat_type = ['methods_problems_' + x for x in ('High', 'Medium', 'Low', 'Safe')]
        for data_dict in self.data:
            jar_name = data_dict.get('jar_name', '')
            compatibility = data_dict.get('compatibility', '')
            # todo: 新增字段 from_rpm_bin | to_rpm_bin |  from_rpm_src  |  to_rpm_src 记录jar包属于哪个rpm
            for key, value in data_dict.items():
                if key not in compat_type:
                    continue
                for method_item in value:
                    package = method_item.get('package', '')
                    method_class = method_item.get('class', '')
                    methods = method_item.get('methods', [])
                    for method in methods:
                        part = [jar_name, package, method_class, compatibility]
                        collect_list = self.generate_collect_list(method, part) + self.append_column(data_dict)
                        data.append(collect_list)
        header = ['jar_name', 'package', 'class', 'compatibility', 'method', 'change',
                  'from_rpm_bin', 'to_rpm_bin', 'from_rpm_src', 'to_rpm_src']
        self.save_path = './java_interface_compare.xls'
        self.write2excel(header, data)

    @staticmethod
    def generate_collect_list(method_change, part):
        method = method_change.get('method', '')
        change = method_change.get('change', '')
        collect_list = part + [method, change]
        return collect_list


if __name__ == '__main__':
    CmpJavaInterfaceJson('centos7.6_openEuler20.03-sp1_java_arch.json').cmp_data()
