# oech-apt-install

#### 介绍

本工具提供 oec-hardware 在 debian/ubuntu 等系列系统下的工具框架依赖、测试项依赖的安装。通过该工具安装相关依赖后，下载编译oec-hardware 源码，即可使用。

#### 使用说明

1. 配置系统的镜像源，建议使用清华的镜像源。

    ubuntu镜像源参考：https://mirrors.tuna.tsinghua.edu.cn/help/ubuntu/

2. 安装工具框架依赖

    bash oech_install_dep.sh install

3. 安装测试项依赖

    bash oech_install_dep.sh test