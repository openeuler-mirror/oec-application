# !/usr/bin/python3
# -*- coding:UTF-8 -*-

import base64
import sys
import os
import yaml
import json
import re
import requests
import time
import copy
from collections import defaultdict, OrderedDict
# import requests.adapters

from xml.etree.ElementTree import parse

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
rpm_info = {}
d = defaultdict(list)
d_oepkg = defaultdict(list)
dict_list = defaultdict(list)
ones = {1: "one", 2: "two", 3: "three", 4: "four",
        5: "five", 6: "six", 7: "seven", 8: "eight",
        9: "nine", 0: "zero"}


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
                # allYamldata.append(os.path.abspath(path + "/" + f))

    for dl in curPathDirList:
        getAllFilesInPath(path + "/" + dl)  # 递归获取当前目录下的文件夹内的文件


# 获取rpm信息，拿到name和description
def shell_cmd(rpm_key, path):
    rpm_info = os.popen("rpm -qi {}".format(path))
    for line in rpm_info.read().splitlines():
        rpm_value = line.split(":")
        if rpm_value[0].strip() == rpm_key:
            return rpm_value[1].strip()


if __name__ == '__main__':
    # 读取rpm包名存入列表内
    # rpm_pkg_path = input("请输入要获取的rpm包目录：")
    # api_token = input("请输入api的token：")
    if len(sys.argv) != 2:
        sys.exit()

    # requests.adapters.DEFUALT_RETRYS = 10
    headers = {"Content-Type":"application/json;charset=UTF-8","Connection":"close"}
    rpm_pkg_path = "/srv/rpm/pub/openeuler-20.03-LTS-SP1"
    rq_header = "curl -X POST --header 'Content-Type: application/json;charset=UTF-8'"
    real_path = os.path.dirname(os.path.realpath(__file__)) + "/"
    api_token = "c4a7f2254bd58885a9c6fa80cbd0b7dc"
    robot_token = "c951fee688f4b037d27602d7461b81fc"
    print("yaml文件的名字")
    #getAllFilesInPath("./oepkgs-management/sig")
    #print(allYamlList)
    with open("yaml_sp3.json", "r") as f:
        d = json.load(f)
    # print(d)
    #d_list = copy.deepcopy(d)
    #for i in d_list:
    #     if i != "openblas":
    #         del d[i]
    #     else:
    #         break
    print("---------------")
    print(len(d))
    print(d)
    #sys.exit()
    tag_num = 0
    for yaml_file in d:
        # # if yaml_modify not in allYamlList:
        # yaml_file = ""
        # if yaml_modify[0].isdigit():
        #     for i, item in enumerate(yaml_modify):
        #         if not item.isdigit() and item == "-":
        #             yaml_file = yaml_file + yaml_modify[i + 1:]
        #             # yaml_file = yaml_modify[i + 1:] + "-" + yaml_modify[:i]
        #             break
        #         elif not item.isdigit() and item != "-":
        #             yaml_file = yaml_file + yaml_modify[i:]
        #             # yaml_file = yaml_modify[i + 1:] + yaml_modify[:i]
        #             break
        #         else:
        #             yaml_file = yaml_file + ones[int(item)] + "-"
        # elif "+" in yaml_modify:
        #     yaml_file = yaml_modify.replace("+", "plus")
        # else:
        #     yaml_file = yaml_modify
        # print("*****{}****".format(yaml_modify))
        #
        # if yaml_file not in allYamlList:
        #     src_code_is.append(yaml_file)
        #     print("-------- out of yaml file --------")
        #     print(src_code_is)
        #     continue
        module_name = d[yaml_file]
        print("----module_name----")
        print(module_name)
        rpm_dict = {}
        version_set = set()
        for rpm_path in module_name:
            rpm_version = shell_cmd("Version", rpm_path)
            print(rpm_version)
            version_set.add(rpm_version)
            rpm_dict[rpm_version] = rpm_path
        version_list = list(version_set)
        version_list.sort()
        print("------++++++")
        print(version_list)
        print(rpm_dict)
        # sys.exit()
        print("+-+-+-+-+-+-+-+")
        # s = requests.session()
        # s.keep_alive = False
        # branch_data = os.popen("curl -X GET --header 'Content-Type: application/json;charset=UTF-8' 'https://gitee.com/api/v5/repos/src-oepkgs/{}/branches?access_token={}'".format(yaml_file, api_token)).read()
        # print("------ branch_data -----")
        # print(branch_data)
        # response_txt = json.loads(branch_data)
        # print("------ branch list -----")
        # print(response_txt)
        # branch_data.close()
        # branch_list = [i["name"] for i in response_txt]
        # print(branch_list)
        # print([i["name"] for i in response_txt])
        # if type(response_txt) == list:
        #     if sys.argv[1] not in [i["name"] for i in response_txt]:
        #         continue
        #     else:
        #         tag_num = tag_num + 1
        #         print("------ tag_num ------")
                # print(tag_num)
                # print("https://gitee.com/api/v5/repos/src-oepkgs/{0}/branches/{1}/setting?access_token={2}".format(
                #     yaml_file, sys.argv[1], api_token))
                # s = requests.session()
                # s.keep_alive = False
        os.system("curl -X DELETE --header 'Content-Type: application/json;charset=UTF-8' 'https://gitee.com/api/v5/repos/src-oepkgs/{}/branches/{}/setting?access_token={}'".format(yaml_file, sys.argv[1], api_token))
        for rpm_version in version_list:
            rpm_path = rpm_dict[rpm_version]
            # if not os.path.exists(real_path + yaml_file):
            # time.sleep(1)
            # s = requests.session()
            # s.keep_alive = False
            # tags_data = requests.get(
            #     "https://gitee.com/api/v5/repos/src-oepkgs/{}/tags?access_token={}".format(yaml_file,
            #                                                                                api_token),
            #     headers=headers)
            # response_txt = json.loads(tags_data.text)
            # tags_data.close()
            print("***************")
            # print(response_txt)
            # tag_list = [i["name"] for i in response_txt]
            # print(tag_list)
            # sys.exit()
            # if sys.argv[1] == "master":
            #     if "20.03-LTS-SP1" + "-v" + rpm_version in [i["name"] for i in response_txt]:
            #         os.chdir(os.path.pardir)
            #         os.system("rm -rf {0}".format(yaml_file))
            #         print(os.getcwd())
            #         src_code_is.append(yaml_file)
            #         print(src_code_is)
            #         # continue
            #         break
            # else:
            #     if sys.argv[1][10:] + "-v" + rpm_version in [i["name"] for i in response_txt]:
            #         os.chdir(os.path.pardir)
            #         os.system("rm -rf {0}".format(yaml_file))
            #         print(os.getcwd())
            #         src_code_is.append(yaml_file)
            #         print(src_code_is)
            #         # continue
            #         break
            if sys.argv[1] == "master":
                # if "20.03-LTS-SP1" + "-v" + rpm_version in [i["name"] for i in response_txt]:
                    # os.chdir(os.path.pardir)
                    # os.system("rm -rf {0}".format(yaml_file))
                    # print(os.getcwd())
                    # src_code_is.append(yaml_file)
                    # print(src_code_is)
                    # continue
                    # break
                os.system("git clone 'https://gitee.com/src-oepkgs/{0}.git';".format(yaml_file))
                print("-----git clone-----")
            else:
                # if sys.argv[1][10:] + "-v" + rpm_version in [i["name"] for i in response_txt]:
                    # os.chdir(os.path.pardir)
                    # os.system("rm -rf {0}".format(yaml_file))
                    # print(os.getcwd())
                    # src_code_is.append(yaml_file)
                    # print(src_code_is)
                    # continue
                    # break
                os.system("git clone -b {1} 'https://gitee.com/src-oepkgs/{0}.git';".format(yaml_file,
                                                                                            sys.argv[1]))
                print("-----git clone-----")
            if not os.path.exists(real_path + yaml_file):
                print("-------path is not exist-------")
                print("route is {}".format(os.path.exists(real_path + yaml_file)))
                break
            os.chdir(os.getcwd() + "/" + yaml_file)
            print(os.listdir("./"))


            print("-----****-----")
            print(os.getcwd())
            # print("-----****-----")
            os.system(
                "rm -rf *;rpm2cpio {0} | cpio -div;git add .;git commit -m '{1}';git push".format(rpm_path,
                                                                                                  rpm_version))
            print(os.getcwd())
            #os.chdir(os.path.pardir)
            #os.system("rm -rf {0}".format(yaml_file))
            print("-------- pwd --------")
            print(os.getcwd())
            # else:
            #     #print(os.getcwd())
            #     print("-------- pwd2 --------")
            #     print(os.getcwd())
            #     #os.chdir(os.getcwd() + "/" + yaml_file)
            #     os.system("rm -rf *;rpm2cpio {1} | cpio -div;git add .;git commit -m '{2}';git push".format(yaml_file,rpm_path,rpm_version))
            # time.sleep(3)
            # st = requests.session()
            # st.keep_alive = False
            # time.sleep(3)
            # response_url = os.popen("curl -X GET --header 'Content-Type: application/json;charset=UTF-8' 'https://gitee.com/api/v5/repos/src-oepkgs/{}/branches/{}?access_token={}'".format(yaml_file, sys.argv[1], api_token)).read()
            commit_id = os.popen("git rev-parse HEAD").read().strip()
            print(commit_id)
            os.chdir(os.path.pardir)
            os.system("rm -rf {0}".format(yaml_file))
            print("-------- pwd --------")
            print(os.getcwd())
            # response_json = json.loads(response_url)
            # response_url.close()
            # if response_json == {'message': 'Branch does not exist'}:
            #     print("-------")
            #     print("{} is 500".format(yaml_file))
            #     yaml_error.append(yaml_file)
            #     break
            print("------- sha value ------")
            # print(response_json)
            # commit_id = response_json["commit"]["sha"]
            print(commit_id)
            if sys.argv[1] == "master":
                os.system(
                    "{} 'https://gitee.com/api/v5/repos/src-oepkgs/{}/tags' -d '{{\"access_token\":\"{}\",\"refs\":\"{}\",\"tag_name\":\"{}\"}}'".format(
                        rq_header, yaml_file, api_token, commit_id, "20.03-LTS-SP1" + "-v" + rpm_version.replace("^",".").replace("~",".")))
            else:
                os.system(
                    "{} 'https://gitee.com/api/v5/repos/src-oepkgs/{}/tags' -d '{{\"access_token\":\"{}\",\"refs\":\"{}\",\"tag_name\":\"{}\"}}'".format(
                        rq_header, yaml_file, api_token, commit_id, sys.argv[1][10:] + "-v" + rpm_version.replace("^",".").replace("~",".")))
            print("------- 库名 ------")
            print(yaml_file)
        tag_num = tag_num + 1
        # time.sleep(1)
        # s = requests.session()
        # s.keep_alive = False
        # data = {"access_token": api_token}
        os.system("curl -X PUT --header 'Content-Type: application/json;charset=UTF-8' 'https://gitee.com/api/v5/repos/src-oepkgs/{}/branches/{}/protection' -d '{{\"access_token\":\"{}\"}}'".format(yaml_file, sys.argv[1], api_token))
        # r_pt.close()
        #sys.exit()
        src_code_up.append(yaml_file)
        # os.system("rm -rf {}".format(yaml_file))
        print(tag_num)
        print(src_code_up)
        #time.sleep(1)
# else:
#     src_code_is.append(yaml_file)
#     print(src_code_is)
#     time.sleep(2)
    #if tag_num >= 3:
    #    print("----- time sleep -----")
    #    #print(time.sleep(1800))
    #    print("----- time end -----")
    #    print("----tag_num------")
    #    sys.exit()
