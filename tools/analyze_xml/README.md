### 工具作用
用来解析rpm源码包repodata下的primary.xml文件，找出其中group节点未被分类的软件包，并且预测未分类的软件包的类别。
### 使用环境
python3.10
### 使用方法
python handle_xml.py -x xml_file_name -r rpm_file_name

xml_file_name 为repodata下xml文件
rpm_file_name 为所需要的rpm包名
例如：
0ad
0ad-data
2048-cli
2ping
389-ds
3omns