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

srcOepkgsNum = 0
allFileNum = 0
allFileList = []  # 存放 当前路径 以及当前路径的子路径 下的所有文件
sigInfolist = []
allYamlList = []
yaml_liu = []
d = defaultdict(list)
d_oepkg = defaultdict(list)
dict_list = defaultdict(list)
rq_header = "curl -X POST --header 'Content-Type: application/json;charset=UTF-8'"
real_path = os.path.dirname(os.path.realpath(__file__)) + "/"
ones = {1: "one", 2: "two", 3: "three", 4: "four",
        5: "five", 6: "six", 7: "seven", 8: "eight",
        9: "nine", 0: "zero"}

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
        getAllFilesInPath(path + "/" + dl)  # 递归获取当前目录下的文件夹内的文件


# base64编码，文件创建api接口需要
def base64_encode(path, group_dir, yaml_str):
    with open(r"{}".format(path), 'r', encoding='utf-8') as f:
        config = yaml.load(f.read().format(yaml_str.split("-+-")[0], yaml_str.split("-+-")[1], yaml_str.split("-+-")[2],
                                           yaml_str.split("-+-")[3]), Loader=yaml.FullLoader)
        print(config)
        encode_str = base64.b64encode(
            yaml.dump(config, allow_unicode=True, default_flow_style=False, sort_keys=False).encode('utf-8')).decode(
            'utf-8')
        print(
            "{} 'https://gitee.com/api/v5/repos/zhang-yn/oepkgs-management_1/contents/sig%2F{}%2Fsrc-oepkgs%2F{}%2F{}.yaml' -d '{{\"access_token\":\"{}\",\"content\":\"{}\",\"message\":\"test\"}}'".format(
                rq_header, group_dir, yaml_str.split("-+-")[0][0], yaml_str.split("-+-")[0], api_token, encode_str))
        os.system(
            "{} 'https://gitee.com/api/v5/repos/zhang-yn/oepkgs-management_1/contents/sig%2F{}%2Fsrc-oepkgs%2F{}%2F{}.yaml' -d '{{\"access_token\":\"{}\",\"content\":\"{}\",\"message\":\"test\"}}'".format(
                rq_header, group_dir, yaml_str.split("-+-")[0][0], yaml_str.split("-+-")[0], api_token, encode_str))
        print("--------- yaml_end ------------")


# 获取rpm信息，拿到name和description
def shell_cmd(rpm_key, path):
    rpm_info = os.popen("rpm -qi {}".format(path))
    for line in rpm_info.read().splitlines():
        rpm_value = line.split(":")
        if rpm_value[0].strip() == rpm_key:
            return rpm_value[1].strip()


def yamlName(yaml_data):
    name = ""
    if yaml_data[0].isdigit():
        for num, item in enumerate(yaml_data):
            if not item.isdigit() and item == "-":
                name = name + yaml_data[num + 1:]
                break
            elif not item.isdigit() and item != "-":
                name = name + yaml_data[num:]
                break
            else:
                name = name + ones[int(item)] + "-"
    elif "+" in yaml_data:
        name = yaml_data.replace("+", "plus")
    else:
        name = yaml_data
    logging.info("*****{}****".format(yaml_data))
    return name

# sig_info.yaml创建
def sig_info(yaml_module, sig_name, yaml_name, group_secdir):
    with open(r"{}".format(yaml_module), 'r', encoding='utf-8') as f:
        config = yaml.load(f.read().format(sig_name), Loader=yaml.FullLoader)
        print("------------------")
        print(yaml_module)
        print(config)
        if yaml_module == "sig-info.yaml":
            config['repositories'] = [{'repo': ['src-oepkgs/' + yaml_name], 'type': group_secdir}]
        else:
            print("111111111111111")
            type_str = [j for j, i in enumerate(config['repositories']) if i.get("type") == group_secdir]
            print(type_str)
            if len(type_str) == 0:
                config['repositories'].append({'repo': ['src-oepkgs/' + yaml_name], 'type': group_secdir})
            else:
                config['repositories'][type_str[0]]['repo'].append("src-oepkgs/" + yaml_name)
        return config


def sig_info_add(sig_name, str_content):
    print("--------- info ----------")
    print("----------------")
    print(
        "https://gitee.com/api/v5/repos/zhang-yn/oepkgs-management_1/contents/sig%2F{}%2Fsig-info.yaml?access_token={}".format(
            sig_name, api_token))
    print("****************")
    response_url = requests.get(
        "https://gitee.com/api/v5/repos/zhang-yn/oepkgs-management_1/contents/sig%2F{}%2Fsig-info.yaml?access_token={}".format(
            sig_name, api_token), headers=headers)
    pr_num = json.loads(response_url.text)
    print(
        "curl -X PUT --header 'Content-Type: application/json;charset=UTF-8' 'https://gitee.com/api/v5/repos/zhang-yn/oepkgs-management_1/contents/sig%2F{}%2Fsig-info.yaml' -d '{{\"access_token\":\"{}\", \"sha\":\"{}\", \"message\":\"test\"}}'".format(
            sig_name, api_token, pr_num["sha"]))
    os.system(
        "curl -X PUT --header 'Content-Type: application/json;charset=UTF-8' 'https://gitee.com/api/v5/repos/zhang-yn/oepkgs-management_1/contents/sig%2F{}%2Fsig-info.yaml' -d '{{\"access_token\":\"{}\",\"content\":\"{}\", \"sha\":\"{}\", \"message\":\"test\"}}'".format(
            sig_name, api_token, str_content, pr_num["sha"]))



# 监听pr
def listen_event(pr_num):
    response = requests.get(
        "https://gitee.com/api/v5/repos/oepkgs/oepkgs-management_1/pulls/{0}/merge?access_token={1}".format(pr_num,
                                                                                                             api_token),
        headers=headers)
    response_dict = json.loads(response.text)
    if "Pull Request已经合并" in response_dict:
        print("------------pr已合入-------------")
    else:
        time.sleep(600)
        listen_event(pr_num)


def source_ocde(xmlfile):
    Parse = parse(xmlfile)
    root = Parse.getroot()
    for child in root:
        a = ""
        b = ""
        c = ""
        for i in child:
            if i.tag[39:] == "summary":
                d1 = i.text
            if i.tag[39:] == "name":
                a = i.text
            if i.tag[39:] == "format":
                for j in i:
                    if j.tag[36:] == "license":
                        b = j.text
                    if j.tag[36:] == "group":
                        c = j.text
                dict_list[a].append(b + "-*-" + c + "-*-" + d1)


a = {"Amusement/other": "multimedia/game", "Amusements/Games/3D/Other": "multimedia/game",
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


def data(yaml_pre,yaml_now):
    if yaml_pre in dict_list.keys() and dict_list[yaml_pre][0].split("-*-")[1] in a.keys():
        group_str = dict_list[yaml_pre][0].split("-*-")[1]
        logging.info("-----{0}-----*******{1}******".format(yaml_pre, group_str))
        des_str = ""
        rpm_str = dict_list[yaml_pre][0].split("-*-")[2]
        for str_dig in rpm_str.split(" "):
            str_dig = "".join(filter(str.isalpha, str_dig.strip()))
            des_str = des_str + str_dig + " "
        license_str = dict_list[yaml_pre][0].split("-*-")[0]
        if group_str in a.keys():
            d_oepkg[a[group_str].split("/", 1)[0].replace(" ", "")].append(
                yaml_now + "-+-" + des_str + "-+-" + license_str + "-+-" + group_str + "-+-" +
                a[group_str].split("/", 1)[1])
    else:
        group_str = shell_cmd("Group", d[yaml_pre][0])
        license_str = shell_cmd("License", d[yaml_pre][0])
        des_str = ""
        rpm_str = shell_cmd("Summary", d[yaml_pre][0])
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
        print(real_path + "oepkgs-management_1/sig/{}/sig-info.yaml".format(d_oepkg_key))
        if i == 0:
            sig_code_str = sig_info("sig-info.yaml", d_oepkg_key, item.split("-+-")[0], item.split("-+-")[4])
            base64_encode("./test.yaml", d_oepkg_key, item)
        else:
            type_str = [j for j, i in enumerate(sig_code_str['repositories']) if
                        i.get("type") == item.split("-+-")[4]]
            if len(type_str) == 0:
                sig_code_str['repositories'].append(
                    {'repo': ['src-oepkgs/' + item.split("-+-")[0]], 'type': item.split("-+-")[4]})
            else:
                sig_code_str['repositories'][type_str[0]]['repo'].append("src-oepkgs/" + item.split("-+-")[0])
            base64_encode("./test.yaml", d_oepkg_key, item)
    logging.info("---------sig_code_str----------")
    print(sig_code_str)
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
            base64_encode("./test.yaml", d_oepkg_key, items)
        else:
            base64_encode("./test.yaml", d_oepkg_key, items)
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


def insert_data():
    for rpm_path in allFileList:
        rpm_file = shell_cmd("Name", rpm_path)  # 获取rpm信息
        d[rpm_file].append(rpm_path)
    os.system("git clone 'https://gitee.com/zhang-yn/oepkgs-management_1.git';")


if __name__ == '__main__':
    # 读取rpm包名存入列表内
    headers = {"Content-Type": "application/json;charset=UTF-8"}
    rpm_pkg_path = "/srv/rpm/pub/"
    # rpm_pkg_path = "contrib"
    api_token = "c4a7f2254bd58885a9c6fa80cbd0b7dc"
    # 取rpm包总数和rpm文件绝对路径
    getAllFilesInPath(rpm_pkg_path)
    print("当前路径下的总文件数 =", allFileNum)
    insert_data()
    # 获取src-oepkgs上已经存在的库，通过yaml文件获取
    getAllFilesInPath("./oepkgs-management_1/sig")
    # 判断取到的rpm文件是否在舱内已经存在，进行过滤)
    d_list = copy.deepcopy(d)
    for d_list_name in d_list:
        if d_list_name in allYamlList or str.lower(d_list_name) in allYamlList:
            d.pop(d_list_name)
    # 遍历字典进行yaml创建
    source_ocde("module2.xml")
    for yaml_modify in d:
        yaml_file = yamlName(yaml_modify)
        data(yaml_modify,yaml_file)
    logging.info("------- 剩余 ------")

    for oepkg_keys in d_oepkg.keys():
        if not os.path.exists(real_path + "/oepkgs-management_1/sig/{}/sig-info.yaml".format(oepkg_keys)):
            yaml_isexist(oepkg_keys)
        else:
            yaml_not_exist(oepkg_keys)