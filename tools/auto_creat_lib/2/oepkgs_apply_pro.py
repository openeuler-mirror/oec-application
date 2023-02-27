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
# j = 2
# with open("rpm_info.json", "w") as f:
def write(col, a):
    for key in a.keys():
        # if key == "openeuler-22.03-LTS-SP1":
        #     print(b[key].keys())
        sheet.write(1, col, key)
        sheet.write(2, col, "name")
        sheet.write(2, col + 1, "group")
        sheet.write(2, col + 2, "summary")
        sheet.write(2, col + 3, "lincense")
        sheet.write(2, col + 4, "link")
        for i in a[key].keys():
            if i.lower() in a_list:
                num = a_list.index(i.lower()) + 3
                sheet.write(num, col, i)
                sheet.write(num, col + 1, a[key][i].split("-*-")[1])
                sheet.write(num, col + 2, a[key][i].split("-*-")[2])
                sheet.write(num, col + 3, a[key][i].split("-*-")[0])
                sheet.write(num, col + 4, a[key][i].split("-*-")[3])
                print(col)
        col = col + 5
    return col


if __name__ == '__main__':
    col = 2
    col = write(col,oepkgs_data)
    col = write(col,openeuler_data)
    xls_file.save("euler_pkg.xls")