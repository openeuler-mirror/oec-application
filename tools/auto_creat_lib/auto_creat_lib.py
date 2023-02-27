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
# Desc: Submit oec-hardware job automatically on compass-ci

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

srcOepkgsNum = 0
allFileNum = 0
allFileList = []  # 存放 当前路径 以及当前路径的子路径 下的所有文件
sigInfolist = []
allYamlList = []
rpm_info = {}
d = defaultdict(list)
d_oepkg = defaultdict(list)
dict_list = defaultdict(list)
rq_header = "curl -X POST --header 'Content-Type: application/json;charset=UTF-8'"
real_path = os.path.dirname(os.path.realpath(__file__)) + "/"

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
              "Development/Languages/C and C++": "evelopment environment/C++",
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
              "Utilities/System": "other", "Utility/Libraries": "basic system/basic library",
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
        config = yaml.load(f.read().format(yaml_str.split("-+-")[0], yaml_str.split("-+-")[1], yaml_str.split("-+-")[2],yaml_str.split("-+-")[3]), Loader=yaml.FullLoader)
        print(config)
        encode_str = base64.b64encode(yaml.dump(config, allow_unicode=True, default_flow_style=False, sort_keys=False).encode('utf-8')).decode('utf-8')
        print("{} 'https://gitee.com/api/v5/repos/zhang-yn/oepkgs-management_5/contents/sig%2F{}%2Fsrc-oepkgs%2F{}%2F{}.yaml' -d '{{\"access_token\":\"{}\",\"content\":\"{}\",\"message\":\"test\"}}'".format(rq_header, group_dir, str.lower(rpm_name[0]), rpm_name, api_token, encode_str))
        os.system("{} 'https://gitee.com/api/v5/repos/zhang-yn/oepkgs-management_5/contents/sig%2F{}%2Fsrc-oepkgs%2F{}%2F{}.yaml' -d '{{\"access_token\":\"{}\",\"content\":\"{}\",\"message\":\"test\"}}'".format(rq_header, group_dir, str.lower(rpm_name[0]), rpm_name, api_token, encode_str))
        print("--------- yaml_end ------------")
        # return encode_str


# 获取rpm信息，拿到name和description
def shell_cmd(rpm_key, path):
    rpm_info = os.popen("rpm -qi {}".format(path))
    for line in rpm_info.read().splitlines():
        rpm_value = line.split(":")
        if rpm_value[0].strip() == rpm_key:
            return rpm_value[1].strip()


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
            type_str = [j for j,i in enumerate(config['repositories']) if i.get("type") == group_secdir]
            print(type_str)
            if len(type_str)==0:
                config['repositories'].append({'repo': ['src-oepkgs/' + yaml_name], 'type': group_secdir})
            else:
                config['repositories'][type_str[0]]['repo'].append("src-oepkgs/" + yaml_name)
        return config

def sig_info_add(sig_name,str_content):
    print("--------- info ----------")
    print("----------------")
    print("https://gitee.com/api/v5/repos/zhang-yn/oepkgs-management_5/contents/sig%2F{}%2Fsig-info.yaml?access_token={}".format(sig_name, api_token))
    print("****************")
    response_url = requests.get("https://gitee.com/api/v5/repos/zhang-yn/oepkgs-management_5/contents/sig%2F{}%2Fsig-info.yaml?access_token={}".format(sig_name, api_token), headers=headers)
    pr_num = json.loads(response_url.text)
    print("curl -X PUT --header 'Content-Type: application/json;charset=UTF-8' 'https://gitee.com/api/v5/repos/zhang-yn/oepkgs-management_5/contents/sig%2F{}%2Fsig-info.yaml' -d '{{\"access_token\":\"{}\", \"sha\":\"{}\", \"message\":\"test\"}}'".format(sig_name,api_token,pr_num["sha"]))
    os.system("curl -X PUT --header 'Content-Type: application/json;charset=UTF-8' 'https://gitee.com/api/v5/repos/zhang-yn/oepkgs-management_5/contents/sig%2F{}%2Fsig-info.yaml' -d '{{\"access_token\":\"{}\",\"content\":\"{}\", \"sha\":\"{}\", \"message\":\"test\"}}'".format(sig_name,api_token,str_content,pr_num["sha"]))


def listen_event(pr_num):
    response = requests.get(
        "https://gitee.com/api/v5/repos/oepkgs/oepkgs-management_5/pulls/{0}/merge?access_token={1}".format(pr_num,
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
                d = i.text
            if i.tag[39:] == "name":
                a = i.text
                # print(a)
            # sys.exit()
            if i.tag[39:] == "format":
                for j in i:
                    if j.tag[36:]=="license":
                        b = j.text
                        # print(b)
                    if j.tag[36:]=="group":
                        # print(c)
                        c = j.text
                dict_list[a].append(b+"-*-"+c+"-*-"+d)

a = {"Amusement/other":"multimedia/game","Amusements/Games/3D/Other":"multimedia/game","Amusements/Games/3D/Race":"multimedia/game","Amusements/Games/3D/Shoot":"multimedia","Amusements/Games/3D/Simulation":"multimedia","Amusements/Games/Action/Arcade":"multimedia","Amusements/Games/Action/Breakout":"multimedia","Amusements/Games/Action/Other":"multimedia","Amusements/Games/Action/Race":"multimedia","Amusements/Games/Action/Shoot":"multimedia","Amusements/Games/Board/Card":"multimedia","Amusements/Games/Board/Chess":"multimedia","Amusements/Games/Board/Other":"multimedia","Amusements/Games/Board/Pool":"multimedia","Amusements/Games/Board/Puzzle":"multimedia","Amusements/Games/Logic":"multimedia","Amusements/Games/Other":"multimedia","Amusements/Games/RPG":"multimedia","Amusements/Games/Strategy/Other":"multimedia","Amusements/Games/Strategy/Real Time":"multimedia","Amusements/Games/Strategy/Turn Based":"multimedia","Amusements/Teaching/Language":"multimedia","Amusements/Teaching/Mathematics":"multimedia","Amusements/Teaching/Other":"multimedia","Amusements/Toys/Background":"multimedia","Amusements/Toys/Clocks":"multimedia","Amusements/Toys/Graphics":"multimedia","Amusements/Toys/Other":"multimedia","Amusements/Toys/Screensavers":"multimedia","Development/Languages/C and C++":"developmentenvironment","Development/Languages/Fortran":"developmentenvironment","Development/Languages/Go":"developmentenvironment","Development/Languages/Haskell":"developmentenvironment","Development/Languages/Java":"developmentenvironment","Development/Languages/Lua":"developmentenvironment","Development/Languages/NodeJS":"developmentenvironment","Development/Languages/OCaml":"developmentenvironment","Development/Languages/Other":"developmentenvironment","Development/Languages/Perl":"developmentenvironment","Development/Languages/PHP":"developmentenvironment","Development/Languages/Python":"developmentenvironment","Development/Languages/Ruby":"developmentenvironment","Development/Languages/Rust":"developmentenvironment","Development/Languages/Scheme":"developmentenvironment","Development/Languages/Tcl":"developmentenvironment","Development/Languages/Perl *.spec -> 29":"developmentenvironment","Development/Libraries/Perl *.spec -> 1127":"developmentenvironment","Development/Languages/Python *.spec -> 1190":"developmentenvironment","Development/Libraries/Python *.spec -> 404":"developmentenvironment","Development/Libraries/C and C++":"developmentenvironment","Development/Libraries/Cross":"developmentenvironment","Development/Libraries/GNOME":"basicsystem","Development/Libraries/Haskell":"developmentenvironment","Development/Libraries/Java":"developmentenvironment","Development/Libraries/KDE":"basicsystem","Development/Libraries/Other":"basicsystem","Development/Libraries/Parallel":"basicsystem","Development/Libraries/Perl":"developmentenvironment","Development/Libraries/PHP":"developmentenvironment","Development/Libraries/Python":"developmentenvironment","Development/Libraries/Rust":"developmentenvironment","Development/Libraries/Tcl":"developmentenvironment","Development/Libraries/X11":"basicsystem","Development/Libraries/YaST":"basicsystem","Development/Tools/Building":"developmentenvironment","Development/Tools/Debuggers":"developmentenvironment","Development/Tools/Doc Generators":"developmentenvironment","Development/Tools/GUI Builders":"developmentenvironment","Development/Tools/IDE":"developmentenvironment","Development/Tools/Navigators":"developmentenvironment","Development/Tools/Other":"developmentenvironment","Development/Tools/Version Control":"developmentenvironment","Hardware/Camera":"hardware","Hardware/Fax":"hardware","Hardware/ISDN":"hardware","Hardware/Joystick":"hardware","Hardware/Mobile":"hardware","Hardware/Modem":"hardware","Hardware/Other":"hardware","Hardware/Palm":"hardware","Hardware/Printing":"hardware","Hardware/Psion":"hardware","Hardware/Radio":"hardware","Hardware/Scanner":"hardware","Hardware/TV":"hardware","Hardware/UPS":"hardware","Hardware/Wifi":"hardware","Meta packages":"hardware","Multimedia":"multimedia","Productivity/Archiving/Backup":"middleware","Productivity/Archiving/Compression":"middleware","Productivity/Clustering/Computing":"middleware","Productivity/Clustering/HA":"middleware","Productivity/Databases/Clients":"database","Productivity/Databases/Servers":"middleware","Productivity/Databases/Tools":"middleware","File utilities":"middleware","Productivity/File utilities":"middleware","Graphics":"multimedia","Productivity/Graphics/3D Editors":"multimedia","Productivity/Graphics/Bitmap Editors":"multimedia","Productivity/Graphics/CAD":"multimedia","Productivity/Graphics/Convertors (Converters?)":"multimedia","Productivity/Graphics/Other":"multimedia","Productivity/Graphics/Vector Editors":"multimedia","Productivity/Graphics/Viewers":"multimedia","Productivity/Graphics/Visualization/Graph":"multimedia","Productivity/Graphics/Visualization/Other":"multimedia","Productivity/Graphics/Visualization/Raytracers":"multimedia","Hamradio":"middleware","Productivity/Hamradio/Fax":"middleware","Productivity/Hamradio/Logging":"middleware","Productivity/Hamradio/Morse":"middleware","Productivity/Hamradio/Other":"middleware","Productivity/Hamradio/Packet":"middleware","Productivity/Hamradio/Psk31":"middleware","Productivity/Hamradio/Satellite":"middleware","Productivity/Multimedia/CD/Grabbers":"multimedia","Productivity/Multimedia/CD/Players":"multimedia","Productivity/Multimedia/CD/Record":"multimedia","Productivity/Multimedia/Other":"multimedia","Productivity/Multimedia/Sound/Editors and Convertors":"multimedia","Productivity/Multimedia/Sound/Midi":"multimedia","Productivity/Multimedia/Sound/Mixers":"multimedia","Productivity/Multimedia/Sound/Players":"multimedia","Productivity/Multimedia/Sound/Utilities":"multimedia","Productivity/Multimedia/Sound/Visualization":"multimedia","Productivity/Multimedia/Video/Editors and Convertors":"multimedia","Productivity/Multimedia/Video/Players":"multimedia","Networking":"multimedia","Productivity/Networking/AOLInstantMessenger":"network","Productivity/Networking/Archie":"network","Productivity/Networking/Boot/Clients":"network","Productivity/Networking/Boot/Servers":"network","Productivity/Networking/Boot/Utilities":"network","Productivity/Networking/Diagnostic":"network","Productivity/Networking/DNS/Servers":"network","Productivity/Networking/DNS/Utilities":"network","Productivity/Networking/Email/Clients":"network","Productivity/Networking/Email/Mailinglists":"network","Productivity/Networking/Email/Servers":"network","Productivity/Networking/Email/Utilities":"network","Productivity/Networking/File-Sharing":"network","Productivity/Networking/Ftp/Clients":"network","Productivity/Networking/Ftp/Servers":"network","Productivity/Networking/ICQ":"network","Productivity/Networking/Instant Messenger":"network","Productivity/Networking/IRC":"network","Productivity/Networking/LDAP/Clients":"network","Productivity/Networking/LDAP/Servers":"network","Productivity/Networking/LDAP/Utilities":"network","Productivity/Networking/Napster":"network","Productivity/Networking/News/Clients":"network","Productivity/Networking/News/Servers":"network","Productivity/Networking/News/Utilities":"network","Productivity/Networking/NFS":"network","Productivity/Networking/NIS":"network","Productivity/Networking/Novell":"network","Productivity/Networking/Other":"network","Productivity/Networking/PPP":"network","Productivity/Networking/Radius/Clients":"network","Productivity/Networking/Radius/Servers":"network","Productivity/Networking/Routing":"network","Productivity/Networking/Samba":"network","Productivity/Networking/Security":"network","Productivity/Networking/SSH":"network","Productivity/Networking/System":"network","Productivity/Networking/Talk/Clients":"network","Productivity/Networking/Talk/Servers":"network","Productivity/Networking/Web/Browsers":"network","Productivity/Networking/Web/Frontends":"network","Productivity/Networking/Web/Proxy":"network","Productivity/Networking/Web/Servers":"network","Productivity/Networking/Web/Utilities":"network","Productivity/Office/Dictionary":"desktop","Productivity/Office/Finance":"desktop","Productivity/Office/Management":"desktop","Productivity/Office/Organizers":"desktop","Productivity/Office/Other":"desktop","Productivity/Office/Spreadsheets":"desktop","Productivity/Office/Suite":"desktop","Productivity/Office/Word Processor":"desktop","Productivity/Other":"desktop","Productivity/Publishing/DocBook":"basicsystem","Productivity/Publishing/HTML/Editors":"basicsystem","Productivity/Publishing/HTML/Tools":"basicsystem","Productivity/Publishing/Other":"basicsystem","Productivity/Publishing/PDF":"basicsystem","Productivity/Publishing/Presentation":"basicsystem","Productivity/Publishing/PS":"basicsystem","Productivity/Publishing/SGML":"basicsystem","Productivity/Publishing/TeX/Base":"basicsystem","Productivity/Publishing/TeX/Fonts":"basicsystem","Productivity/Publishing/TeX/Frontends":"basicsystem","Productivity/Publishing/TeX/Utilities":"basicsystem","Productivity/Publishing/Texinfo":"basicsystem","Productivity/Publishing/Troff":"basicsystem","Productivity/Publishing/Word":"basicsystem","Productivity/Publishing/XML":"basicsystem","Productivity/Scientific/Astronomy":"middleware","Productivity/Scientific/Chemistry":"middleware","Productivity/Scientific/Electronics":"middleware","Productivity/Scientific/Math":"middleware","Productivity/Scientific/Other":"middleware","Productivity/Scientific/Physics":"middleware","Productivity/Security":"middleware","Productivity/Telephony/Clients":"middleware","Productivity/Telephony/H323/Clients":"middleware","Productivity/Telephony/H323/Servers":"middleware","Productivity/Telephony/H323/Utilities":"middleware","Productivity/Telephony/Servers":"middleware","Productivity/Telephony/SIP/Clients":"middleware","Productivity/Telephony/SIP/Servers":"middleware","Productivity/Telephony/SIP/Utilities":"middleware","Productivity/Telephony/Utilities":"middleware","Productivity/Text/Convertors":"middleware","Productivity/Text/Editors":"middleware","Productivity/Text/Spell":"middleware","Productivity/Text/Utilities":"middleware","System/Emulators/Other":"hardware","System/Emulators/PC":"hardware","GUI":"desktop","System/GUI/GNOME":"desktop","System/GUI/KDE":"desktop","System/GUI/LXDE":"desktop","System/GUI/Other":"desktop","System/GUI/XFCE":"desktop","System/I18n/Chinese":"basicsystem","System/I18n/Japanese":"basicsystem","System/I18n/Korean":"basicsystem","X11":"desktop","System/X11/Displaymanagers":"desktop","System/X11/Fonts":"desktop","System/X11/Icons":"desktop","System/X11/Servers/XF86_3":"desktop","System/X11/Servers/XF86_4":"desktop","System/X11/Terminals":"desktop","System/X11/Utilities":"desktop","YaST":"systemtools","System/YaST":"systemtools","Internationalization (I18N)":""}



if __name__ == '__main__':
    # 读取rpm包名存入列表内
    # rpm_pkg_path = input("请输入要获取的rpm包目录：")
    # api_token = input("请输入api的token：")
    headers = {"Content-Type": "application/json;charset=UTF-8"}
    rpm_pkg_path = "/srv/rpm/pub/openeuler-22.03-LTS/"
    #rpm_pkg_path = "contrib"
    api_token = "a52c89251c94583ab0288d86c9711bb8"
    # 取rpm包总数和rpm文件绝对路径
    getAllFilesInPath(rpm_pkg_path)
    print("当前路径下的总文件数 =", allFileNum)
    for rpm_path in allFileList:
        rpm_file = shell_cmd("Name", rpm_path)  # 获取rpm信息
        d[rpm_file].append(rpm_path)
    os.system("git clone 'https://gitee.com/zhang-yn/oepkgs-management_5.git';")
    # 获取src-oepkgs上已经存在的库，通过yaml文件获取
    getAllFilesInPath("./oepkgs-management_5/sig")
    # 判断取到的rpm文件是否在舱内已经存在，进行过滤
    for i in allYamlList:
        if i in d.keys():
            d.pop(i)
    print("-----*****-----")
    print(len(d.keys()))
    #sys.exit()
    # 遍历字典进行yaml创建
    for yaml_modify in d:
        yaml_file = ""
        if yaml_modify[0].isdigit():
            for i, item in enumerate(yaml_modify):
                if not item.isdigit() and item == "-":
                    yaml_file = yaml_modify[i + 1:] + "-" + yaml_modify[:i]
                    break
                elif not item.isdigit() and item != "-":
                    yaml_file = yaml_modify[i + 1:] + yaml_modify[:i]
                    break
        else:
            yaml_file = yaml_modify
        if yaml_modify in dict_list.keys():
            group_str = dict_list[yaml_modify][0].split("-*-")[0]
            des_str = dict_list[yaml_modify][0].split("-*-")[2]
            license_str = dict_list[yaml_modify][0].split("-*-")[1]
            if group_str in a.keys():
                d_oepkg[a[group_str]].append(yaml_file+"-+-"+des_str+"-+-"+license_str+"-+-"+group_str)
            # if yaml_modify[0].isdigit():
            #     pass
        else:
            group_str = shell_cmd("Group", d[yaml_modify][0])
            license_str = shell_cmd("License", d[yaml_modify][0])
            des_str = ""
            rpm_str = shell_cmd("Summary", d[yaml_modify][0])
            for str_dig in rpm_str.split(" "):
                str_dig = "".join(filter(str.isalpha, str_dig))
                des_str = des_str + str_dig + " "
            if group_str in group_dict.keys():
                d_oepkg[group_dict[group_str]].append(yaml_file+"-+-"+des_str+"-+-"+license_str+"-+-"+group_str)
    print(d_oepkg)
    for d_oepkg_key in d_oepkg.keys():
        group_dir = d_oepkg_key.split("/", 1)[0].replace(" ","")
        group_secdir = d_oepkg_key.split("/", 1)[1]
        sig_code_str = {}
        if not os.path.exists(real_path +"/oepkgs-management_5/sig/{}/sig_info.yaml".format(group_dir)):
            for i, item in enumerate(d_oepkg[d_oepkg_key]):
                print("44-------------------")
                print(real_path +"oepkgs-management_5/sig/{}/sig_info.yaml".format(group_dir))
                print("222--------------")
                if i == 0:
                    print("bbbbbbb-----------ddddddddddd")
                    sig_code_str = sig_info("sig-info.yaml", group_dir, item.split("-+-")[0], group_secdir)
                    print(sig_code_str)
                    print("dddddd-------------------bbbbb")
                    base64_encode("./test.yaml", group_dir, item)
                else:
                    print("333------------------")
                    sig_code_str['repositories'][0]['repo'].append("src-oepkgs/" + item.split("-+-")[0])
                    base64_encode("./test.yaml", group_dir, item)
            print("---------sig_code_str----------")
            print(sig_code_str)
            code_str = base64.b64encode(yaml.dump(sig_code_str, allow_unicode=True, default_flow_style=False, sort_keys=False).encode('utf-8')).decode('utf-8')
            os.system("{} 'https://gitee.com/api/v5/repos/zhang-yn/oepkgs-management_5/contents/sig%2F{}%2Fsig-info.yaml' -d '{{\"access_token\":\"{}\",\"content\":\"{}\",\"message\":\"test\"}}'".format(rq_header, group_dir, api_token, code_str))
            print("---------sig_code_str end----------")
        else:
            for j, items in enumerate(d_oepkg[d_oepkg_key]):
                print("dddddd-------------------")
                if j == 0:
                    sig_code_str = sig_info("./oepkgs-management_5/sig/{}/sig-info.yaml".format(group_dir), group_dir,  items.split("/", 1)[0], group_secdir)
                    print("-------000000-------")
                    print(sig_code_str)
                    base64_encode("./test.yaml", group_dir, items)
                else:
                    print(sig_code_str)
                    print("------22222222-------")
                    base64_encode("./test.yaml", group_dir, items)
                    type_str = [j for j,i in enumerate(sig_code_str['repositories']) if i.get("type") == group_secdir]
                    if len(type_str)==0:
                        sig_code_str['repositories'].append({'repo': ['src-oepkgs/' + items.split("/", 1)[0]], 'type': group_secdir})
                    else:
                        sig_code_str['repositories'][type_str[0]]['repo'].append( "src-oepkgs/" + items.split("/", 1)[0])
            print("dddddd-------------------")
            code_str = base64.b64encode(yaml.dump(sig_code_str, allow_unicode=True, default_flow_style=False, sort_keys=False).encode('utf-8')).decode('utf-8')
            sig_info_add(group_dir, code_str)

    print("---------------")
    print("配置文件创建完成")
    sys.exit()

    # 创建pr
    data = {"access_token": api_token, "title": "自动化创建库", "head": "zhang-yn:master", "base": "master"}
    response = requests.post("https://gitee.com/api/v5/repos/oepkgs/oepkgs-management_5/pulls", params=data,
    headers = headers)
    pr_num = json.loads(response.text)["number"]
    print(pr_num)
    listen_event(pr_num)

    rq_header = "curl -X POST --header 'Content-Type: application/json;charset=UTF-8'"
    api_token = "84ecf3c63f2b49cf2b809cba3e2ba091"
    for lib_name in d:
    # fork仓库
        os.system("{} 'https://gitee.com/api/v5/repos/src-oepkgs/{}/forks' -d '{{\"access_token\":\"{}\"}}'".format(rq_header,
                                                                                                  lib_name,
                                                                                                  api_token))
    module_name = d[lib_name]
    for rpm_path in module_name:
        rpm_version = rpm_path.split("/")[-1][:-8][len(lib_name) + 1:]
    # 解压rpm，git commit
        if not os.path.exists(lib_name):
            os.system(
        "git clone 'https://gitee.com/zhang-yn/{0}.git';cd {0};pwd;rm -rf *;rpm2cpio {1} | cpio -div;git add .;git commit -m '{2}';git push".format(
            lib_name, rpm_path, rpm_version))
            print(
            "git clone 'https://gitee.com/zhang-yn/{0}.git';cd {0};rm -rf README.*;rpm2cpio {1} | cpio -div;git add .;git commit -m '{2}';git push".format(
                lib_name, rpm_path, rpm_version))
        else:
            os.system(
            "cd {0};rm -rf *;rpm2cpio {1} | cpio -div;git add .;git commit -m '{2}';git push".format(lib_name,
                                                                                         rpm_path,
                                                                                         rpm_version))

# 创建pr
data = {"access_token": api_token, "title": "自动化创建库", "head": "zhang-yn:master",
"base": "openEuler-22.03-LTS"}
response = requests.post("https://gitee.com/api/v5/repos/wwccyang/{}/pulls".format(lib_name), params=data,
headers = rq_header)

# 取commit信息，打tag
response_url = requests.get(
"https://gitee.com/api/v5/repos/src-oepkgs/{}/commits?access_token={}=master&page=1&per_page=20".format(
    lib_name, api_token), headers = rq_header)
pr_num = json.loads(response_url.text)
for i in range(1, len(d[lib_name]) + 1):
    print("********************")
print(pr_num[i]["sha"])
commit_id = pr_num[i]["sha"]
tag_str = pr_num[i]["commit"]["message"]
os.system(
"{} 'https://gitee.com/api/v5/repos/zhang-yn/{}/tags' -d '{{\"access_token\":\"{}\",\"refs\":\"{}\",\"tag_name\":\"{}\"}}'".format(
    rq_header, lib_name, api_token, commit_id, "22.0-" + tag_str))