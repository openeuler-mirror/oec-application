#!/bin/echo Warning: this library should be sourced!
function log_info()
{
    echo "[`date +%Y-%m-%d\ %T`] [  INFO ] $@"
}


function log_error()
{
    echo -e "\033[31m"[`date +%Y-%m-%d\ %T`] [ ERROR ] $@" \033[0m"
    exit 1
}


# 执行评论功能
function exec_comment() {
  log_info "***** Start to exec comment *****"
  python3 /var/jenkins_home/accesscontrol/oec-application/tools/oepkgs-management-AccessControl/src/pr_comment.py -p $1 -t "c951fee688f4b037d27602d7461b81fc" -u "https://gitee.com/oepkgs/oepkgs-management.git" -n $2
  log_info "***** End to exec comment *****"
}

# 执行入口
function main() {

  exec_comment $1 $2
}
main $1 $2