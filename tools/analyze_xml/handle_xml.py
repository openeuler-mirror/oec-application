#!/usr/bin/python3

from xml.dom.minidom import parse
import xml.dom.minidom
from openpyxl import Workbook
from build_log import Log

logger = Log()

# 使用minidom解析器打开 XML 文档
DOMTree = xml.dom.minidom.parse("suse-primary.xml")
collection = DOMTree.documentElement
if collection.hasAttribute("shelf"):
    logger.info("Root element : %s" % collection.getAttribute("shelf"))

# 创建工作簿
wb = Workbook()
ws = wb.active

# 写入表头
ws.append(['name', 'group', 'summary', 'description'])

# 在集合中获取所有rpm包
packages = collection.getElementsByTagName("package")

# 打印每个rpm包所需内容的详细信息
for package in packages:
    if package.hasAttribute("package"):
        logger.info("Title: %s" % package.getAttribute("package"))

    name = package.getElementsByTagName('name')[0]
    name1 = name.childNodes[0].data
    # 所需要的包名文件
    with open("all", 'r', encoding="utf-8") as f:
        for line in f:
            if line.strip() == name1:
                logger.info(name1)
                description = package.getElementsByTagName('description')[0]
                group = package.getElementsByTagName('rpm:group')[0]
                summary = package.getElementsByTagName('summary')[0]
                ws.append([name1, group.childNodes[0].data, summary.childNodes[0].data,
                           description.childNodes[0].data.replace('\n', '')])

# 所需要保存的表的路径
wb.save('test-11.xlsx')