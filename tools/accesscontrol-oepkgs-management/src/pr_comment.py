# -*- coding: utf-8 -*-

import logging.config
import argparse
import time

from result import Result
from gitee_proxy import GiteeProxy
from git import GitProxy

logging.basicConfig(level=logging.INFO)


class GiteeComment(object):
    """
    comments process
    """

    def __init__(self, pr):
        """
        :param pr: pull request number
        """
        self._pr = pr



    @classmethod
    def table_th(cls):
        """
        table header
        """
        return "<tr><th colspan=2>Check Name</th> <th>Check Result</th> <th>Check Details</th></tr>"

    @classmethod
    def table_tr(cls, name, icon, status, build_no):
        """
        one row or span row
        """
        href = "http://47.99.118.44:9095/job/Gitee/{}/console".format(build_no)
        return "<tr><td rowspan=4>check_yaml</td> <td>{}</td> <td>{}<strong>{}</strong></td> " \
               "<td><a href={}>{}{}</a></td></tr>".format(
            name, icon, status, href, "#", build_no)

    @classmethod
    def table_tr_rowspan(cls, name, icon, status, build_no):
        """
        span row
        """
        href = "http://47.99.118.44:9095/job/Gitee/{}/console".format(build_no)
        return "<tr><td>{}</td> <td>{}<strong>{}</strong></td>" \
               "<td><a href={}>{}{}</a></td></tr>".format(
            name, icon, status, href, "#", build_no)

    @classmethod
    def table_tr_colspan(cls, name, icon, status, build_no):
        """
        span row
        """
        href = "http://47.99.118.44:9095/job/Gitee/{}/console".format(build_no)
        return "<tr><td colspan=2>{}</td> <td>{}<strong>{}</strong></td>" \
               "<td><a href={}>{}{}</a></td></tr></tr>".format(
            name, icon, status, href, "#", build_no)

    def comment_repo_build(self, gitee_token, url, job_id):
        """
        构建结果
        :param url: 仓库地址
        :param job_id: jenkins构建编号
        :param gitee_token: gitee授权码
        :return:
        """
        owner = "oepkgs"
        repo_name = "oepkgs-management"
        comments, results = self._build_html_format(gitee_token, url, job_id)
        gitee_proxy = GiteeProxy(owner, repo_name, gitee_token)
        gitee_proxy.comment_pr("\n".join(comments), self._pr)
        merge = ["/lgtm", "/approve"]
        time.sleep(2)
        if 2 in results:
            gitee_proxy.comment_pr("has some problem in pull request", self._pr)
        else:
            gitee_proxy.comment_pr("\n".join(merge), self._pr)

        return "\n".join(comments)
    
    def _build_html_format(self, gitee_token, url, job_id):
        """
        组装构建信息，并评论pr
        :return:
        """
        comments = ["<table>", self.table_th()]
        report_table, results = self._ac(gitee_token, url, job_id)
        comments.extend(report_table)
        comments.append("</table>")
        return comments, results

    def _ac(self, gitee_token, url, job_id):
        """
        组装门禁检查结果
        :param url: gitee repo address
        :return:
        """
        owner = "oepkgs"
        repo_name = "oepkgs-management"
        acl = {
            "name_compare": GitProxy(repo_name).compare_yaml_repo_name(url, self._pr),
            "name_isdigit": GitProxy(repo_name).check_name_isdigit(self._pr),
            "repo_isexist": GitProxy(repo_name).check_name_is_exist(self._pr),
            "yaml": GiteeProxy(owner, repo_name, gitee_token).check_yaml(self._pr),
            "invalid_file": GitProxy(repo_name).check_invalid_file(self._pr),
            "check_num": GitProxy(repo_name).check_repo_num(self._pr),
            "check_path": GitProxy(repo_name).check_file_path(self._pr)
        }
        comments = []
        results = []
        for index, item in enumerate(acl):
            result = acl.get(item)
            results.append(result)
            ac_result = Result.value_instance(result)
            if index == 0:
                comments.append(self.__class__.table_tr(
                    item, ac_result.emoji, ac_result.status, job_id))
            elif index in [1, 2, 3]:
                comments.append(self.__class__.table_tr_rowspan(
                    item, ac_result.emoji, ac_result.status, job_id))
            else:
                comments.append(self.__class__.table_tr_colspan(
                    item, ac_result.emoji, ac_result.status, job_id))
        logger.info("ac comment: %s", comments)

        return comments, results


def init_args():
    """
    init args
    :return:
    """
    parser = argparse.ArgumentParser()
    parser.add_argument("-p", type=int, dest="pr", help="pull request number")
    parser.add_argument("-u", type=str, dest="url", help="repo_url")
    parser.add_argument("-o", type=str, dest="owner", help="gitee owner")
    parser.add_argument("-r", type=str, dest="repo", help="repo name")
    parser.add_argument("-t", type=str, dest="gitee_token", help="gitee api token")
    parser.add_argument("-n", type=int, dest="job_id", help="jenkins build number")

    return parser.parse_args()


if "__main__" == __name__:
    args = init_args()

    logger = logging.getLogger("info")

    logger.info("Start Init Environment")
    REPO_NAME = "oepkgs-management"
    init = GitProxy(REPO_NAME)
    init.update_repo()
    init.fetch_pull_request(args.url, args.pr)
    logger.info("Init Success")

    obj = GiteeComment(args.pr)
    obj.comment_repo_build(args.gitee_token, args.url, args.job_id)
