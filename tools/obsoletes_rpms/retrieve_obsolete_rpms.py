# -*- encoding=utf-8 -*-
"""
# **********************************************************************************
# Copyright (c) Huawei Technologies Co., Ltd. 2020-2020. All rights reserved.
# [oecp] is licensed under the Mulan PSL v2.
# You can use this software according to the terms and conditions of the Mulan PSL v2.
# You may obtain a copy of Mulan PSL v2 at:
#     http://license.coscl.org.cn/MulanPSL2
# THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
# PURPOSE.
# See the Mulan PSL v2 for more details.
# Author:
# Create: 2023-03-18
# Description: Oecp tools
# **********************************************************************************
"""
import argparse
import csv
import json
import logging
import re
import shutil
import stat
import subprocess
import sys
from multiprocessing import Pool, cpu_count
import os.path
from urllib import request
from urllib.error import HTTPError
from urllib.parse import urljoin

import pandas as pd
import wget
from git import Repo, GitCommandError

from tools.logger import init_logger
from tools.shell_cmd import shell_cmd

logger = logging.getLogger('obsolete')


class RetrieveObsoleteRpms(object):

    def __init__(self, rpm_report):
        self.all_rpm_report = rpm_report
        self.src_openeuler = "https://gitee.com/src-openeuler/"

    @staticmethod
    def get_rpm_name(rpm):
        """
        返回rpm包名称
        :param rpm:
        :return:
        """
        m = re.match(r"^(.+)-.+-.+", rpm)

        if m:
            return m.group(1)
        else:
            return rpm

    @staticmethod
    def perform_cpio(package):
        """
        解压srpm包
        @param package: srpm包路径
        @return: None
        """
        stdin = subprocess.Popen(['rpm2cpio', package], stdout=subprocess.PIPE)
        p = subprocess.Popen(['cpio', '-d', '-i'],
                             stdin=stdin.stdout,
                             stdout=subprocess.DEVNULL,
                             stderr=subprocess.STDOUT)
        p.communicate(timeout=600)

    @staticmethod
    def combine_single_result(results, src_base, bin_rpm, category):
        single_result = {}
        single_result.setdefault('src_rpm', src_base)
        single_result.setdefault('binary_rpm', bin_rpm)
        single_result.setdefault('category', category)

        results.append(single_result)

    @staticmethod
    def re_component_results(results):
        component_results = []
        for bin_rpms in results.values():
            component_results.extend(bin_rpms)

        return component_results

    @staticmethod
    def obtain_spec_file(dir_spec, src_name):
        for file in os.listdir(dir_spec):
            if file.endswith(".spec"):
                return os.path.join(dir_spec, file)

        logger.warning(f"{src_name} spec file is not exists.")
        return ""

    @staticmethod
    def obtain_repository_name(src_name):
        rename_path = os.path.realpath(os.path.join(os.path.dirname(__file__), "conf/rename_repository.json"))
        with open(rename_path, "r") as rf:
            rename_repository = json.load(rf)
        src_repo_name = rename_repository.get(src_name, src_name) + ".git"

        return src_repo_name

    @staticmethod
    def prase_spec_with_plag(spec_path, src_name, flag):
        """
        prase obsolete rpms from download spec file.
        @param spec_path: path of spec file.
        @param src_name: source rpm name.
        @return:
        """
        obtain_results = []
        if not spec_path:
            return obtain_results

        cmd = f"rpmspec -q --{flag.lower()} {spec_path}"
        code, out, err = shell_cmd(cmd.split())
        if not code:
            if err:
                logger.debug(err)
            if out:
                for line in out.split("\n"):
                    if not line:
                        continue
                    symbol = re.search(r"[><=]=?", line)
                    if symbol:
                        tar_name = line.split(symbol.group())[0].strip()
                        obtain_results.append(tar_name)
                    else:
                        obtain_results.append(line.strip())
            else:
                logger.debug(f"{spec_path} not found {flag}.")
        else:
            logger.warning(f"Prase spec Error: {cmd}")
            with open(spec_path, "r") as spec_f:
                content = spec_f.read()
            name_version_pat = r"(\S+)\s([><=]=?)\s(\S+)"
            for line in content.split('\n'):
                pat = r"^" + flag + ":"
                if re.match(pat, line):
                    line_strips = re.sub(pat, '', line).strip()
                    match_result = re.findall(name_version_pat, line_strips)
                    if match_result:
                        for single_tar in match_result:
                            obtain_results.append(single_tar[0].replace("%{name}", src_name))
                    else:
                        component_tar = [single.replace("%{name}", src_name) for single in line_strips.split()]
                        obtain_results.extend(component_tar)

        return obtain_results

    @staticmethod
    def write_to_csv_report(results, branch, model, work_dir):
        header = results[0].keys()
        report_name = f"obsoletes_report_{branch}_{model}.csv"
        report_path = os.path.join(work_dir, report_name)
        # 创建并以读写方式打开一个新文件
        flags = os.O_RDWR | os.O_CREAT
        # owner有全部权限，其他用户有读权限
        modes = stat.S_IROTH | stat.S_IRWXU
        with os.fdopen(os.open(report_path, flags, modes), 'w', newline='', encoding='utf-8') as fd:
            f_csv = csv.DictWriter(fd, header)
            f_csv.writeheader()
            f_csv.writerows(results)

        logger.info(f"Success create obsoletes-rpms-report at: {report_path}")

    def acquire_detete_rpms(self, all_rpm_report, branch):
        """
        Resolve rpm packages removed from older versions.
        @param all_rpm_report: oecp generated all-rpm-report.csv
        @return -> Dict: Deleted src rpm mapping to binary rpms.

        format eg:
        {
            "cookxml": {
                "result": [
                    {
                        "src_rpm": "cookxml-x.x.x-x.oe1.src.rpm",
                        "binary_rpm": "cookxml-x.x.x-x.oe1.noarch.rpm",
                        "category": "src_detete"
                    }
                ],
                "src_update": ""
            },
            "boost": {
                "result": [
                    {
                        "src_rpm": "boost-x.x.x-x.oe1.src.rpm",
                        "binary_rpm": "boost-build-x.x.x-x.oe1.noarch.rpm",
                        "category": "bin_rpm_detete"
                    },
                    {
                        "src_rpm": "boost-x.x.x-x.oe1.src.rpm",
                        "binary_rpm": "boost-doctools-x.x.x-x.oe1.aarch64.rpm",
                        "category": "bin_rpm_detete"
                    },
                    {
                        "src_rpm": "boost-x.x.x-x.oe1.src.rpm",
                        "binary_rpm": "boost-examples-x.x.x-x.oe1.noarch.rpm",
                        "category": "bin_rpm_detete"
                    }
                    ...
                ],
                "src_update": "boost-x.x.x-x.oe2203sp1.src.rpm"
            }

            ...

        }

        """
        results = {}
        df = pd.read_csv(all_rpm_report)
        df.drop(df.index[0], inplace=True)
        header = df.columns.tolist()
        if branch not in header[3]:
            logger.error("please check input branch is diff from all-rpm-report second iso name.")
            return results
        delete_rpms = df.loc[(df[header[4]] == '4') | (df[header[4]] == 4)]
        series_delete_src = delete_rpms.astype(str)[header[1]]
        all_new_src = df.loc[(df[header[3]].notnull())]
        all_new_src_name = {}
        for i in all_new_src.astype(str)[header[3]]:
            all_new_src_name.setdefault(self.get_rpm_name(i), i)
        all_new_bin_name = [self.get_rpm_name(j) for j in all_new_src.astype(str)[header[2]]]
        map_delete_rpms = self.calculate_same_rpms(series_delete_src, delete_rpms, header)

        for delete_rpm, bin_rpms in map_delete_rpms.items():
            src_full_name = bin_rpms['src_rpm']
            results.setdefault(delete_rpm, {
                "result": [],
                "src_update": all_new_src_name.get(delete_rpm, '')
            })
            if delete_rpm not in set(all_new_src_name.keys()):
                for rpm in bin_rpms['rpms']:
                    self.combine_single_result(results.get(delete_rpm).get("result"), src_full_name, rpm, 'src_detete')
            else:
                for rpm in bin_rpms['rpms']:
                    if self.get_rpm_name(rpm) in all_new_bin_name:
                        self.combine_single_result(results.get(delete_rpm).get("result"), src_full_name, rpm,
                                                   'arch_change')
                    else:
                        self.combine_single_result(results.get(delete_rpm).get("result"), src_full_name, rpm,
                                                   'bin_rpm_detete')

        logger.info(f"total retrieve deleted rpms belong to {len(results)} src rpm.")

        return results

    def calculate_same_rpms(self, srcs, obj_csv, header):
        """
        combination deleted src rpm mapping to rpms deteted from old os.
        @param srcs:
        @param obj_csv:
        @param header:
        @return:
        """
        all_map_rpms = {}
        for src in set(srcs):
            src_name = self.get_rpm_name(src)
            all_map_rpms.setdefault(src_name, {'src_rpm': src})
            all_bin_src = obj_csv.loc[(obj_csv[header[1]] == src)]
            bin_rpms = [i for i in all_bin_src.astype(str)[header[0]]]
            src_rpm = all_map_rpms.get(src_name)
            src_rpm.setdefault('rpms', bin_rpms)

        return all_map_rpms

    def check_gitee_spec_file(self, work_dir, src_name, content, branch):
        rpms = content["result"]
        src_repo_name = self.obtain_repository_name(src_name)
        base_url = urljoin(self.src_openeuler, src_repo_name)
        src_repo_url = urljoin(self.src_openeuler, src_repo_name)
        dir_path = os.path.join(work_dir, src_name)
        os.makedirs(dir_path)
        try:
            response = request.urlopen(base_url)
            if response.getcode() == 200:
                Repo.clone_from(src_repo_url, to_path=dir_path, branch=branch)
                logger.info(f"Download and prase {src_name} spec file")
            else:
                logger.warning(f"{src_name} repository is inaccessible: {base_url}")
        except GitCommandError:
            logger.warning(f"{src_name} Remote branch {branch} not found in upstream origin")
        except HTTPError as err:
            logger.error(f"{err}: {base_url}")
        finally:
            component_rpms = self.combine_prase_result(dir_path, src_name, rpms)

        return component_rpms

    def wget_repo_src_rpms(self, work_dir, src_name, content, branch):
        rpms = content["result"]
        url_source_repo = f"https://repo.huaweicloud.com/openeuler/{branch}/source/Packages/"
        src_rpm_full_name = content.get('src_update')
        src_url = os.path.join(url_source_repo, src_rpm_full_name)
        download_dir = os.path.join(work_dir, src_name)
        os.makedirs(download_dir)
        try:
            if src_rpm_full_name:
                src_path = wget.download(src_url, out=download_dir, bar=None)
                logger.info(f"Download and prase {src_name} spec file")
                os.chdir(download_dir)
                self.perform_cpio(src_path)
                os.chdir(os.getcwd())
            else:
                logger.warning(f"srpm {src_name} is deleted at new version")
        except Exception as err:
            logger.error(err)
        finally:
            component_rpms = self.combine_prase_result(download_dir, src_name, rpms)

        return component_rpms

    def combine_prase_result(self, dir_path, src_name, rpms):
        component_rpms = []
        spec_file = self.obtain_spec_file(dir_path, src_name)
        obsolete_rpms = self.prase_spec_with_plag(spec_file, src_name, "Obsoletes")
        provides = self.prase_spec_with_plag(spec_file, src_name, "Provides")
        for rpm in rpms:
            # 可能存在没有该分支的spec文件，或spec中不存在Obsoletes标识
            rpm.setdefault('obsoletes_flag', 'no exist')
            rpm.setdefault('spec_obsoletes_rpms', ','.join(set(obsolete_rpms)))
            rpm.setdefault('provide_flag', 'no exist')
            rpm.setdefault('spec_provides', ','.join(set(provides)))
            rpm_name = self.get_rpm_name(rpm['binary_rpm'])
            if rpm_name in obsolete_rpms:
                rpm['obsoletes_flag'] = 'exist'
            if rpm_name in provides:
                rpm['provide_flag'] = 'exist'
            component_rpms.append(rpm)

        return component_rpms

    def obtain_final_result(self, filter_result, work_dir, branch, model):
        """
        Based on the deleted rpms, download the src rpms(os) or source code(repo) to retrieve the obsoletes label.
        @param filter_result:
        @param work_dir: work directory
        @param branch: branch name
        @param model: src rpms(os) or source code(repo)
        @return -> List[dict]: retrieve results.
        """
        component_results = []
        download_dir = os.path.join(work_dir, "download_spec")
        if not os.path.exists(download_dir):
            os.makedirs(download_dir)
        pool = Pool(cpu_count())
        try:
            for src_name, content in filter_result.items():
                if model == "repo":
                    pool.apply_async(self.check_gitee_spec_file, (download_dir, src_name, content, branch),
                                     callback=component_results.extend)
                elif model == "os":
                    pool.apply_async(self.wget_repo_src_rpms, (download_dir, src_name, content, branch),
                                     callback=component_results.extend)
                else:
                    logger.error(f"input model is error,please check it.")
        except KeyboardInterrupt:
            pool.terminate()

        pool.close()
        pool.join()
        shutil.rmtree(download_dir)

        return component_results

    def run_retrieve(self, branch, work_dir, model):
        """
        Retrieve deleted rpms whether to mark obsoletes in spec files, create csv report.
        @param rpm_report: all-rpm-report.csv
        @param branch: update to branch name(eg: openEuler-22.03-LTS-SP1)
        @param work_dir: arg.work_dir
        @param model: os(everything in os) or repo(update rpms)
        """
        filter_result = self.acquire_detete_rpms(self.all_rpm_report, branch)
        component_results = self.obtain_final_result(filter_result, work_dir, branch, model)
        if not component_results:
            logger.info("component_results is empty, can't create obsoletes-rpms-report.")
        else:
            # craete obsoletes-rpms-report.csv
            self.write_to_csv_report(component_results, branch, model, work_dir)


def init_args():
    """
    init args
    :return:
    """
    parser = argparse.ArgumentParser()

    default_work_dir = "/tmp/obsoletes"
    parser.add_argument("-b", "--branch", type=str, dest="os_branch", help="compare target os branch")
    parser.add_argument("-om", "--obsolete_model", type=str, dest="os_prase_type", default="os",
                        help="obsolete target os type")
    parser.add_argument("-w", "--work-dir", type=str, dest="work_dir", default=default_work_dir, help="work root dir")
    parser.add_argument("prase_report", metavar="file", type=str, nargs='*', help="oecp all-rpm-report.csv")

    return parser.parse_args()


if __name__ == "__main__":
    init_logger()
    args = init_args()
    if not args.prase_report[0].endswith(".csv"):
        logger.error(f"this is not a csv file, must input all-rpm-report.csv to prase.")
        sys.exit(1)
    logger.info(f"OS Branch: {args.os_branch}")
    logger.info(f"Work Directory: {args.work_dir}")
    logger.info(f"Obsolete Model: {args.os_prase_type}")

    RetrieveObsoleteRpms(args.prase_report[0]).run_retrieve(args.os_branch, args.work_dir, args.os_prase_type)
