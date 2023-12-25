import logging.config
import argparse
from start_build import start_build


def init_args():
    """
    init args
    get args from command line
    :return:
    input args 
    """
    parser = argparse.ArgumentParser()
    parser.add_argument("-p", type=int, dest="pr", help="pull request number")
    parser.add_argument("-u", type=str, dest="url", help="repo_url")
    parser.add_argument("-r", type=str, dest="repo", help="repo_name")
    parser.add_argument("-b", type=str, dest="branch", help="gitee branch")
    parser.add_argument("-bb", type=str, dest="buildbranch", help="build branch")
    parser.add_argument("-n", type=int, dest="job_id", help="jenkins build number")

    return parser.parse_args()


if "__main__" == __name__:
    args = init_args()

    logger = logging.getLogger("info")

    logger.info("Start Init Environment")
    
    logger.info("Init Success")

    start_build(args)
    logger.info("Finish")