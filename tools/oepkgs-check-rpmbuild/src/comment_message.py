#import logging.config
#import argparse
#import time


class CommentMessage(object):
    """
    the result in table to comment 
    """

    def __init__(self, result0, result1):
        """
        :param result0: the aarch64 build result
        :param result1: the x86_64 build result
        """
        self._result0 = result0
        self._result1 = result1

    @classmethod
    def table_head(cls):
        """
        the head of the table
        | Arch | Check Arch | Build_Result | Result_Detail |
        """
        return "<tr><th>Arch</th> <th>Check Arch</th> <th>Build_Result</th> <th>Result_Detail</th> </tr>"


    @classmethod
    def table_content(cls, arch, check_arch, result, build_num):
        """
        the content of the table
        :param build_num: jenkins build id
        """
        href = "https://jenkins.openeuler.isrc.ac.cn/job/OepkgsNetCheck/{}/console".format(build_num)
        return "<tr><td>{}</td> <td>{}</td> <td>{}</td>" \
               "<td><a href={}>{}{}</a></td></tr></tr>".format(arch, check_arch, result, href, "#", build_num)


    def build_table(self, build_num):
        """
        get the build result
        :param build_num: jenkins build id
        :return:
        the final table
        """
        
        comments = ["<table>", self.table_head()]
        result_table = self.get_result_table(build_num)
        comments.extend(result_table)
        comments.append("</table>")
        return comments
        
    
    def get_result_table(self, build_num):
        """
        :param build_num: jenkins build id
        :return:
        the build result table
        """
        comments = []
        line1 = self.__class__.table_content("aarch64", "Yes", self._result0, build_num)
        line2 = self.__class__.table_content("x86_64", "Yes", self._result1, build_num)
        comments.append(line1)
        comments.append(line2)
        return comments

    