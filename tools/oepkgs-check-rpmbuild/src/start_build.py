import time
from get_token import get_oepkgs_token
from create_job import create_job
from comment_message import CommentMessage
from gitee_message import GiteeMessage
from build_job import build_job
from get_record import get_build_record
from logger import log_info, log_error, log_warning
from tools import get_branch


class MyException(Exception):
    '''
    detect exception
    '''
    pass   


def get_info(args):
    '''
    process the input args
    :param args: the input args from command line
    
    :return:
    buildbranch: openeuler version to build,e.g. 22.03-LTS-SP1
    token: the login token of the account oepkgs_management
    '''
    buildbranch = get_branch(args.buildbranch)
    token = get_oepkgs_token()
    #print(token)
    log_info("oepkgs_token:" + token)
    return buildbranch, token


def rpmbuild_action(token, url, bb, repoid, buildbranch):
    """
    start create job and build job 
    :param token: the login token of the account oepkgs_management
    :param url: the repo url containing the source code of the rpm
    :param arch: build arch: aarch64 or x86_64
    :param branch: the branch of the url
    :param repoid: the repo id
    :param buildbranch: the version of openeuler to build rpm
    :return:
    jobid: the id of the job
    jobname: the name of the job
    """
    create_success, jobname, jobid = create_job(token, url, bb, repoid, buildbranch)
    if create_success == 'fail':
        log_error("failed to create a job")
        raise MyException("can't create a job")
    elif create_success == 'success':
        log_info("successfully create a job: " + "jobName " + str(jobname) + "; jobId " + str(jobid))
    
    build_success = build_job(token, jobname, jobid)
    if build_success == 'fail':
        log_error("failed to build a job")
        raise MyException("can't build a job")
    elif build_success == 'success':
        log_info("successfully build a job:" + "jobName " + str(jobname) + "; jobId " + str(jobid))
    
    return jobname, jobid


def git_comment(jobname, jobid, res):
    '''
    print git comment
    '''
    #print("git comment:")
    log_info("jobName:" + str(jobname) + "; jobid:" + str(jobid) + "; result:" + str(res))
    return res


def get_result(token, jobname_list, jobid_list):
    '''
    get build result
    :param token: the login token of the account oepkgs_management
    :param jobid: the id list of the jobs
    :param jobname: the name list of the jobs
    :return:
    build result
    '''
    get_result0 = 0
    get_result1 = 0
    roun = 0
    while True:
        if get_result0 == 0:
            jobname = jobname_list[0]
            jobid = jobid_list[0]
            res = get_build_record(token, jobname, jobid)
            if res == 'SUCCESS' or res == 'FAILURE':
                log_info("aarch64:")
                result0 = git_comment(jobname, jobid, res)
                get_result0 = 1
        if get_result1 == 0:
            jobname = jobname_list[1]
            jobid = jobid_list[1]
            res = get_build_record(token, jobname, jobid)
            if res == 'SUCCESS' or res == 'FAILURE':
                log_info("x86_64:")
                result1 = git_comment(jobname, jobid, res)
                get_result1 = 1
        if get_result1 == 1 and get_result0 == 1:
            break            
        time.sleep(120)
        roun = roun + 1
        if roun > 30:
            if get_result0 == 0:
                result0 = 'TIME OUT'
            if get_result1 == 0:
                result1 = 'TIME OUT'
            break
            
    return result0, result1


def start_build(args):
    '''
    start the check rpmbuild result process

    1)create and build job
    2)get build record
    3)comment pr in gitee
    '''
    url = args.url
    branch = args.branch
    repoid = 127
    branch_list = ['aarch64', 'x86_64']
    
    buildbranch, token = get_info(args)
    jobname_list = []
    jobid_list = []
    for arch in branch_list:
        bb = [arch, branch]
        name, iid = rpmbuild_action(token, url, bb, repoid, buildbranch)
        jobname_list.append(name)
        jobid_list.append(iid)
    
    result0, result1 = get_result(token, jobname_list, jobid_list)
    ini = CommentMessage(result0, result1)
    
    #ini=CommentMessage('SUCCESS','FAILED')
    comment = ini.build_table(args.job_id)
    git = GiteeMessage(args.pr, args.repo, "c951fee688f4b037d27602d7461b81fc")
    log_info(comment)
    git.comment_pr("\n".join(comment))


