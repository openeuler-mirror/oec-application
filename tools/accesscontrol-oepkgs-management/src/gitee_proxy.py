import logging
import yaml

from requests import get_requests

logger = logging.getLogger("common")


class GiteeProxy:

    def __init__(self, owner, repo, token):
        """
        :param token: 用户授权码
        """
        self._owner = owner
        self._repo = repo
        self._token = token

    @staticmethod
    def _check_yaml(repo_yaml_url, timeout=10):
        """
        获取社区repo
        yaml验证
        :param repo_yaml_url:
        :param timeout:
        :return:
        """
        repos = []

        def analysis(response):
            """
            requests回调
            :param response: requests response object
            :return:
            """
            handler = yaml.safe_load(response.text)
            repos.append(item for item in handler)

        yaml_url = repo_yaml_url
        rs = get_requests("get", url=yaml_url, timeout=timeout, obj=analysis)
        if rs != 0:
            logger.warning("get repo yaml failed", yaml_url)
            return 2

        if not repos:
            return 2

        return 0

    def get_all_repo_name(self):
        """
        获取oepkgs组织下所有仓库名
        """
        flag = True
        repo_name_list = []
        page = 1
        per_page = 20
        while flag:
            url = 'https://gitee.com/api/v5/orgs/{}/repos?access_token={}&type=all&page={}&per_page={}'.format(
                self._owner, self._token, page, per_page)

            def analysis(response):
                """
                requests回调，解析pr列表
                :param response: requests response object
                :return:
                """
                nonlocal page
                nonlocal flag
                handler = response.json()

                if handler:
                    for repo in handler:
                        repo_name_list.append(repo.get('name'))
                    page += 1
                    if len(handler) == 0:
                        flag = False

            rs = get_requests("get", url, timeout=10, obj=analysis)

            if rs != 0:
                logger.warning("pr comment failed")

        return repo_name_list

    def get_pr_commit_list(self, pr_num):
        """
        获取此次提交的pr的所有yaml文件的url以及其他信息
        :return:
        """
        api = 'https://gitee.com/api/v5/repos/{}/{}/pulls/{}/files?access_token={}'.format(self._owner, self._repo,
                                                                                           pr_num, self._token)

        yaml_url_list = []

        def analysis(response):
            """
            requests回调，解析pr列表
            :param response: requests response object
            :return:
            """
            handler = response.json()
            for commit in handler:
                yaml_url_list.append(commit.get('raw_url'))

        rs = get_requests("get", api, timeout=10, obj=analysis)
        if rs != 0:
            logger.warning("get pr_commit_list failed")

        return yaml_url_list

    def check_yaml(self, pr_num):
        """
        check yaml file
        :param pr_num: 本仓库PR的序数
        """
        logger.info("Start check yaml is complies with specifications ?")
        commit_list = self.get_pr_commit_list(pr_num)
        for commit in commit_list:
            if "sig" not in commit:
                continue
            result = self._check_yaml(commit)
            if result == 2:
                return 2
        logger.info("----END CHECK----")
        return 0

    def comment_pr(self, comment, pr_num):
        """
        评论pull request
        :param pr_num: 本仓库PR的序数
        :param comment: 评论内容
        """
        logger.debug("comment pull request %s", pr_num)
        comment_pr_url = "https://gitee.com/api/v5/repos/{}/{}/pulls/{}/comments".format(self._owner, self._repo,
                                                                                         pr_num)
        data = {"access_token": self._token, "body": comment}

        rs = get_requests("post", comment_pr_url, body=data, timeout=30)
        if rs != 0:
            logger.warning("pr comment failed")
            return False
        return True
