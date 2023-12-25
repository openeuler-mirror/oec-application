from tools import get_requests


class GiteeMessage(object):
    """
    the result in table to comment 
    """

    def __init__(self, pr_num, repo_name, gitee_token):
        """
        :param pr_num: the pr number
        :param repo_name: the name of the repo
        :param gitee_token: the toen of the gitee user
        """
        self._pr_num = pr_num
        self._repo_name = repo_name
        self._gitee_token = gitee_token

    def comment_pr(self, comment):
        """
        comment pull request
        :param comment: the content of the comment
        :return:
        whether successfully comment the pr
        """
        #logger.debug("comment pull request %s", pr_num)
        owner = "src-oepkgs"
        comment_pr_url = "https://gitee.com/api/v5/repos/{}/{}/pulls/{}/comments"\
        .format(owner, self._repo_name, self._pr_num)
        data = {"access_token": self._gitee_token, "body": comment}

        rs = get_requests("post", comment_pr_url, body=data, timeout=30)
        if rs != 0:
            #logger.warning("pr comment failed")
            return False
        return True