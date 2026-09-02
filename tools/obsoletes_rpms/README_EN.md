# Tool Function

English | [简体中文](./README.md)

Obtain the compatibility issues that may be caused by software package deletion between versions.

# Dependency Installation

```sh
install rpm-build:
'''
yum install -y rpm-build &&
pip3 install -i https://pypi.tuna.tsinghua.edu.cn/simple -r requirement
'''
```

# Instructions

`python3 retrieve_obsolete_rpms.py [-b OS_BRANCH] [-om OBSOLETES_MODEL] [-w WORK_DIR]  all-rpm-report.csv`

1. Enter the version differences output by the oecp tool: all-rpm-report.csv.
2. Enter the version branch name after the upgrade: eg: openEuler-22.03-LTS-SP1.
3. Parsing modes
   - OS: Download the `.srpm` file in Everything of the released version to obtain the `.spec` file for parsing.
   - repo: Obtain the latest `.spec` file of the corresponding branch in the open source repository for parsing.
4. For example, run `python3 retrieve_obsolete_rpms.py -b openEuler-22.03-LTS-SP1 -om repo  -w /root/ all-rpm-report.csv`.
