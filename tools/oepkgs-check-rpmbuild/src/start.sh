#!/bin/bash

# display the information
function log_info()
{
    echo "[`date +%Y-%m-%d\ %T`] [  INFO ] $@"
}


function log_error()
{
    echo -e "\033[31m"[`date +%Y-%m-%d\ %T`] [ ERROR ] $@" \033[0m"
    exit 1
}


# start to check rpmbuild result
function check_build() {
  log_info "***** Start to check rpmbuild result *****"
  python3 start.py -b $1 -u $2 -r $3 -p $4 -n $5 -bb $6
  log_info "***** End to check rpmbuild result *****"
}

# the start point
function main() {

  check_build $1 $2 $3 $4 $5 $6
}

main $1 $2 $3 $4 $5 $6