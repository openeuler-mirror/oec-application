# 工具作用
使用clang提取源码目录下各个函数接口，存入json文件


# 依赖
clang


# 使用说明
1. 输入两个参数，第一个参数为需要扫描源码包的名称，第二个参数为源码包路径
2. 例如执行python3 clang_extract_api.py glibc /root/test/glibc-2.17