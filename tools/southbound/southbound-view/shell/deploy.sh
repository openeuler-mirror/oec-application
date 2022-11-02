#!/bin/bash
# 拉取主仓最新代码，本地调用npm打包，将前端编译好的包传输到服务器中
set -e
# 脚本路径
build_dir=$(cd $(dirname $0); pwd)
# 根目录路径
cd ../
root_dir=$(cd $(dirname $0); pwd)
echo -e "root dir is "${root_dir}
# 打包文件路径
dist_dir=${root_dir}
# 打包生成的目录
file_name=dist
# 部署的远程服务器IP
server_host=XXX.XXX.XXX.XXX

# 进入根目录，拉取最新代码
echo -e "************* pull code source ************"
cd ${root_dir}
git pull origin main

# 执行打包命名
echo -e "************* npm build ************"
npm run build

# 进入打包文件目录，对打包生成的文件进行压缩
echo -e "************* enter directory ************"
cd  ${dist_dir}

# 上传到服务器/opt/southbound-view目录下
echo -e "************* upload file to server host ************"
mkdir -p /opt/southbound-view
scp -r ${dist_dir}/${file_name} root@${server_host}:/opt/southbound-view
echo -e "************* upload success ************"
