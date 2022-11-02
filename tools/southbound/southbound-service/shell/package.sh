#!/bin/bash
# 使用maven对项目进行打包，将一键启动脚本和jar包拷贝到 ./out目录下，压缩成压缩包
set -e
build_dir=$(cd $(dirname $0); pwd)
echo -e " build_dir is ${build_dir}"
root_dir=${build_dir}/..
echo -e " root_path is ${root_dir}"
# 打包文件名称
jar_name=SouthboundDashBoard-0.0.1-SNAPSHOT.jar
pkg_name=SouthboundDashBoard-0.0.1
# 文件存放目录
file_path=southbound-service
# 部署的远程服务器IP
server_host=XXX.XXX.XXX.XXX

## 进入根目录，拉取最新代码
echo -e "************* pull code source ************"
cd ${root_dir}
git pull origin main

echo -e "************* mvn build ************"
mvn clean package -Dfile.encoding=UTF-8 -DskipTests=true

mkdir -p ${root_dir}/${file_path}/
test -d ${root_dir}/${file_path} && rm -rf ${root_dir}/${file_path}/*

# 将后台包SouthboundDashBoard-0.0.1-SNAPSHOT.jar放到out目录下
echo -e "************* cp java.jar ************"
cp  ${root_dir}/target/${jar_name}  ${root_dir}/${file_path}/${jar_name}

# 将后台启动脚本放到out目录下
echo -e "************* cp start.sh ************"
cp  ${build_dir}/start.sh ${root_dir}/${file_path}/start.sh

cd ${file_path}/
# 将文件上传到远端服务器
echo -e "************* upload jar to server host ************"
scp -r ${root_dir}/${file_path} root@${server_host}:/opt
echo -e "************* upload success ************"