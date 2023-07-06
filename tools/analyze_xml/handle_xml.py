#!/usr/bin/python3
import argparse
from xml.dom.minidom import parse
import xml.dom.minidom
from openpyxl import Workbook
import pandas as pd
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.naive_bayes import MultinomialNB
from sklearn.pipeline import make_pipeline
from build_log import Log

logger = Log()


def create_csv(xml_file, all_rpm_name_file):
    # 使用minidom解析器打开 XML 文档
    DOM_TREE = xml.dom.minidom.parse(xml_file)
    collection = DOM_TREE.documentElement
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
        rpm_name = name.childNodes[0].data
        # 所需要的包名文件
        with open(all_rpm_name_file, 'r', encoding="utf-8") as f:
            for line in f:
                if line.strip() == rpm_name:
                    logger.info(rpm_name)
                    description = package.getElementsByTagName('description')[0]
                    group = package.getElementsByTagName('rpm:group')[0]
                    summary = package.getElementsByTagName('summary')[0]
                    ws.append([rpm_name, group.childNodes[0].data, summary.childNodes[0].data,
                               description.childNodes[0].data.replace('\n', '')])

    for cell in ws['B']:
        if cell.value is not None and isinstance(cell.value, str):
            cell.value = cell.value.replace('Unspecified', '')

    # 所需要保存的表的路径
    wb.save('file_num.xlsx')
    # 将xlsx转换成csv
    data_xls = pd.read_excel('file_num.xlsx', engine='openpyxl')
    data_xls.to_csv('file_num.csv', encoding='utf-8')

    return "file_num.csv"


def analyza_csv(xml_file, all_rpm_name_file):
    CSV_FILE = create_csv(xml_file, all_rpm_name_file)
    # 加载数据
    # 将此路径替换为你的csv文件的路径
    FILE_PATH = CSV_FILE
    data = pd.read_csv(FILE_PATH)

    # 检查数据是否包含NaN值
    missing_values = data.isnull().sum()

    # 如果有未分类的软件包 (NaN在'b'列,一下abc列皆为表头)
    if missing_values['group'] > 0:
        # 分割数据为训练集和未分类集
        train_data = data[data['group'].notna()]
        unclassified_data = data[data['group'].isna()]

        # 创建一个模型来预测类别
        model = make_pipeline(CountVectorizer(), MultinomialNB())

        # 使用列a和c作为特征，列b作为目标变量来训练模型
        X_TRAIN = train_data['name'] + ' ' + train_data['summary'] + train_data['description']
        y_train = train_data['group']
        model.fit(X_TRAIN, y_train)

        # 预测未分类的软件包的类别
        X_UNCLASSIFIED = unclassified_data['name'] + ' ' + unclassified_data['summary'] + unclassified_data[
            'description']
        predicted_categories = model.predict(X_UNCLASSIFIED)
        # 将预测的类别添加到表格
        data.loc[data['group'].isna(), 'group'] = predicted_categories

        # 保存更新后的表格到新的csv文件
        data.to_csv('updated_file1.csv', index=False)
        logger.info("Categories have been predicted and the updated file has been saved as 'updated_file.csv'")
    else:
        logger.info("All packages are already categorized.")


def init_args():
    """
    init args
    :return:
    """
    parser = argparse.ArgumentParser()
    parser.add_argument("-x", type=str, dest="xml_file_name", help="xml file name")
    parser.add_argument("-r", type=str, dest="rpm_file_name", help="所需要解析的包名")

    return parser.parse_args()


if "__main__" == __name__:
    args = init_args()
    analyza_csv(args.xml_file_name, args.rpm_file_name)