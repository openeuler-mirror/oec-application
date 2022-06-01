# 文件说明

## 描述

查询兼容性清单表格中的板卡，如果没有在pci.ids文件中，按照一定的格式将该板卡信息插入。

## 用法

python pci.py pci.ids路径 兼容性清单表格路径

示例：

python pci.py  D:\pci\pci.ids D:\pci\openEuler20.03-LTS-SP1上两类平台板卡兼容清单.xlsx


备注: 

路径可以是绝对路径或者相对路径 

兼容性清单表格获取路径：https://gitee.com/openeuler/website-v2/tree/master/data/compatibility