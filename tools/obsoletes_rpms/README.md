# 工具作用
获取版本间删除软件包可能引起的兼容性问题


# 依赖环境安装与部署
install rpm-build:
'''
yum install -y rpm-build &&
pip3 install -i https://pypi.tuna.tsinghua.edu.cn/simple -r requirement
'''


# 使用说明

`python3 retrieve_obsolete_rpms.py [-b OS_BRANCH] [-om OBSOLETES_MODEL] [-w WORK_DIR]  all-rpm-report.csv`

1. 输入oecp工具输出版本间差异结果: all-rpm-report.csv
2. 输入升级后版本分支名, eg: openEuler-22.03-LTS-SP1
3. 解析模式
   1. os: 下载已发布版本everything中srpm获取spec文件进行解析
   2. repo: 获取开源仓库对应分支最新spec文件进行解析
4. 例如执行python3 retrieve_obsolete_rpms.py -b openEuler-22.03-LTS-SP1 -om repo  -w /root/ all-rpm-report.csv