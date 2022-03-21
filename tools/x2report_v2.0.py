import ast
import csv
import pathlib
import xlwt

conclusion_mapping = {
    1: 'version changed',
    2: 'missing',
    3: 'check needed',
    4: 'package name changed',
    5: 'version unchanged'
}
conclusionType_mapping = {
    1: 'Missing function',
    2: 'Function return value type changed',
    3: 'The function parameter size changes',
    4: 'The number of function parameters changes'
}

fieldnames = ['software', 'src_os', 'target_os', 'arch', 'assessment_item', 'runtime', 'generate_time',
              'dependent_count', 'package_pend_count', 'external_interfaces_count',
              'interface_pend_count', 'interface_compatibility_percent',
              'dependent_compatibility_percent', 'src_pkg', 'target_pkg', 'package_name', 'target_package_name',
              'conclusion_level', 'package_conclusion', 'sub_dep_name', 'sub_dep_type', 'sub_dep_from',
              'sub_dep_conclusion', 'c_cplus_conclusion', 'c_cplus_fileName', 'c_cplus_functionName',
              'c_cplus_src_function', 'c_cplus_target_function',
              'c_cplus_change', 'openEuler_jdk', 'jar_build_jdk', 'jdk_jar_name', 'method_name', 'method_signature',
              'method_package',
              'method_change', 'jar_name', 'rpm_name', 'interface_compare_jar_name',
              'interface_compare_rpm_name', 'changes_package', 'changes_class',
              'changes_methods', 'changes_change']


def x2report():
    with open('result.csv', 'w', newline='', encoding='utf-8') as f:
        writer = csv.DictWriter(f, fieldnames)
        writer.writeheader()

        html_dir = pathlib.Path('.')
        for html_file in html_dir.iterdir():
            if html_file.suffix != '.html':
                continue
            html_path = html_file
            with open(html_path, 'r', encoding='utf-8') as f:
                lines = f.readlines()
                for line in lines:
                    if line.strip().startswith("data: {"):
                        data = ast.literal_eval(line.strip()[6:])[0]
                        break
            # csv_name = str(html_path.with_suffix('.csv'))
            # reset all field with -
            row = {}
            for fieldname in fieldnames:
                row[fieldname] = '-'

            # update base info and count info
            summary = data['summary']
            row.update(summary)
            countInfo = data['count_info']
            row.update(countInfo)

            for package in data['package']:
                row['src_pkg'] = package['src_pkg']
                row['target_pkg'] = package['target_pkg']
                # update function interface
                for dep_package in package['dep_package']:
                    row['target_package_name'] = dep_package['target_package_name']
                    row['conclusion_level'] = dep_package['conclusion_level']
                    row['package_conclusion'] = dep_package['conclusion']
                    row['package_name'] = dep_package['package_name']
                    for sub_dep in dep_package['sub_dep']:
                        row['sub_dep_name'] = sub_dep['name']
                        row['sub_dep_type'] = sub_dep['type']
                        row['sub_dep_from'] = sub_dep['from']
                        row['sub_dep_conclusion'] = sub_dep['conclusion']
                        if not sub_dep['C/C++']:
                            writer.writerow(row)
                        for c_cplus in sub_dep['C/C++']:
                            row['c_cplus_conclusion'] = c_cplus[0]
                            row['c_cplus_fileName'] = c_cplus[1]
                            row['c_cplus_functionName'] = c_cplus[2]
                            row['c_cplus_src_function'] = c_cplus[3]
                            row['c_cplus_target_function'] = c_cplus[4]
                            row['c_cplus_change'] = c_cplus[5:10]
                            writer.writerow(row)
                        row['c_cplus_conclusion'] = row['c_cplus_fileName'] = row['c_cplus_functionName'] = row[
                            'c_cplus_src_function'] = row['c_cplus_target_function'] = row['c_cplus_change'] = '-'

                # reset function interface
                row['target_package_name'] = row['conclusion_level'] = row[
                    'package_conclusion'] = row['package_name'] = row['sub_dep_name'] = row['sub_dep_type'] = row[
                    'sub_dep_from'] = row['sub_dep_conclusion'] = row['c_cplus_conclusion'] = row['c_cplus_fileName'] = \
                    row['c_cplus_functionName'] = row['c_cplus_src_function'] = row['c_cplus_target_function'] = row[
                    'c_cplus_change'] = '-'
                # update jdk interface
                for jdk in package['jdk']:
                    row['openEuler_jdk'] = jdk['openEuler_jdk']
                    row['jar_build_jdk'] = jdk['jar_build_jdk']
                    row['jdk_jar_name'] = jdk['jar_name']
                    for methods in jdk['methods']:
                        row['method_name'] = methods['method_name']
                        row['method_signature'] = methods['method_signature']
                        row['method_package'] = methods['package']
                        row['method_change'] = methods['change']
                        writer.writerow(row)

                # reset jdk interface
                row['openEuler_jdk'] = row['jar_build_jdk'] = row['jdk_jar_name'] = row['method_name'] = row[
                    'method_signature'] = row['method_package'] = row['method_change'] = '-'

                # update java interface
                for java_item in package['java']:
                    row['jar_name'] = java_item['jar_name']
                    row['rpm_name'] = java_item['rpm_name']
                    if not java_item['interface_compare']:
                        writer.writerow(row)
                    for interface_compare in java_item['interface_compare']:
                        row['interface_compare_jar_name'] = interface_compare['jar_name']
                        row['interface_compare_rpm_name'] = interface_compare['rpm_name']
                        for changes in interface_compare['changes']:
                            row['changes_package'] = changes['package']
                            row['changes_class'] = changes['class']
                            row['changes_methods'] = changes['methods']
                            row['changes_change'] = changes['change']
                            writer.writerow(row)
                        row['changes_package'] = row['changes_class'] = row['changes_methods'] = row[
                            'changes_change'] = '-'

                # reset function interface
                row['jar_name'] = row['rpm_name'] = row['interface_compare_jar_name'] = row[
                    'interface_compare_rpm_name'] = row['changes_package'] = row['changes_class'] = row[
                    'changes_methods'] = row['changes_change'] = '-'

            # add separator
            for fieldname in fieldnames:
                row[fieldname] = '-'
            writer.writerow(row)


def csv_to_xlsx():
    with open('result.csv', 'r', encoding='utf-8') as f:
        read = csv.reader(f)
        workbook = xlwt.Workbook()
        sheet = workbook.add_sheet('data')  # 创建一个sheet表格

        # 创建样式
        style = xlwt.XFStyle()
        style_pattern = xlwt.XFStyle()
        style_wrap = xlwt.XFStyle()

        # 背景
        pattern = xlwt.Pattern()
        pattern.pattern = xlwt.Pattern.SOLID_PATTERN  # May be: NO_PATTERN, SOLID_PATTERN, or 0x00 through 0x12
        pattern.pattern_fore_colour = 7  # 给背景颜色赋值
        style_pattern.pattern = pattern  # 把背景颜色加到表格样式里去

        # 字体
        font = xlwt.Font()
        font.name = '微软雅黑'  # 设置字体
        font.colour_index = 4  # 设置字体颜色
        font.bold = True  # 是否加粗
        font.height = 20 * 10  # 设置字号为10号
        style.font = font
        style_pattern.font = font
        style_wrap.font = font

        # 边框
        borders = xlwt.Borders()
        borders.top = xlwt.Borders.THIN  # DASHED虚线，NO_LINE没有，THIN实线
        borders.bottom = xlwt.Borders.THIN
        borders.left = xlwt.Borders.THIN
        borders.right = xlwt.Borders.THIN
        borders.left_colour = 4  # 设置线颜色
        style.borders = borders
        style_pattern.borders = borders
        style_wrap.borders = borders

        # 对齐
        alignment = xlwt.Alignment()
        alignment.horz = 0x02  # 0x01(左端对齐)、0x02(水平方向上居中对齐)、0x03(右端对齐)
        alignment.vert = 0x01  # 0x00(上端对齐)、 0x01(垂直方向上居中对齐)、0x02(底端对齐)
        alignment.wrap = 1  # 设置自动换行
        style_wrap.alignment = alignment

        l = 0
        for line in read:
            r = 0
            for i in line:
                if l == 0:
                    sheet.write(l, r, i, style_wrap)
                else:
                    if 15 <= r <= 28 or r > 35:
                        sheet.write(l, r, i, style_pattern)
                    else:
                        sheet.write(l, r, i, style)  # 一个一个将单元格数据写入
                r = r + 1
            l = l + 1

        for i in range(len(fieldnames)):
            sheet.col(i).width = 4800

        workbook.save('result.xls')  # 保存Excel


if __name__ == '__main__':
    x2report()
    csv_to_xlsx()
