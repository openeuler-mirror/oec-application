# -*- encoding=utf-8 -*-

import os
import logging
import re
from cmd import shell_cmd
import retrying


logging.basicConfig(level=logging.INFO)
logger = logging.getLogger("common")


class GitProxy(object):
    """
    git 代理，实现常见的git操作
    """

    def __init__(self, repo_dir):
        """
        :param repo_dir: 仓库目录
        """
        self._repo_dir = repo_dir

    @classmethod
    def clone_repository(cls, sub_dir, repo_url, work_dir=None):
        """
        clone git仓库
        :return: GitProxy() or None
        @param work_dir:
        @param sub_dir:
        @param repo_url: 仓库地址
        """
        repo_dir = os.path.join(work_dir, sub_dir) if work_dir else sub_dir

        clone_cmd = "cd /var/jenkins_home/; git clone {}".format(repo_url)
        ret, _, _ = shell_cmd(clone_cmd)

        if not ret:
            logger.warning("clone repository failed, %s", ret)
            return 2

        return cls(repo_dir)

    @retrying.retry(retry_on_result=lambda result: result is False,
                    stop_max_attempt_number=3, wait_fixed=2)
    def update_repo(self):
        """
        更新本地仓库
        @return:
        """
        update_cmd = "cd /var/jenkins_home/{}; git pull".format(self._repo_dir)
        ret, _, _ = shell_cmd(update_cmd)
        if ret:
            logger.warning("update repository failed, %s", ret)
            return 2
        return True

    def fetch_pull_request(self, url, pr_num):
        """
        fetch pr
        :param url: 仓库地址
        :param pr_num: pr编号
        :return:
        """
        fetch_cmd = "cd /var/jenkins_home/{}; git fetch --depth {} {} +refs/pull/{}/MERGE:refs/pull/{}/MERGE".format(
           self._repo_dir, 4, url, pr_num, pr_num)
        ret, out, _ = shell_cmd(fetch_cmd, cap_out=True, cmd_verbose=False)
        if ret:
            logger.error("git fetch failed,%s ,%s", ret, out)
            return False
        return True

    def get_content_of_file_with_commit(self, file_path, pr_num):
        """
        获取单个commit文件内容
        :param pr_num: refs/pull/{pr_num}/MERGE
        :param file_path: 文件完整路径 sig/hpc/src-oepkgs/m/mopac.yaml
        :return: StringIO
        """
        get_content_cmd = "cd /var/jenkins_home/{}; git show refs/pull/{}/MERGE:{}".format(
           self._repo_dir, pr_num, file_path)
        ret, out, _ = shell_cmd(get_content_cmd, cap_out=True)
        if ret:
            logger.warning("get file content of commit failed, %s", ret)
            return 2

        return out

    def diff_files_between_commits(self, pr_num):
        """
        pr内容与当前仓库差异的文件名，即新增的仓库配置名
        :param pr_num: refs/pull/{pr_num}/MERGE
        :return: list&lt;string&gt;
        """
        diff_files_cmd = "cd /var/jenkins_home/{}; git diff --name-only refs/pull/{}/MERGE".format(
            self._repo_dir, pr_num)
        ret, out, _ = shell_cmd(diff_files_cmd, cap_out=True)

        if ret:
            logger.error("get diff files of commits failed, %s", ret)
            return []
        return out

    def check_file_path(self, pr_num):
        """
        验证路径
        :param pr_num:
        """
        logger.info("Start check repo path is true ?")
        files_path = self.diff_files_between_commits(pr_num)
        for file_path in files_path:
            file_list = file_path.split('/')

            if file_list[0].strip() != "sig".strip() or file_list[2].strip() != "src-oepkgs".strip():
                logger.error("file_list path error")
                return 2
            elif file_list[3].strip().lower() != file_list[4].strip()[0].lower():
                logger.error("{} last path error".format(file_list[-1]))
                return 2
            elif file_list[4].split(".")[-1] != "yaml".strip():
                logger.error("yaml name error")
                return 2
        logger.info("----END CHECK----")
        return 0

    def check_repo_num(self, pr_num):
        """
        验证此次建仓库数量是否小于100
        @param pr_num: pr序数
        @return:
        """
        logger.info("Start check files number is more than 100 ?")
        files_path = self.diff_files_between_commits(pr_num)
        if len(files_path) > 101:
            logger.error("create repo more 100, please delete some")
            return 2
        logger.info("----END CHECK----")
        return 0

    def check_invalid_file(self, pr_num, invalid_file=".keep"):
        """
        check invalid file
        """
        logger.info("Start check files have invalid file ?")
        filenames = self.diff_files_between_commits(pr_num)
        for file_name in filenames:
            if invalid_file in file_name:
                logger.error("'.keep' is invalid file, please delete")
                return 2
        logger.info("----END CHECK----")
        return 0

    def check_name_isdigit(self, pr_num):
        """
        check name not start with a number
        @param pr_num:
        @return:
        """
        logger.info("Start check is the repo name complies with specifications ?")
        files_path = self.diff_files_between_commits(pr_num)
        for file_path in files_path:
            yaml_name = file_path.split('/')[-1]
            if yaml_name == "sig-info.yaml" or "yaml" not in yaml_name:
                continue
            f = self.get_content_of_file_with_commit(file_path, pr_num)
            f = f[0]
            repo_name = f.split(":")[-1].strip()
            result = re.search("\A(?!_)(?!.*?_$)(?!-)(?!.*-$)(?!\+)([^.])[-a-zA-Z0-9_.\u4e00-\u9fa5\uff00-\uffff+ ]+\Z",
                               repo_name)
            if not result:
                logger.error("repo_name: {} has an error".format(repo_name))
                return 2
        logger.info("----END CHECK----")
        return 0

    def check_name_is_exist(self, pr_num):
        """
        check name is exist?
        @param pr_num:
        @return:
        """
        logger.info("start check name is exist in repo ?")
        files_path = self.diff_files_between_commits(pr_num)
        for file_path in files_path:
            yaml_name = file_path.split('/')[-1]
            if yaml_name == "sig-info.yaml" or "yaml" not in yaml_name:
                continue
            get_files_num_cmd = "cd /var/jenkins_home/{}; git ls-files | grep {}".format(self._repo_dir, yaml_name) +\
                                "| awk -F '/' '{print $NF}'" + "| grep  ^{}$".format(yaml_name)
            ret, out, _ = shell_cmd(get_files_num_cmd, cap_out=True)
            if not ret:
                logger.error("{} is exist".format(yaml_name))
                return 2
        logger.info("----END CHECK----")
        return 0

    @retrying.retry(retry_on_result=lambda result: result is IndexError,
                    stop_max_attempt_number=3, wait_fixed=2)
    def compare_yaml_repo_name(self, url, pr_num, depth=4, progress=False):
        """
        check name is exist?
        @param pr_num:
        @param url:
        @param depth
        @param progress
        @return:
        """
        logger.info("Start compare yaml name and repo name is same ?")
        files_path = self.diff_files_between_commits(pr_num)
        for file_path in files_path:
            yaml_name = file_path.split('/')[-1]
            if yaml_name == "sig-info.yaml" or "yaml" not in yaml_name:
                continue
            fetch_cmd = "cd /var/jenkins_home/{}; git fetch {} --depth {} {} +refs/pull/{}/MERGE:refs/pull/{}/MERGE".\
                format(self._repo_dir, "--progress" if progress else "", depth, url, pr_num, pr_num)
            ret, out, _ = shell_cmd(fetch_cmd, cap_out=True, cmd_verbose=False)
            if ret:
                logger.error("git fetch failed,%s ,%s", ret, out)
                return 2
            f = self.get_content_of_file_with_commit(file_path, pr_num)
            f = f[0]
            repo_name = f.split(":")[-1]
            yaml_name = yaml_name.split('.yaml')[0]
            logger.info(yaml_name)
            if repo_name.strip() != yaml_name.strip():
                logger.error("repo_name: {} and yaml_name: {} name not same, please change".format(repo_name,
                                                                                                   yaml_name))
                return 2
        logger.info("----END CHECK----")
        return 0
