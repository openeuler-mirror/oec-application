# **构建rpm包流程**

###### 总体流程：
1. 基于PR，在oepkgs-mangement仓库中创建配置文件，用于建仓
2. 往步骤一生成的https://gitee.com/src-oepkgs/仓库中补充构建所需源码文件
3. 仓库的webhook将自动触发构建任务

#### 一、基于PR，创建仓库
在[oepkgs-management](https://gitee.com/oepkgs/oepkgs-management)仓库提PR，填写配置文件，用于在[src-oepkgs](https://gitee.com/src-oepkgs)（以perf-）下面创建仓库
#### 二、补充源码文件

#### 三、基于webhook，自动触发构建任务原理


1. 提交构建rpm包任务
2. 查看日志判断是否构建成功
3. 构建成功则会自动进行安装测试，若成功则入库
4. 构建/安装测试失败则查看日志分析原因并进行修复
5. 在oepkgs-management仓库建仓
6. 将修复的源码以及spec等文件放入
7. 仓库的webhook将自动触发提交构建任务

####  一. 提交构建rpm包任务
`submit rpmbuild.yaml -m -I ssh-on-fail.yaml`（如果job失败了，自动sleep）
```
# 测试用例名称
suite: rpmbuild
# 类别
category: functional
# 测试用例 ~/lkp-test/tests/rpmbuild
rpmbuild:
# 远程仓库源码包地址
repo_addr: https://xxxxxx/xx.src.rpm
# 构建后的包仓库分支
custom_repo_name: compatible/c7

# 测试机为虚拟机时:
testbox: vm-2p8g
arch: aarch64
os: openeuler
os_version: 22.03-LTS

# 测试机为容器时:
# testbox: dc-8g
# arch: aarch64
# docker_image: openeuler:22.03-LTS
```
#### 二. 查看日志判断是否构建成功
###### 2.1 可通过job_id来查看日志
 <u>https://compass-ci.openeuler.org/jobs</u>


#### 三. rpmbuild脚本
在submit rpmbuild.yaml 时，测试用例“rpmbuild”会去引用脚本 ~/lkp-tests/tests/rpmbuild
该脚本会通过rpmbuild.yaml提供的信息进行rpmbuild -ba xxx。如果构建成功，则通过upload_rpm_pkg函数先将测试机上打好的软件包放入/srv/rpm/upload，再通过update_repo_mq处理上传的软件包。处理完的包会先放入/srv/rpm/testing中，每天零点定时更新到/srv/rpm/pub中。
#### 四. 测试构建的包能否正常安装
###### 4.1  可以查看/srv/result/install-rpm(自动构建任务，无需提交)

###### 4.2  手动提交install.yaml
需要加入以下参数

mount_repo_addr: 构建后的包所在仓库地址

mount_repo_name: 仓库分支

srpm_name: 构建好的rpm包,去除.rpm

```
suite: install-rpm
category: functional
install-rpm:
rpm_name: xxxx
os: openeuler
os_version: 22.03-LTS
testbox: vm-2p8g
arch: aarch64
#testbox: dc-8g
#docker_image: openeuler:22.03-LTS
mount_repo_addr: https://api.compass-ci.openeuler.org:20018/rpm/testing/openeuler-22.03-LTS/compatible/c7/aarch64
mount_repo_name: compatible/c7
```

#### 五. 处理自动构建失败的包
###### 5.1 查找原因并修复
可以通过2.1得知构建失败的原因，可在执行任务的虚拟机/容器中进行重新构建并一步步修复
修复完成的源码以及spec文件放入以下仓库中
###### 5.2 建仓
在<u>https://gitee.com/oepkgs/oepkgs-management/</u>仓库进行建仓

**5.2.1 先将该仓库forked到自己账号的仓库中**

**5.2.2 将forked的仓库git clone到本地，进行建仓**

**5.2.3 将修复完成的源码/spec文件放入到自己仓库中，再将自己仓库的内容Pull requests到企业仓**

**5.2.4 当前src-oepkgs仓库配置了webhook，只要把软件源码以及spec文件合入到仓库里，compass-ci这边就会自动触发提交构建任务**
#### 六. 查询软件包
[https://compass-ci.openeuler.org/oepkgs](https://compass-ci.openeuler.org/oepkgs)
可在此查询引入到软件所的软件包