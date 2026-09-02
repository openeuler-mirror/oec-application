# Tool Function

English | [简体中文](./README.md)

It parses the **primary.xml** file in repodata of the RPM source package, finds the software packages that are not classified under the group node, and predicts the categories of the unclassified software packages.

## Operating Environment

Python 3.10

## How to Use

python handle_xml.py -x xml_file_name -r rpm_file_name

xml_file_name indicates the XML file in repodata.
rpm_file_name indicates the name of the required RPM package.
Examples:
0ad
0ad-data
2048-cli
2ping
389-ds
3omns
