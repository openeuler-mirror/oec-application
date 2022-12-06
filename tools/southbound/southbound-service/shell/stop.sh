#!/bin/bash
# 在linux环境下的一键启动脚本
set -e 
jar_name=SouthboundDashBoard-0.0.1-SNAPSHOT.jar
echo "****** look for progress *******"
PROCESS="$(ps -ef | grep -E ${jar_name} | grep -v grep | awk '{print$2}' )"
echo $PROCESS
if [[ -n $PROCESS ]];
then
  echo "$1 running... start kill..."
  kill -15 $PROCESS
else
  echo "$1 not running"
fi

