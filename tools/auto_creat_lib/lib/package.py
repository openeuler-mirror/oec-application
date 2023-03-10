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
# Desc: Read the RPM package information in the repository based on the version and write the YAML file based on the information.

import base64
import sys
import os
import yaml
import json
import re
import requests
import time
from collections import defaultdict
from xml.etree.ElementTree import parse
import copy
import logging
from lib import package
from txdpy import get_Bletter, get_Sletter
import xlwt
import xlrd
import openpyxl
from openpyxl import load_workbook
from xlutils.copy import copy
from lxml import html


srcOepkgsNum = 0
allFileNum = 0
allFileList = []  # 存放 当前路径 以及当前路径的子路径 下的所有文件
sigInfolist = []
allYamlList = []
filter_char = []
allYamldata = []
src_code_up = []
src_code_is = []
yaml_error = []
script_toute = sys.path[0]
else_list = []
yaml_liu = []
Inyaml = []
allYamldata_tag = []
dict_oepkgs = {}
d = defaultdict(list)
d_oepkg = defaultdict(list)
dict_list = defaultdict(list)
api_token = "c4a7f2254bd58885a9c6fa80cbd0b7dc"
headers = {"Content-Type": "application/json;charset=UTF-8"}
closed_header = "curl -X PATCH --header 'Content-Type: application/json;charset=UTF-8'"
rq_header = "curl -X POST --header 'Content-Type: application/json;charset=UTF-8'"
real_path = os.path.dirname(os.path.realpath(__file__)) + "/"
ones = {1: "one", 2: "two", 3: "three", 4: "four",
        5: "five", 6: "six", 7: "seven", 8: "eight",
        9: "nine", 0: "zero"}
name_list = ['gostyle', 'etcd', 'rubocop', 'hdp', 'csmith', 'http_load', 'serving', 'minio', 'jfuzz', 'junit', 'openstack', 'python-fuzz', 'rabbitmq', 'tomcat', 'uwsgi', 'ffmpeg', 'influxdb', 'boost', 'vmware', 'websphere', 'hpcondor', 'haproxy', 'lvs', 'scanoss', 'quartz', 'netmap', '顺水平台大数据', 'elementui', 'qemu', 'java', 'pylint', 'flake8', 'log4cpp', 'horovod', 'scancode-toolkit', 'vue', 'yum', 'prometheus', 'eureka', ' nmap ', 'docker', 'openstack', 'vmware', 'grpc-java', 'lmbench', 'aavmf', 'leveldb', 'ovirt', 'calico', 'fio', 'flute、libbase', 'gostub', '1823', 'checkstyle', 'gmock', 'tensorboard', 'puppet', 'memcached', 'fcgi', 'wrk', 'alf-fuzz', 'canal', 'iperf', 'varnish', 'chromium', 'libfonts', 'pytorch', 'ceph', 'jetty', 'caffe', 'springcloud', 'zeromq', 'slurm', 'zfs', 'envoy', 'jemalloc', 'mysql', 'keepalived', 'oracle db2', 'pythonchecker', 'spring framework', 'benchmarksql', 'rocksdb', '.net core', 'libserializer', 'springboot', 'xml-commons-apis', 'ats', 'fastdb', 'sac', 'ambari ', 'lustre', 'rapidonvif ', 'containerd', 'redis', 'kafkacat', 'pentaho-reporting-flow-engine', 'greenplum ', 'clickhouse', 'librepository', 'ambari', 'libvirt', 'kvm', 'libreoffice', 'guacamole', 'opencv', 'unittest', 'lib61850', 'mysql', ' exosip', 'clangtidy', 'pentaho-libxml', 'django', 'milvus', 'spring boot', 'kafka', 'gitlab-ci', 'libvirt', 'lstio', 'grafana', 'jboss', 'webbench', 'liblayout', 'apache http server', 'openmpi', 'mongodb', 'libformula', 'greenplum', 'pigz', 'gtest', 'tpccrunner', 'virtual box', ' node midea server', 'rocketmq', 'netperf', 'sentinel', 'libloader', 'lldpd ', 'cassandra', 'rabbitmq', 'tomcat', 'elasticsearch', 'licensecheck', 'fixbotengine-cxx', 'mongoose', 'otter', 'qemu', 'curl', 'openhpc', 'gnu', 'flannel', 'nagios', 'pacemaker', 'nvidia-docker', 'k8s', 'zabbix', 'stratovirt', 'spring cloud', 'telegraf', 'paddlepaddle', 'sysbench', 'tensorflow 2', 'netty', 'tengine', 'sonarqube', 'tesseract', 'tensorflow', 'lzo', 'jmeter', 'postgresql', 'apollo', 'glm-devel', 'coredns', 'siege', 'cdh', 'docker', 'lpsolve-devel', 'sphinx', 'kubernetes', 'node.js', 'arcface', 'caffe', 'open vswitch', '3908/3508', 'nunit', 'glassfish', 'orientdb', 'libreoffice', 'squid', 'nginx', 'websvn', 'secbinarycheck', '3908 raid ', 'fixbotengine-java', 'convey', ' fastdfs', 'echarts', 'nodejs', 'activemq', 'mariadb', 'ansible', 'unixbench', 'javafuzzer']
openeuler_version = ["openEuler-20.03-LTS", "openEuler-20.03-LTS-SP1", "openEuler-20.03-LTS-SP2", "openEuler-20.03-LTS-SP3", "openEuler-20.09", "openEuler-21.03", "openEuler-21.09", "openEuler-22.03-LTS", "openEuler-22.03-LTS-SP1", "openEuler-22.09"]
oepkgs_version = ["openeuler-20.03-LTS-SP1", "openeuler-20.03-LTS-SP2", "openeuler-20.03-LTS-SP3", "openeuler-22.03-LTS", "openeuler-22.03-LTS-SP1"]
group_dict = {"Amusements/Games": "multimedia/game", "Amusements/Graphics": "multimedia/graphics",
              "Apache Software": "other/other", "Application": "other/other",
              "Application/Development": "development environment/development environment",
              "Application/Internet": "network/communications", "Application/System": "basic system/system tools",
              "Applications": "other/other", "Applications/Archiving": "other/other",
              "Applications/Communications": "network/", "Applications/Databases": "databases/databases",
              "Applications/Development": "development environment/development tools",
              "Applications/Editors": "development environment/auxiliary applications",
              "Applications/Emulators": "hardware/emulation", "Applications/Engineering": "basic system/accelerator",
              "Applications/File": "development environment/other", "Applications/Internet": "network/communications",
              "Applications/Multimedia": "multimedia/audio",
              "Applications/Productivity": "development environment/productization",
              "Applications/Publishing": "development environment/publish", "Applications/Server": "middleware/server",
              "Applications/System": "basic system/syetem tools", "Applications/Text": "development environment/other",
              "Arch/Tech": "other/other", "Backup Server": "middleware/server",
              "Development": "development environment/development environment",
              "Development Documentation": "development environment/document",
              "Development/Bioinformatics": "development environment/biological development",
              "Development/C": "development environment/C", "Development/C++": "development environment/C++",
              "Development/Data": "development environment/data",
              "Development/Debug": "development environment/debugger",
              "Development/Debuggers": "development environment/debugger",
              "Development/Documentation": "development environment/document",
              "Development/Java": "development environment/java",
              "Development/Languages": "development environment/other",
              "Development/Languages/Other": "development environment/other",
              "Development/Languages/Python": "development environment/python",
              "Development/Languages/C and C++": "development environment/C++",
              "Development/Libraries": "development environment/basic library",
              "Development/Libraries/C and C++": "development environment/basic library",
              "Development/Libraries/Python": "development environment/basic library",
              "Development/Libraries/Java": "development environment/basic library",
              "Google": "other/other",
              "Development/Python": "development environment/python",
              "Development/Ruby": "development environment/ruby",
              "Development/System": "development environment/system",
              "Development/Testing": "development environment/testing",
              "Development/Tools": "development environment/tools",
              "Development/Tools/Building": "development environment/build tools",
              "Development/Tools/Debuggers": "development environment/debugger",
              "Development/Tools/Other": "development environment/debugger/other",
              "Development/Tools/Version Control": "development environment/version management",
              "Distro/utilities": "development environment/publish",
              "Distro/virtual": "development environment/publish", "Documentation": "development environment/document",
              "Documentation/Man": "development environment/document", "Graphical desktop/Other": "desktop/graphics",
              "Installation Script": "development environment/deployment", "LXQt": "desktop/graphics",
              "Libraries": "basic system/basic library", "NONE": "other/other",
              "Networking/Admin": "network/network management", "Networking/Daemons": "network/basic service",
              "Networking/Diagnostic": "network/network diagnosis",
              "OS Security": "development environment/security development",
              "Productivity/Databases/Servers": "database/service",
              "Productivity/Networking/Diagnostic": "network/network diagnosis",
              "Productivity/Networking/Other": "network/other", "System Environment": "basic system/other",
              "System Environment/Base": "basic system/basic library",
              "System Environment/Daemons": "basic system/basic service",
              "System Environment/Development": "development environment/development environment",
              "System Environment/Development Tools": "development environment/tools",
              "System Environment/Kernel": "other/other", "System Environment/Libraries": "basic system/basic library",
              "System Environment/Shells": "basic system/basic tools", "System/Boot": "basic system/Initiate",
              "System/Config": "basic system/config", "System/Daemons": "basic system/basic service",
              "System/GUI/Other": "desktop/graphics", "System/I18n/Chinese": "basic system/standardization",
              "System/Kernel": "other/other", "System/Libraries": "basic system/basic library",
              "System/Management": "basic system/management", "System/Monitoring": "basic system/monitoring tools",
              "Test": "development environment/testing", "Testing": "development environment/testing",
              "development environment/testing": "development environment/developing plug-ins",
              "Text Processing/Markup/HTML": "development environment/developing plug-ins",
              "Text Processing/Markup/XML": "development environment/developing plug-ins",
              "Tools/Docker": "virtualization/container", "Unspecified": "other/other",
              "User Interface/Desktops": "desktop/graphical applications",
              "User Interface/X": "desktop/graphics library",
              "User Interface/X Hardware Support": "desktop/graphical basics", "Utilities": "basic system/other",
              "Utilities/System": "other/other", "Utility/Libraries": "basic system/basic library",
              "Virtualization/Management": "virtualization/virtualization management",
              "__GROUP_SYS_BASE__": "other/other", "a": "other/other", "applications/database": "database/other",
              "default": "other/other", "devel": "development environment/development", "hw.com": "other/other",
              "testing": "development environment/testing", "xx.com": "other/other", "xxx.com": "other/other",
              "？？？？": "development environment/development framework"}

suse_group_info = {"Amusement/other": "multimedia/game", "Amusements/Games/3D/Other": "multimedia/game",
     "Amusements/Games/3D/Race": "multimedia/game", "Amusements/Games/3D/Shoot": "multimedia/game",
     "Amusements/Games/3D/Simulation": "multimedia/game", "Amusements/Games/Action/Arcade": "multimedia/game",
     "Amusements/Games/Action/Breakout": "multimedia/game", "Amusements/Games/Action/Other": "multimedia/game",
     "Amusements/Games/Action/Race": "multimedia/game", "Amusements/Games/Action/Shoot": "multimedia/game",
     "Amusements/Games/Board/Card": "multimedia/game", "Amusements/Games/Board/Chess": "multimedia/game",
     "Amusements/Games/Board/Other": "multimedia/game", "Amusements/Games/Board/Pool": "multimedia/game",
     "Amusements/Games/Board/Puzzle": "multimedia/game", "Amusements/Games/Logic": "multimedia/game",
     "Amusements/Games/Other/game": "multimedia/game", "Amusements/Games/RPG": "multimedia/game",
     "Amusements/Games/Strategy/Other": "multimedia/game", "Amusements/Games/Strategy/Real Time": "multimedia/game",
     "Amusements/Games/Strategy/Turn Based": "multimedia/game", "Amusements/Teaching/Language": "multimedia/teaching",
     "Amusements/Teaching/Mathematics": "multimedia/teaching", "Amusements/Teaching/Other": "multimedia/teaching",
     "Amusements/Toys/Background": "multimedia/tools", "Amusements/Toys/Clocks": "multimedia/tools",
     "Amusements/Toys/Graphics": "multimedia/tools", "Amusements/Toys/Other": "multimedia/tools",
     "Amusements/Toys/Screensavers": "multimedia/tools",
     "Development/Languages/C and C++": "development environment/C++",
     "Development/Languages/Fortran": "development environment/Forgtran Development",
     "Development/Languages/Go": "development environment/go development",
     "Development/Languages/Haskell": "development environment/Haskell development",
     "Development/Languages/Java": "development environment/java development",
     "Development/Languages/Lua": "development environment/lua development",
     "Development/Languages/NodeJS": "development environment/nodejs development",
     "Development/Languages/OCaml": "development environment/0caml development",
     "Development/Languages/Other": "development environment/other development",
     "Development/Languages/perl": "development environment/perl development",
     "Development/Languages/PHP": "development environment/php development",
     "Development/Languages/Python": "development environment/python development",
     "Development/Languages/Ruby": "development environment/ruby development",
     "Development/Languages/Rust": "development environment/rust development",
     "Development/Languages/Scheme": "development environment/scheme development",
     "Development/Languages/Tcl": "developm entenvironment/tc1 development",
     "Development/Languages/perl *.spec -> 29": "development environment/perl development",
     "Development/Libraries/perl *.spec -> 1127": "development environment/Development Library",
     "Development/Languages/Python *.spec -> 1190": "development environment/python development",
     "Development/Libraries/Python *.spec -> 404": "development environment/Development Library",
     "Development/Libraries/C and C++": "development environment/c development",
     "Development/Libraries/Cross": "development environment/Development Library",
     "Development/Libraries/GNOME": "basic system/Basic library",
     "Development/Libraries/Haskell": "development environment/Haskell development",
     "Development/Libraries/Java": "development environment/java development",
     "Development/Libraries/KDE": "basic system/Basic library",
     "Development/Libraries/Other": "basic system/Basic library",
     "Development/Libraries/Parallel": "basic system/Basic library",
     "Development/Libraries/perl": "development environment/perl development",
     "Development/Libraries/PHP": "development environment/php development",
     "Development/Libraries/Python": "development environment/python development",
     "Development/Libraries/Rust": "development environment/ruby development",
     "Development/Libraries/Tcl": "development environment/tcl development",
     "Development/Libraries/X11": "basic system/Basic library",
     "Development/Libraries/YaST": "basic system/Basic library",
     "Development/Tools/Building": "development environment/tools",
     "Development/Tools/Debuggers": "development environment/tools",
     "Development/Tools/Doc Generators": "development environment/tools",
     "Development/Tools/GUI Builders": "development environment/tools",
     "Development/Tools/IDE": "development environment/tools",
     "Development/Tools/Navigators": "development environment/tools",
     "Development/Tools/Other": "development environment/tools",
     "Development/Tools/Version Control": "development environment/tools", "Hardware/Camera": "hardware/hardware",
     "Hardware/Fax": "hardware/hardware", "Hardware/ISDN": "hardware/hardware",
     "Hardware/Joystick": "hardware/hardware", "Hardware/Mobile": "hardware/hardware",
     "Hardware/Modem": "hardware/hardware", "Hardware/Other": "hardware/hardware", "Hardware/Palm": "hardware/hardware",
     "Hardware/Printing": "hardware/hardware", "Hardware/Psion": "hardware/hardware",
     "Hardware/Radio": "hardware/hardware", "Hardware/Scanner": "hardware/hardware", "Hardware/TV": "hardware/hardware",
     "Hardware/UPS": "hardware/hardware", "Hardware/Wifi": "hardware/hardware", "Meta packages": "hardware/hardware",
     "Multimedia": "multimedia/hardware", "Productivity/Archiving/Backup": "middleware/Backup and restoration",
     "Productivity/Archiving/Compression": "middleware/Decompress",
     "Productivity/Clustering/Computing": "middleware/clustering",
     "Productivity/Clustering/HA": "middleware/clustering", "Productivity/Databases/Clients": "database/database",
     "Productivity/Databases/Servers": "middleware/database", "Productivity/Databases/Tools": "middleware/database",
     "File utilities": "middleware/tools", "Productivity/File utilities": "middleware/tools",
     "Graphics": "multimedia/Graphics", "Productivity/Graphics/3D Editors": "multimedia/Graphics",
     "Productivity/Graphics/Bitmap Editors": "multimedia/Graphics", "Productivity/Graphics/CAD": "multimedia/Graphics",
     "Productivity/Graphics/Convertors (Converters?)": "multimedia/Graphics",
     "Productivity/Graphics/Other": "multimedia/Graphics",
     "Productivity/Graphics/Vector Editors": "multimedia/Graphics",
     "Productivity/Graphics/Viewers": "multimedia/Graphics",
     "Productivity/Graphics/Visualization/Graph": "multimedia/Graphics",
     "Productivity/Graphics/Visualization/Other": "multimedia/Graphics",
     "Productivity/Graphics/Visualization/Raytracers": "multimedia/Graphics", "Hamradio": "middleware/Office",
     "Productivity/Hamradio/Fax": "middleware/Office", "Productivity/Hamradio/Logging": "middleware/Office",
     "Productivity/Hamradio/Morse": "middleware/Office", "Productivity/Hamradio/Other": "middleware/Office",
     "Productivity/Hamradio/Packet": "middleware/Office", "Productivity/Hamradio/Psk31": "middleware/Office",
     "Productivity/Hamradio/Satellite": "middleware/Office", "multimedia": "multimedia/Audio",
     "Productivity/Multimedia/CD/Grabbers": "multimedia/Audio",
     "Productivity/Multimedia/CD/Players": "multimedia/Audio", "Productivity/Multimedia/CD/Record": "multimedia/Audio",
     "Productivity/Multimedia/Other": "multimedia/Audio",
     "Productivity/Multimedia/Sound/Editors and Convertors": "multimedia/Audio",
     "Productivity/Multimedia/Sound/Midi": "multimedia/Audio",
     "Productivity/Multimedia/Sound/Mixers": "multimedia/Audio",
     "Productivity/Multimedia/Sound/Players": "multimedia/Audio",
     "Productivity/Multimedia/Sound/Utilities": "multimedia/Audio",
     "Productivity/Multimedia/Sound/Visualization": "multimedia/Audio",
     "Productivity/Multimedia/Video/Editors and Convertors": "multimedia/Audio",
     "Productivity/Multimedia/Video/Players": "multimedia/Audio", "Networking": "multimedia/Audio",
     "Productivity/Networking/AOLInstantMessenger": "network/Network Management",
     "Productivity/Networking/Archie": "network/Network Management",
     "Productivity/Networking/Boot/Clients": "network/Basic Services",
     "Productivity/Networking/Boot/Servers": "network/Basic Services",
     "Productivity/Networking/Boot/Utilities": "network/Network Management",
     "Productivity/Networking/Diagnostic": "network/Network diagnosis",
     "Productivity/Networking/DNS/Servers": "network/Basic Services",
     "Productivity/Networking/DNS/Utilities": "network/Network Management",
     "Productivity/Networking/Email/Clients": "network/communications",
     "Productivity/Networking/Email/Mailinglists": "network/Network Management",
     "Productivity/Networking/Email/Servers": "network/Basic Services",
     "Productivity/Networking/Email/Utilities": "network/Network Management",
     "Productivity/Networking/File-Sharing": "network/Basic Services",
     "Productivity/Networking/Ftp/Clients": "network/Basic Services",
     "Productivity/Networking/Ftp/Servers": "network/Basic Services",
     "Productivity/Networking/ICQ": "network/communications",
     "Productivity/Networking/Instant Messenger": "network/Network Management",
     "Productivity/Networking/IRC": "network/communications",
     "Productivity/Networking/LDAP/Clients": "network/Basic Services",
     "Productivity/Networking/LDAP/Servers": "network/Basic Services",
     "Productivity/Networking/LDAP/Utilities": "network/Network Management",
     "Productivity/Networking/Napster": "network/Network Management",
     "Productivity/Networking/News/Clients": "network/Basic Services",
     "Productivity/Networking/News/Servers": "network/Basic Services",
     "Productivity/Networking/News/Utilities": "network/Network Management",
     "Productivity/Networking/NFS": "network/Basic Services", "Productivity/Networking/NIS": "network/Basic Services",
     "Productivity/Networking/Novell": "network/Network Management", "Productivity/Networking/Other": "network/other",
     "Productivity/Networking/PPP": "network/communications",
     "Productivity/Networking/Radius/Clients": "network/Basic Services",
     "Productivity/Networking/Radius/Servers": "network/Basic Services",
     "Productivity/Networking/Routing": "network/Network Management",
     "Productivity/Networking/Samba": "network/Basic Services",
     "Productivity/Networking/Security": "network/Network Management",
     "Productivity/Networking/SSH": "network/Basic Services",
     "Productivity/Networking/System": "network/Network Management",
     "Productivity/Networking/Talk/Clients": "network/Basic Services",
     "Productivity/Networking/Talk/Servers": "network/Basic Services",
     "Productivity/Networking/Web/Browsers": "network/Communicating",
     "Productivity/Networking/Web/Frontends": "network/Communicating",
     "Productivity/Networking/Web/Proxy": "network/Network Management",
     "Productivity/Networking/Web/Servers": "network/Basic Services",
     "Productivity/Networking/Web/Utilities": "network/Network Management",
     "Productivity/Office/Dictionary": "desktop/office", "Productivity/Office/Finance": "desktop/office",
     "Productivity/Office/Management": "desktop/office", "Productivity/Office/Organizers": "desktop/office",
     "Productivity/Office/Other": "desktop/office", "Productivity/Office/Spreadsheets": "desktop/office",
     "Productivity/Office/Suite": "desktop/office", "Productivity/Office/Word Processor": "desktop/office",
     "Productivity/Other": "desktop/office", "Productivity/Publishing/DocBook": "basic system/Release Tools",
     "Productivity/Publishing/HTML/Editors": "basic system/Release Tools",
     "Productivity/Publishing/HTML/Tools": "basic system/Release Tools",
     "Productivity/Publishing/Other": "basic system/Release Tools",
     "Productivity/Publishing/PDF": "basic system/Release Tools",
     "Productivity/Publishing/Presentation": "basic system/Release Tools",
     "Productivity/Publishing/PS": "basic system/Release Tools",
     "Productivity/Publishing/SGML": "basic system/Release Tools",
     "Productivity/Publishing/TeX/Base": "basic system/Release Tools",
     "Productivity/Publishing/TeX/Fonts": "basic system/Release Tools",
     "Productivity/Publishing/TeX/Frontends": "basic system/Release Tools",
     "Productivity/Publishing/TeX/Utilities": "basic system/Release Tools",
     "Productivity/Publishing/Texinfo": "basic system/Release Tools",
     "Productivity/Publishing/Troff": "basic system/Release Tools",
     "Productivity/Publishing/Word": "basic system/Release Tools",
     "Productivity/Publishing/XML": "basic system/Release Tools",
     "Productivity/Scientific/Astronomy": "middleware/scientific computing",
     "Productivity/Scientific/Chemistry": "middleware/scientific computing",
     "Productivity/Scientific/Electronics": "middleware/scientific computing",
     "Productivity/Scientific/Math": "middleware/scientific computing",
     "Productivity/Scientific/Other": "middleware/scientific computing",
     "Productivity/Scientific/Physics": "middleware/scientific computing",
     "Productivity/Security": "middleware/Security Tools",
     "Productivity/Telephony/Clients": "middleware/Security Tools",
     "Productivity/Telephony/H323/Clients": "middleware/Security Tools",
     "Productivity/Telephony/H323/Servers": "middleware/Security Tools",
     "Productivity/Telephony/H323/Utilities": "middleware/Security Tools",
     "Productivity/Telephony/Servers": "middleware/Security Tools",
     "Productivity/Telephony/SIP/Clients": "middleware/Security Tools",
     "Productivity/Telephony/SIP/Servers": "middleware/Security Tools",
     "Productivity/Telephony/SIP/Utilities": "middleware/Security Tools",
     "Productivity/Telephony/Utilities": "middleware/Security Tools",
     "Productivity/Text/Convertors": "middleware/Security Tools",
     "Productivity/Text/Editors": "middleware/Security Tools", "Productivity/Text/Spell": "middleware/Security Tools",
     "Productivity/Text/Utilities": "middleware/Security Tools", "System/Emulators/Other": "hardware/emulation",
     "System/Emulators/PC": "hardware/emulation", "GUI": "desktop/Desktop Library",
     "System/GUI/GNOME": "desktop/graphics system", "System/GUI/KDE": "desktop/graphics system",
     "System/GUI/LXDE": "desktop/graphics system", "System/GUI/Other": "desktop/graphics system",
     "System/GUI/XFCE": "desktop/graphics system", "System/I18n/Chinese": "basic system/Standardization",
     "System/I18n/Japanese": "basic system/Standardization", "System/I18n/Korean": "basic system/Standardization",
     "X11": "desktop/Graphical Basics", "System/X11/Displaymanagers": "desktop/Graphical Basics",
     "System/X11/Fonts": "desktop/Graphical Basics", "System/X11/Icons": "desktop/Graphical Basics",
     "System/X11/Servers/XF86_3": "desktop/Graphical Basics", "System/X11/Servers/XF86_4": "desktop/Graphical Basics",
     "System/X11/Terminals": "desktop/Graphical Basics", "System/X11/Utilities": "desktop/Graphical Basics",
     "YaST": "systemtools/tools", "System/YaST": "systemtools/tools"}


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
                yaml_name = f[:-5]
                allYamlList.append(yaml_name)
    for dl in curPathDirList:
        getAllFilesInPath(path + "/" + dl)


def getAllFilesInPath_1(path):
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
        getAllFilesInPath_1(path + "/" + dl)  # 递归获取当前目录下的文件夹内的文件


def getAllFilesInPath_2(path, dict_value):
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
            gz_file = path + "/" + f
            if path.split("/")[-2] == "source" and f[-15:] == "-primary.xml.gz":
                os.system("cp {0} -rf {1};gzip -d {2}".format(gz_file, script_toute, script_toute + "/"  + f))
                oepkgs_link = "https://repo.oepkgs.net/openEuler/rpm/openEuler-" + gz_file.split("-", 1)[-1].split("repodata/")[0]
                xml_file(path.split("/")[4], f[:-3], oepkgs_link, "oepkgs", dict_value)
    for dl in curPathDirList:
        getAllFilesInPath_2(path + "/" + dl, dict_value)  # 递归获取当前目录下的文件夹内的文件


def shell_cmd(rpm_key, path):
    rpm_info = os.popen("rpm -qi {}".format(path))
    for line in rpm_info.read().splitlines():
        rpm_value = line.split(":")
        if rpm_value[0].strip() == rpm_key:
            return rpm_value[1].strip()


def creat_pr():
    data = {"access_token": api_token, "title": "自动化创建库", "head": "zhang-yn:master", "base": "master"}
    response = requests.post("https://gitee.com/api/v5/repos/oepkgs/oepkgs-management/pulls", params=data, headers=headers)
    pr_num = json.loads(response.text)["number"]
    logging.info("-------- waiting 10 minutes ---------")
    time.sleep(150)
    response = requests.get("https://gitee.com/api/v5/repos/oepkgs/oepkgs-management/pulls/{}/merge?access_token={}".format(pr_num, api_token), headers=headers)
    response_dict = json.loads(response.text)
    if "message" in response_dict.keys():
        if response_dict['message'] == 'Pull Request已经合并':
            logging.info("--- 已合入 ---")
    else:
        commit_id = os.popen(
                "{0} 'https://gitee.com/api/v5/repos/oepkgs/oepkgs-management/pulls/{1}' -d '{{\"access_token\":\"{2}\",\"state\":\"closed\"}}'".format(
                closed_header, pr_num, api_token)).read()
        response_json = json.loads(commit_id)
        if response_json["state"] == "closed":
            creat_pr()
        else:
            logging.info("---------- pr 未关闭 ------------")
            sys.exit()
        logging.info("--- 已合入 ---")


def listen_event(pr_num):
    response = requests.get(
        "https://gitee.com/api/v5/repos/oepkgs/oepkgs-management/pulls/{}/merge?access_token={}".format(pr_num, api_token), headers=headers)
    response_dict = json.loads(response.text)
    if not response_dict['message'] == 'Pull Request已经合并':
        time.sleep(300)
        listen_event(pr_num)


def push_pkg(yaml_file, rpm_path, rpm_version):
    if sys.argv[1] == "master":
        os.system("git clone 'https://gitee.com/src-oepkgs/{0}.git';".format(yaml_file))
    else:
        os.system("git clone -b {1} 'https://gitee.com/src-oepkgs/{0}.git';".format(yaml_file,
                                                                                    sys.argv[1]))
    if not os.path.exists(real_path + yaml_file):
        logging.info("------- download code error ------")
        sys.exit()
    os.chdir(os.getcwd() + "/" + yaml_file)
    os.system(
        "rm -rf *;rpm2cpio {0} | cpio -div;git add .;git commit -m '{1}';git push".format(rpm_path,
                                                                                            rpm_version))
    commit_id = os.popen("git rev-parse HEAD").read().strip()
    os.chdir(os.path.pardir)
    os.system("rm -rf {0}".format(yaml_file))
    if sys.argv[1] == "master":
        os.system(
            "{} 'https://gitee.com/api/v5/repos/src-oepkgs/{}/tags' -d '{{\"access_token\":\"{}\",\"refs\":\"{}\",\"tag_name\":\"{}\"}}'".format(
                rq_header, yaml_file, api_token, commit_id, "20.03-LTS-SP1" + "-v" + rpm_version.replace("^", ".").replace("~", ".")))
    else:
        os.system(
            "{} 'https://gitee.com/api/v5/repos/src-oepkgs/{}/tags' -d '{{\"access_token\":\"{}\",\"refs\":\"{}\",\"tag_name\":\"{}\"}}'".format(
                rq_header, yaml_file, api_token, commit_id, sys.argv[1][10:] + "-v" + rpm_version.replace("^", ".").replace("~", ".")))
    logging.info("------- 库名 ------")


def yamlName(yaml_modify):
    name = ""
    if yaml_modify[0].isdigit():
        for i, item in enumerate(yaml_modify):
            if not item.isdigit() and item == "-":
                name = name + yaml_modify[i + 1:]
                break
            elif not item.isdigit() and item != "-":
                name = name + yaml_modify[i:]
                break
            else:
                name = name + ones[int(item)] + "-"
    elif "+" in yaml_modify:
        name = yaml_modify.replace("+", "plus")
    else:
        name = yaml_modify
    logging.info("*****{}****".format(yaml_modify))
    return name


def judge_commitId(name, num, yaml_modify, data):
    d_oepkg[name] = data[yaml_modify]
    os.chdir(os.path.pardir)
    os.system("rm -rf {0}".format(name))
    num = num + 1
    logging.info("------ {0} branch {1} 已添加 -----".format(name, num))
    return num


def read_yaml(path, ws, line):
    with open(r"{}".format(path), 'r', encoding='utf-8') as f:
        config = yaml.load(f.read(), Loader=yaml.FullLoader)
        if config.get("name").lower in name_list:
            ws.write(line, 0, config.get("name"))
            ws.write(line, 1, config.get("group"))
            ws.write(line, 2, config.get("description"))
            ws.write(line, 3, config.get("license"))
        else:
            else_list.append(i)


def xml_file(version, xmlfile_path, link_str, signal, dict_value):
    Parse = parse(xmlfile_path)
    root = Parse.getroot()
    for child in root:
        a = ""
        b = ""
        c = ""
        for k in child:
            if k.tag[39:] == "summary":
                d = k.text
            if k.tag[39:] == "name":
                a = k.text
            if k.tag[39:] == "location":
                e = link_str + k.attrib["href"]
            if k.tag[39:] == "format":
                for j in k:
                    if j.tag[36:] == "license":
                        b = j.text
                    if j.tag[36:] == "group":
                        c = j.text
                if signal == "openeuler":
                    dict_list[version][a] = b + "-*-" + c + "-*-" + d + "-*-" + e
                else:
                    dict_value[a] = b + "-*-" + c + "-*-" + d + "-*-" + e


def openeuler(version):
    for i in version:
        dict_list[i] = {}
        openeuler_link = "https://repo.openeuler.org/{0}/source/".format(i)
        a = requests.get(openeuler_link + "repodata/")
        tree = html.fromstring(a.content)
        navareas = tree.xpath('//tbody/tr/td[@class ="link"]/a/@href')
        for j in navareas:
            if "-primary.xml.gz" in j:
                os.system("wget https://repo.openeuler.org/{0}/source/repodata/{1};gzip -d {1}".format(i, j))
                xml_file(i, j[:-3], openeuler_link, "openeuler", dict_list[i])
                time.sleep(25)
    with open("test.json", "w", encoding="utf-8", ) as fw:
        fw.write(json.dumps(dict_list))


def oepkgs(version):
    for i in version:
        dict_oepkgs[i] = {}
        getAllFilesInPath_2("/srv/rpm/pub/" + i, dict_oepkgs[i])
        os.system("rm -rf *-primary.xml")
    logging.info("----------- xml end  -----------")
    with open("oepkgs.json", "w", encoding="utf-8", ) as fb:
        fb.write(json.dumps(dict_oepkgs))


def open_file():
    book = xlrd.open_workbook("oepkgs.xlsx")
    # 获取第一张工作表
    sh = book.sheet_by_index(3)
    col_value = sh.col_values(0)
    del col_value[0]
    del col_value[1]
    del col_value[2]
    xls = xlrd.open_workbook("oepkgs.xlsx")
    xls_file = copy(xls)
    sheet = xls_file.get_sheet(3)
    return col_value, sheet, xls_file


def write(col, a, sheet):
    for key in a.keys():
        sheet.write(1, col, key)
        sheet.write(2, col, "name")
        sheet.write(2, col + 1, "group")
        sheet.write(2, col + 2, "summary")
        sheet.write(2, col + 3, "lincense")
        sheet.write(2, col + 4, "link")
        write_col(a, sheet, key, col)
        col = col + 5
    return col


def write_col(a, sheet_new, key, col):
    for i in a[key].keys():
        if i.lower() in oepkgs_list:
            num = oepkgs_list.index(i.lower()) + 3
            sheet_new.write(num, col, i)
            sheet_new.write(num, col + 1, a[key][i].split("-*-")[1])
            sheet_new.write(num, col + 2, a[key][i].split("-*-")[2])
            sheet_new.write(num, col + 3, a[key][i].split("-*-")[0])
            sheet_new.write(num, col + 4, a[key][i].split("-*-")[3])


def base64_encode(path, group_dir, yaml_str):
    with open(r"{}".format(path), 'r', encoding='utf-8') as f:
        config = yaml.load(f.read().format(yaml_str.split("-+-")[0], yaml_str.split("-+-")[1], yaml_str.split("-+-")[2],
                                           yaml_str.split("-+-")[3]), Loader=yaml.FullLoader)
        encode_str = base64.b64encode(
            yaml.dump(config, allow_unicode=True, default_flow_style=False, sort_keys=False).encode('utf-8')).decode(
            'utf-8')
        os.system(
            "{} 'https://gitee.com/api/v5/repos/zhang-yn/oepkgs-management_1/contents/sig%2F{}%2Fsrc-oepkgs%2F{}%2F{}.yaml' -d '{{\"access_token\":\"{}\",\"content\":\"{}\",\"message\":\"test\"}}'".format(
                rq_header, group_dir, yaml_str.split("-+-")[0][0], yaml_str.split("-+-")[0], api_token, encode_str))


def source_code(xmlfile):
    Parse = parse(xmlfile)
    root = Parse.getroot()
    for child in root:
        a1 = ""
        b = ""
        c = ""
        for i in child:
            if i.tag[39:] == "summary":
                d1 = i.text
            if i.tag[39:] == "name":
                a1 = i.text
            if i.tag[39:] == "format":
                for j in i:
                    if j.tag[36:] == "license":
                        b = j.text
                    if j.tag[36:] == "group":
                        c = j.text
                dict_list[a1].append(b + "-*-" + c + "-*-" + d1)


def data_box(yaml_pre, yaml_now):
    if yaml_pre in dict_list.keys() and dict_list[yaml_pre][0].split("-*-")[1] in suse_group_info.keys():
        group_str = dict_list[yaml_pre][0].split("-*-")[1]
        logging.info("-----{0}-----*******{1}******".format(yaml_pre, group_str))
        des_str = ""
        rpm_str = dict_list[yaml_pre][0].split("-*-")[2]
        for str_dig in rpm_str.split(" "):
            str_dig = "".join(filter(str.isalpha, str_dig.strip()))
            des_str = des_str + str_dig + " "
        license_str = dict_list[yaml_pre][0].split("-*-")[0]
        if group_str in suse_group_info.keys():
            d_oepkg[suse_group_info[group_str].split("/", 1)[0].replace(" ", "")].append(
                yaml_now + "-+-" + des_str + "-+-" + license_str + "-+-" + group_str + "-+-" +
                suse_group_info[group_str].split("/", 1)[1])
    else:
        group_str = shell_cmd("Group", d_dict[yaml_pre][0])
        license_str = shell_cmd("License", d_dict[yaml_pre][0])
        des_str = ""
        rpm_str = shell_cmd("Summary", d_dict[yaml_pre][0])
        for str_dig in rpm_str.split(" "):
            str_dig = "".join(filter(str.isalpha, str_dig.strip()))
            des_str = des_str + str_dig + " "
        if group_str in group_dict.keys():
            d_oepkg[group_dict[group_str].split("/", 1)[0].replace(" ", "")].append(
                yaml_now + "-+-" + des_str + "-+-" + license_str + "-+-" + group_str + "-+-" +
                group_dict[group_str].split("/", 1)[1])
        else:
            yaml_liu.append(yaml_pre)


def yaml_isexist(d_oepkg_key):
    sig_code_str = {}
    for i, item in enumerate(d_oepkg[d_oepkg_key]):
        if i == 0:
            sig_code_str = sig_info("config/sig-info.yaml", d_oepkg_key, item.split("-+-")[0], item.split("-+-")[4])
            base64_encode("config/test.yaml", d_oepkg_key, item)
        else:
            type_str = [j for j, i in enumerate(sig_code_str['repositories']) if
                        i.get("type") == item.split("-+-")[4]]
            if len(type_str) == 0:
                sig_code_str['repositories'].append(
                    {'repo': ['src-oepkgs/' + item.split("-+-")[0]], 'type': item.split("-+-")[4]})
            else:
                sig_code_str['repositories'][type_str[0]]['repo'].append("src-oepkgs/" + item.split("-+-")[0])
            base64_encode("config/test.yaml", d_oepkg_key, item)
    logging.info("---------sig_code_str----------")
    code_str = base64.b64encode(
        yaml.dump(sig_code_str, allow_unicode=True, default_flow_style=False, sort_keys=False).encode(
            'utf-8')).decode('utf-8')
    os.system(
        "{} 'https://gitee.com/api/v5/repos/zhang-yn/oepkgs-management_1/contents/sig%2F{}%2Fsig-info.yaml' -d '{{\"access_token\":\"{}\",\"content\":\"{}\",\"message\":\"test\"}}'".format(
            rq_header, d_oepkg_key, api_token, code_str))
    logging.info("---------sig_code_str end----------")


def yaml_not_exist(d_oepkg_key):
    sig_code_str = {}
    for j, items in enumerate(d_oepkg[d_oepkg_key]):
        if j == 0:
            sig_code_str = sig_info("./oepkgs-management_1/sig/{}/sig-info.yaml".format(d_oepkg_key), d_oepkg_key,
                                    items.split("-+-")[0], items.split("-+-")[4])
            base64_encode("config/test.yaml", d_oepkg_key, items)
        else:
            base64_encode("config/test.yaml", d_oepkg_key, items)
            type_str = [j for j, i in enumerate(sig_code_str['repositories']) if
                        i.get("type") == items.split("-+-")[4]]
            if len(type_str) == 0:
                sig_code_str['repositories'].append(
                    {'repo': ['src-oepkgs/' + items.split("-+-")[0]], 'type': items.split("-+-")[4]})
            else:
                sig_code_str['repositories'][type_str[0]]['repo'].append("src-oepkgs/" + items.split("-+-")[0])
    code_str = base64.b64encode(
        yaml.dump(sig_code_str, allow_unicode=True, default_flow_style=False, sort_keys=False).encode(
            'utf-8')).decode('utf-8')
    sig_info_add(d_oepkg_key, code_str)


def sig_info(yaml_module, sig_name, yaml_name, group_secdir):
    with open(r"{}".format(yaml_module), 'r', encoding='utf-8') as f:
        config = yaml.load(f.read().format(sig_name), Loader=yaml.FullLoader)
        if yaml_module == "sig-info.yaml":
            config['repositories'] = [{'repo': ['src-oepkgs/' + yaml_name], 'type': group_secdir}]
        else:
            type_str = [j for j, i in enumerate(config['repositories']) if i.get("type") == group_secdir]
            if len(type_str) == 0:
                config['repositories'].append({'repo': ['src-oepkgs/' + yaml_name], 'type': group_secdir})
            else:
                config['repositories'][type_str[0]]['repo'].append("src-oepkgs/" + yaml_name)
        return config


def sig_info_add(sig_name, str_content):
    response_url = requests.get(
        "https://gitee.com/api/v5/repos/zhang-yn/oepkgs-management_1/contents/sig%2F{}%2Fsig-info.yaml?access_token={}".format(
            sig_name, api_token), headers=headers)
    pr_num = json.loads(response_url.text)
    os.system(
        "curl -X PUT --header 'Content-Type: application/json;charset=UTF-8' 'https://gitee.com/api/v5/repos/zhang-yn/oepkgs-management_1/contents/sig%2F{}%2Fsig-info.yaml' -d '{{\"access_token\":\"{}\",\"content\":\"{}\", \"sha\":\"{}\", \"message\":\"test\"}}'".format(
            sig_name, api_token, str_content, pr_num["sha"]))
