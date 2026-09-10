# Tool Function

English | [简体中文](./README.md)

It scans RPM packages that depend on Spring Framework in batches, check whether these packages are affected by **JAR files in the spring-beans-\*.jar** format, checks whether the JAR packages in the RPM packages are affected by the **CachedIntrospectionResuLts.class** file, and outputs the affected RPM packages.

## Operating Environment

Python 3.7

## Instructions

1. Enter a parameter. The first parameter is the path for scanning RPM packages in batches.

2. For example, run the following command:

   ```python
   python3 check_springframwork.py /root/yuanlipeng/check_springframwork/scan_path
   ```
