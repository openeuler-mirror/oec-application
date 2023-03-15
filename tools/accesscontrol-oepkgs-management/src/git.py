# -*- encoding=utf-8 -*-

import os
import re
import retrying
from shell_cmd import shell_cmd_live
from shell_cmd import shell_cmd
from build_log import Log

logger = Log()


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
        ret, _, _ = shell_cmd_live(clone_cmd)

        if not ret:
            logger.warning("clone repository failed, {}".format(ret))
            return 2

        return cls(repo_dir)

    @retrying.retry(retry_on_result=lambda result: result is False,
                    stop_max_attempt_number=3, wait_fixed=2000)
    def update_repo(self):
        """
        更新本地仓库
        @return:
        """
        update_cmd = "cd /var/jenkins_home/{}; git pull".format(self._repo_dir)
        ret, _, _ = shell_cmd_live(update_cmd)
        if ret:
            logger.warning("update repository failed, {}".format(ret))
            return False
        return True

    def fetch_pull_request(self, url, pr_num):
        """
        fetch pr
        :param url: 仓库地址
        :param pr_num: pr编号
        :return:
        """
        fetch_cmd = "cd /var/jenkins_home/{}; git fetch {} pull/{}/head:pr_{}".format(
            self._repo_dir, url, pr_num, pr_num)
        ret, out, _ = shell_cmd_live(fetch_cmd, cap_out=True, cmd_verbose=False)
        if ret:
            logger.error("Git fetch failed,{} {}".format(ret, out))
            return False
        return True

    def get_content_of_file_with_commit(self, file_path, pr_num):
        """
        获取单个commit文件内容
        :param pr_num: refs/pull/{pr_num}/MERGE
        :param file_path: 文件完整路径 sig/hpc/src-oepkgs/m/mopac.yaml
        :return: StringIO
        """
        get_content_cmd = "cd /var/jenkins_home/{}; git show pr_{}:{}".format(
            self._repo_dir, pr_num, file_path)
        ret, out, _ = shell_cmd_live(get_content_cmd, cap_out=True)
        if ret:
            logger.error("Get file content of commit failed, {}".format(ret))
            return 2

        return out

    def diff_files_between_commits(self, pr_num):
        """
        pr内容与当前仓库差异的文件名，即新增的仓库配置名
        :param pr_num: refs/pull/{pr_num}/MERGE
        :return: list&lt;string&gt;
        """
        diff_files_cmd = "cd /var/jenkins_home/{}; git diff master --name-only --diff-filter=ACM pr_{}".format(
            self._repo_dir, pr_num)
        ret, out, _ = shell_cmd_live(diff_files_cmd, cap_out=True)

        if ret:
            logger.error("Get diff files of commits failed, {}!".format(ret))
            return []
        return out

    @retrying.retry(retry_on_exception=lambda exception: isinstance(exception, IndexError),
                    stop_max_attempt_number=3, wait_fixed=2000)
    def check_file_path(self, pr_num):
        """
        验证路径
        :param pr_num:
        """
        logger.info("Check the repo file name and path are correct ?")
        files_path = self.diff_files_between_commits(pr_num)
        for file_path in files_path:
            if "sig-info.yaml" in file_path:
                continue
            file_list = file_path.split('/')
            if file_list[0].strip() != "sig".strip() or file_list[2].strip() != "src-oepkgs".strip():
                logger.error("Path error, first dir not sig or third dir not src-oepkgs !")
                return 2
            elif file_list[3].strip().lower() != file_list[4].strip()[0].lower():
                logger.error(
                    "{} repo name and dir name not same, please change it !".format(file_list[-1]))
                return 2
            elif file_list[4].split(".")[-1] != "yaml".strip():
                logger.error("{} Yaml name error, without .yaml please add !".format(file_list))
                return 2
        logger.info("----END CHECK----")
        return 0

    @retrying.retry(retry_on_exception=lambda exception: isinstance(exception, IndexError),
                    stop_max_attempt_number=3, wait_fixed=2000)
    def check_repo_num(self, pr_num):
        """
        验证此次建仓库数量是否小于100
        @param pr_num: pr序数
        @return:
        """
        logger.info("Check whether the number of files is greater than 100 ?")
        files_path = self.diff_files_between_commits(pr_num)
        if len(files_path) > 101:
            logger.error("Create repo more 100, please delete some !")
            return 2
        logger.info("----END CHECK----")
        return 0

    @retrying.retry(retry_on_exception=lambda exception: isinstance(exception, IndexError),
                    stop_max_attempt_number=3, wait_fixed=2000)
    def check_invalid_file(self, pr_num, invalid_file=".keep"):
        """
        check invalid file
        """
        logger.info("Check for invalid files ?")
        filenames = self.diff_files_between_commits(pr_num)
        for file_name in filenames:
            if invalid_file in file_name:
                logger.error("'.keep' is invalid file, please delete !")
                return 2
        logger.info("----END CHECK----")
        return 0

    @retrying.retry(retry_on_exception=lambda exception: isinstance(exception, IndexError),
                    stop_max_attempt_number=3, wait_fixed=2000)
    def check_name_isdigit(self, pr_num):
        """
        check name not start with a number
        @param pr_num:
        @return:
        """
        logger.info("Check whether the repo name conforms to the specification ?")
        files_path = self.diff_files_between_commits(pr_num)
        for file_path in files_path:
            yaml_name = file_path.split('/')[-1]
            if yaml_name == "sig-info.yaml" or "yaml" not in yaml_name:
                continue
            change_type = self.check_a_m_d(yaml_name, pr_num)
            if change_type == "M":
                continue
            elif change_type == "D":
                logger.error("Can not delete yaml files")
                return 2
            f = self.get_content_of_file_with_commit(file_path, pr_num)
            f = f[0]
            repo_name = f.split(":")[-1].strip()
            result = re.search("\A(?!_)(?!.*?_$)(?!-)(?!.*-$)(?!\+)([^.])[-a-zA-Z0-9_.\u4e00-\u9fa5\uff00-\uffff+ ]+\Z",
                               repo_name)
            if not result:
                logger.error(
                    "{} is not complies with specifications, please change it !".format(repo_name))
                return 2
        logger.info("----END CHECK----")
        return 0

    def check_a_m_d(self, yaml_name, pr_num):
        analyzed_cmd = "cd /var/jenkins_home/{}; git log pr_{} -1 --pretty=format: --name-status --no-merges" \
                       "| grep {} | awk -F' ' '{{print $1}}' | cat".format(self._repo_dir, pr_num,
                                                                           yaml_name)
        out = shell_cmd(analyzed_cmd)
        return out

    @retrying.retry(retry_on_exception=lambda exception: isinstance(exception, IndexError),
                    stop_max_attempt_number=3, wait_fixed=2000)
    def check_name_is_exist(self, pr_num):
        """
        check name is exist?
        @param pr_num:
        @return:
        """
        logger.info("Check whether the repo already exists ?")
        files_path = self.diff_files_between_commits(pr_num)
        for file_path in files_path:
            yaml_name = file_path.split('/')[-1]
            if yaml_name == "sig-info.yaml" or "yaml" not in yaml_name:
                continue
            change_type = self.check_a_m_d(yaml_name, pr_num)
            if change_type == "M":
                continue
            elif change_type == "D":
                logger.error("Can not delete yaml files")
                return 2
            get_files_num_cmd = "cd /var/jenkins_home/{}; git ls-files | grep -i {}".format(self._repo_dir, yaml_name) \
                                + "| awk -F '/' '{print $NF}'" + "| grep -i ^{}$".format(yaml_name)
            ret, out, _ = shell_cmd_live(get_files_num_cmd, cap_out=True)
            if not ret:
                logger.error("{} is exist, please find in repo !".format(yaml_name))
                return 2
        logger.info("----END CHECK----")
        return 0

    @retrying.retry(retry_on_exception=lambda exception: isinstance(exception, IndexError),
                    stop_max_attempt_number=3, wait_fixed=2000)
    def compare_yaml_repo_name(self, pr_num):
        """
        check name 
        @param pr_num:
        @return:
        """
        logger.info("Check whether yaml name and repo name are the same ?")
        files_path = self.diff_files_between_commits(pr_num)
        for file_path in files_path:
            yaml_name = file_path.split('/')[-1]
            if "yaml" not in yaml_name:
                continue
            if yaml_name == "sig-info.yaml":
                dir_name = file_path.split('/')[-2]
                f = self.get_content_of_file_with_commit(file_path, pr_num)
                f = f[0]
                if f == 2:
                    return 2
                repo_name = f.split(":")[-1]
                logger.info(yaml_name)
                if repo_name.strip() != dir_name:
                    logger.error("sig-info.yaml [name] {} and dir [name] {} not same".format(repo_name, dir_name))
                    return 2
                continue
            change_type = self.check_a_m_d(yaml_name, pr_num)
            if change_type == "D":
                logger.error("Can not delete yaml files")
                return 2
            f = self.get_content_of_file_with_commit(file_path, pr_num)
            f = f[0]
            if f == 2:
                return 2
            repo_name = f.split(":")[-1]
            yaml_name = yaml_name.split('.yaml')[0]
            logger.info(yaml_name)
            if repo_name.strip() != yaml_name.strip():
                logger.error(
                    "repo_name: {} and yaml_name: {} name not same, please change !".format(repo_name,
                                                                                            yaml_name))
                return 2
        sig_info_yaml_check_res = self.check_siginfo(pr_num)
        if sig_info_yaml_check_res == 2:
            return 2
        logger.info("----END CHECK----")
        return 0

    def check_siginfo(self, pr_num):
        """
        check sig-info.yaml change
        """
        logger.info("Check sig-info.yaml ?")
        files_path = self.diff_files_between_commits(pr_num)
        for file_path in files_path:
            yaml_name = file_path.split('/')[-1]
            change_type = self.check_a_m_d(yaml_name, pr_num)
            if yaml_name == "sig-info.yaml" and change_type == "M":
                dir_name = file_path.split('/')[-2]
                f = self.get_content_of_file_with_commit(file_path, pr_num)
                f = f[0]
                repo_name = f.split(":")[-1]
                if repo_name.strip() != dir_name.strip():
                    logger.error("You can not change sig-info.yaml [name] {} or [description]".format(dir_name))
                    return 2
        return 0
