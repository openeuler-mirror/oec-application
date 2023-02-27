# !/usr/bin/python3
# -*- coding:UTF-8 -*-

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
from txdpy import get_Bletter,get_Sletter

srcOepkgsNum = 0
allFileNum = 0
allFileList = []  # 存放 当前路径 以及当前路径的子路径 下的所有文件
sigInfolist = []
allYamlList = []
filter_char = []
allYamldata = []
rpm_info = {}
d = defaultdict(list)
d_oepkg = defaultdict(list)
dict_list = defaultdict(list)
Inyaml = []

def getAllFilesInPath(path):
    #print(path)
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
                # yaml_name = f[:-5]
                # allYamlList.append(yaml_name)
                if path.split("/")[1] == "oepkgs-management_10":
                    allYamldata.append(os.path.abspath(path + "/" + f))
                elif path.split("/")[1] == "oepkgs-management":
                    Inyaml.append(f)
    for dl in curPathDirList:
        getAllFilesInPath(path + "/" + dl)  # 递归获取当前目录下的文件夹内的文件


def creat_pr():
    print("------ pr -------")
    data = {"access_token": api_token, "title": "自动化创建库", "head": "zhang-yn:master", "base": "master"}
    response = requests.post("https://gitee.com/api/v5/repos/oepkgs/oepkgs-management/pulls", params=data,headers=headers)
    #print(response)
    pr_num = json.loads(response.text)["number"]
    print("-------- waiting 10 minutes ---------")
    #sys.exit()
    time.sleep(150)
    response = requests.get("https://gitee.com/api/v5/repos/oepkgs/oepkgs-management/pulls/{}/merge?access_token={}".format(pr_num,api_token),headers=headers)
    response_dict = json.loads(response.text)
    if "message" in response_dict.keys():
        if response_dict['message'] == 'Pull Request已经合并':
            print("merge push")
    else:
        commit_id = os.popen(
                "{0} 'https://gitee.com/api/v5/repos/oepkgs/oepkgs-management/pulls/{1}' -d '{{\"access_token\":\"{2}\",\"state\":\"closed\"}}'".format(
                closed_header, pr_num, api_token)).read()
        response_json = json.loads(commit_id)
        if response_json["state"] == "closed":
            # print("******* 当前提交未合并 ********")
            creat_pr()
        else:
            print("---------- pr 未关闭 ------------")
            sys.exit()
        print("--- 已合入 ---")
    #time.sleep(300)
    #print(pr_num)
    #sys.exit()
    #os.system(
    #    "{} 'https://gitee.com/api/v5/repos/oepkgs/oepkgs-management/pulls/{}/comments' -d '{{\"access_token\":\"{}\"，\"body\":\"{}\"}}'".format(
    #        rq_header, pr_num, robot_token, "/lgtm"))
    #os.system(
    #    "{} 'https://gitee.com/api/v5/repos/oepkgs/oepkgs-management/pulls/{}/comments' -d '{{\"access_token\":\"{}\"，\"body\":\"{}\"}}'".format(
    #        rq_header, pr_num, robot_token, "/approve"))



# 监听pr
def listen_event(pr_num):
    response = requests.get(
        "https://gitee.com/api/v5/repos/oepkgs/oepkgs-management/pulls/{}/merge?access_token={}".format(pr_num,api_token),headers=headers)
    response_dict = json.loads(response.text)
    if not response_dict['message'] == 'Pull Request已经合并':
        time.sleep(300)
        listen_event(pr_num)




if __name__ == '__main__':
    # 读取rpm包名存入列表内
    # rpm_pkg_path = input("请输入要获取的rpm包目录：")
    # api_token = input("请输入api的token：")
    headers = {"Content-Type": "application/json;charset=UTF-8"}
    rpm_pkg_path = "/srv/rpm/pub/"
    rq_header = "curl -X POST --header 'Content-Type: application/json;charset=UTF-8'"
    real_path = os.path.dirname(os.path.realpath(__file__)) + "/"
    closed_header = "curl -X PATCH --header 'Content-Type: application/json;charset=UTF-8'"
    api_token = "c4a7f2254bd58885a9c6fa80cbd0b7dc"
    robot_token = "c951fee688f4b037d27602d7461b81fc"
    # 取rpm包总数和rpm文件绝对路径
    # getAllFilesInPath(rpm_pkg_path)

    a = 0
    # 创建pr
    os.system("git clone 'https://gitee.com/zhang-yn/oepkgs-management.git';")
    print("--------")
    getAllFilesInPath("./oepkgs-management_10/sig")
    getAllFilesInPath("./oepkgs-management/sig")
    print("********")
    #print(Inyaml)
    print("===========")
    print(len(allYamldata))
    print(len(set(allYamldata)))
    print("---------------")
    #b = [j.split("/")[-1] for j in allYamldata]
    #a = copy.deepcopy(b)
    #for i in a:
    #    if i in Inyaml:
    #        b.remove(i)
    #print(len(set(b)))
    #print(b)
    #sys.exit()
    #print(len(set([j.split("/")[-1][:-5] for j in allYamldata])))
    #c = [j.split("/")[-1][:-5] for j in allYamldata]
    #list_num = []
    #for i in c:
    #    if c.count(i) > 1:
    #        list_num.append(i)
    #print(list_num)
    #sys.exit()
    # if len(get_Bletter(i)) != 0:
    #for i, item in enumerate(allYamldata):
    #    if len(get_Bletter(item.split("/")[-1][:-5])) != 0:
    #        if item.split("/")[-1][:-5].lower() + ".yaml" not in Inyaml:
    #            if item.split("/")[-1][:-5].lower() in [j.split("/")[-1][:-5] for j in allYamldata]:
    #                print(item.split("/")[-1][:-5],item.split("/")[-1][:-5].lower(),item)
    #sys.exit()
    for i, item in enumerate(allYamldata):
        if len(get_Bletter(item.split("/")[-1][:-5])) != 0:
            if item.split("/")[-1][:-5].lower() + ".yaml" in Inyaml:
                # os.system("rm -rf {}".format(item))
                continue
        dest_path = real_path + "oepkgs-management" + "/"  + "/".join(item.split("/")[-5:-1]) + "/"
        #print(item)
        if item.split("/")[-1] not in Inyaml and len(item.split("/")[-1][:-5]) > 1 and len(item.split("/")[-1].split(".")) == 2:
            a = a + 1
            if not os.path.exists(dest_path):
                os.system("mkdir -p {0};cp -rf {1} {0}".format(dest_path,item))
            else:
                os.system("cp -rf {1} {0}".format(dest_path, item))
            if a >= 100:
                os.system("cd {0};git add .;git commit -m '自动化仓库创建';git push".format("oepkgs-management"))
                creat_pr()
                time.sleep(500)
                # print(a)
                a = 0
                print("--------")
                # print(a)
    # data = {"access_token": api_token, "title": "自动化创建库", "head": "zhang-yn:master", "base": "master"}
    # response = requests.post("https://gitee.com/api/v5/repos/oepkgs/oepkgs-management/pulls", params=data,
    # headers = headers)
    # pr_num = json.loads(response.text)["number"]
    # print(pr_num)
    # # listen_event(pr_num)





    print("------- creat end -------")
    sys.exit()
    for lib_name in d:
    # fork仓库
        os.system("{} 'https://gitee.com/api/v5/repos/src-oepkgs/{}/forks' -d '{{\"access_token\":\"{}\"}}'".format(rq_header,
                                                                                                  lib_name,
                                                                                                  api_token))
        module_name = d[lib_name]
        for rpm_path in module_name:
            rpm_version = rpm_path.split("/")[-1][:-8][len(lib_name) + 1:]
        # 解压rpm，git commit
            if not os.path.exists(real_path+lib_name):
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
