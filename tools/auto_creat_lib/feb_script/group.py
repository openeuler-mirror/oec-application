#!/usr/bin/env python3
# coding: utf-8
# Copyright (c) 2022 Huawei Technologies Co., Ltd.
# oec-hardware is licensed under the Mulan PSL v2.
# You can use this software according to the terms and conditions of the Mulan PSL v2.
# You may obtain a copy of Mulan PSL v2 at:
#     http://license.coscl.org.cn/MulanPSL2
# THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
# PURPOSE.
# See the Mulan PSL v2 for more details.
# Author: @zhangyinuo
# Create: 2023-02-27
# Desc: Read the YAML file of the oepkg-management library and write the file to the Excel file.

import base64
import sys
import os
import yaml
import json
import copy
import re
import requests
import time
from collections import defaultdict
from xml.etree.ElementTree import parse
from txdpy import get_Bletter, get_Sletter
import xlwt
import logging

srcOepkgsNum = 0
allFileNum = 0
allFileList = []  # 存放 当前路径 以及当前路径的子路径 下的所有文件
sigInfolist = []
allYamlList = []
d_list = []
else_list = []
allYamldata = []
rpm_info = {}
d = defaultdict(list)
d_oepkg = defaultdict(list)
dict_list = defaultdict(list)
Inyaml = []
name_list = ['gostyle', 'etcd', 'rubocop', 'hdp', 'csmith', 'http_load', 'serving', 'minio', 'jfuzz', 'junit', 'openstack', 'python-fuzz', 'rabbitmq', 'tomcat', 'uwsgi', 'ffmpeg', 'influxdb', 'boost', 'vmware', 'websphere', 'hpcondor', 'haproxy', 'lvs', 'scanoss', 'quartz', 'netmap', '顺水平台大数据', 'elementui', 'qemu', 'java', 'pylint', 'flake8', 'log4cpp', 'horovod', 'scancode-toolkit', 'vue', 'yum', 'prometheus', 'eureka', ' nmap ', 'docker', 'openstack', 'vmware', 'grpc-java', 'lmbench', 'aavmf', 'leveldb', 'ovirt', 'calico', 'fio', 'flute、libbase', 'gostub', '1823', 'checkstyle', 'gmock', 'tensorboard', 'puppet', 'memcached', 'fcgi', 'wrk', 'alf-fuzz', 'canal', 'iperf', 'varnish', 'chromium', 'libfonts', 'pytorch', 'ceph', 'jetty', 'caffe', 'springcloud', 'zeromq', 'slurm', 'zfs', 'envoy', 'jemalloc', 'mysql', 'keepalived', 'oracle db2', 'pythonchecker', 'spring framework', 'benchmarksql', 'rocksdb', '.net core', 'libserializer', 'springboot', 'xml-commons-apis', 'ats', 'fastdb', 'sac', 'ambari ', 'lustre', 'rapidonvif ', 'containerd', 'redis', 'kafkacat', 'pentaho-reporting-flow-engine', 'greenplum ', 'clickhouse', 'librepository', 'ambari', 'libvirt', 'kvm', 'libreoffice', 'guacamole', 'opencv', 'unittest', 'lib61850', 'mysql', ' exosip', 'clangtidy', 'pentaho-libxml', 'django', 'milvus', 'spring boot', 'kafka', 'gitlab-ci', 'libvirt', 'lstio', 'grafana', 'jboss', 'webbench', 'liblayout', 'apache http server', 'openmpi', 'mongodb', 'libformula', 'greenplum', 'pigz', 'gtest', 'tpccrunner', 'virtual box', ' node midea server', 'rocketmq', 'netperf', 'sentinel', 'libloader', 'lldpd ', 'cassandra', 'rabbitmq', 'tomcat', 'elasticsearch', 'licensecheck', 'fixbotengine-cxx', 'mongoose', 'otter', 'qemu', 'curl', 'openhpc', 'gnu', 'flannel', 'nagios', 'pacemaker', 'nvidia-docker', 'k8s', 'zabbix', 'stratovirt', 'spring cloud', 'telegraf', 'paddlepaddle', 'sysbench', 'tensorflow 2', 'netty', 'tengine', 'sonarqube', 'tesseract', 'tensorflow', 'lzo', 'jmeter', 'postgresql', 'apollo', 'glm-devel', 'coredns', 'siege', 'cdh', 'docker', 'lpsolve-devel', 'sphinx', 'kubernetes', 'node.js', 'arcface', 'caffe', 'open vswitch', '3908/3508', 'nunit', 'glassfish', 'orientdb', 'libreoffice', 'squid', 'nginx', 'websvn', 'secbinarycheck', '3908 raid ', 'fixbotengine-java', 'convey', ' fastdfs', 'echarts', 'nodejs', 'activemq', 'mariadb', 'ansible', 'unixbench', 'javafuzzer']

def getAllFilesInPath(path):
    global allFileNum
    curPathDirList = []  # 当前路径下的所有文件夹
    files = os.listdir(path)  # 返回当前路径下的所有文件和文件夹
    for f in files:
        if os.path.isdir(path + "/" + f):
            if f[0] == ".":
                pass  # 排除隐藏文件夹
            else:
                curPathDirList.append(f)  # 添加非隐藏文件夹
        if os.path.isfile(path + "/" + f):
            if f[-8:] == ".src.rpm":
                allFileList.append(os.path.abspath(path + "/" + f))  # 添加文件
                allFileNum = allFileNum + 1
                # 总文件数+1
            if f[-5:] == ".yaml" and f != "sig-info.yaml":
                if path.split("/")[1] == "oepkgs-management_10":
                    allYamldata.append(os.path.abspath(path + "/" + f))
                elif path.split("/")[1] == "oepkgs-management":
                    Inyaml.append(path + "/" + f)
    for dl in curPathDirList:
        getAllFilesInPath(path + "/" + dl)  # 递归获取当前目录下的文件夹内的文件




def read_yaml(path):
    with open(r"{}".format(path), 'r', encoding='utf-8') as f:
        config = yaml.load(f.read(), Loader=yaml.FullLoader)
        if config.get("name").lower in name_list:
            ws.write(line,0,config.get("name"))
            ws.write(line,1,config.get("group"))
            ws.write(line,2,config.get("description"))
            ws.write(line,3,config.get("license"))
        else:
            else_list.append(i)


if __name__ == '__main__':
    a = 0
    # 创建pr
    os.system("git clone 'https://gitee.com/zhang-yn/oepkgs-management.git';")
    getAllFilesInPath("./oepkgs-management/sig")
    print(len(Inyaml))
    wb = xlwt.Workbook()
    ws = wb.add_sheet('1 sheet')
    line = 0
    column = 0
    num = 0
    for i in Inyaml:
        read_yaml(i)
        line = line + 1
    logging.info("------ test -----")
    wb.save('1.xls')
    print("==== end =====")