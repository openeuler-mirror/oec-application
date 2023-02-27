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
oepkgs_list1 = []
a_list = []
c = []
for i in col_value:
    a_list.append(i.lower())

name_list = ["oepkgs_data", "openeuler_data"]
for a in name_list:
    for key in oepkgs_data.keys():
        for j in oepkgs_data[key].keys():
            oepkgs_list.append(j)
for i in oepkgs_list:
    oepkgs_list1.append(i.lower())
for i in a_list:
    if i not in oepkgs_list1:
        c.append(i)
print(len(c))
print(c)

# 测excel里除第一行以外 其他行为空的 第一行的值和数量