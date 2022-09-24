#!/usr/bin/bash
# coding: utf-8
# Copyright (c) 2022 Huawei Technologies Co., Ltd.
# oec-hardware is licensed under the Mulan PSL v2.
# You can use this software according to the terms and conditions of the Mulan PSL v2.
# You may obtain a copy of Mulan PSL v2 at:
#     http://license.coscl.org.cn/MulanPSL2
# THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
# PURPOSE.
# See the Mulan PSL v2 for more details.
# Author: @meitingli/bubble_mt@outlook.com
# Create: 2022-09-22
# Desc: Install oec-hardware dependency sortware in debian/ubuntu

function install_dep() {
    # oec-hardware install depend
    apt install -y dmidecode python3-yaml tar util-linux git ethtool psmisc gzip usbutils util-linux hwdata
    apt install -y python3-pip
    pip install concurrent_log_handler

    # install kernel-devel
    apt install -y linux-kernel-headers kernel-package
    # oec-hardware-server install depend
    apt install -y python3-dev nginx python3-flask uwsgi qperf

}

function test_dep() {
    # install depend for test suites
    apt install -y dvd+rw-tools genisoimage fio make iproute2 perftest opensm \
    ipmitool gzip crash kexec-tools memtester nvme-cli usbutils qemu util-linux \
    expect policycoreutils build-essential xz-utils
}

function main() {
    func_name=$1

    if [[ $func_name == "install" ]]; then
        install_dep
    elif [[ $func_name == "test" ]]; then
        test_dep
    else
        echo "The function doesn't exist, please check!"
        return 1
    fi
}

main "$@"
