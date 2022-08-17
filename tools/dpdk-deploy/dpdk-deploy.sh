#!/bin/bash
# Copyright (c) 2022 Huawei Technologies Co., Ltd.
# oec-hardware is licensed under the Mulan PSL v2.
# You can use this software according to the terms and conditions of the Mulan PSL v2.
# You may obtain a copy of Mulan PSL v2 at: http://license.coscl.org.cn/MulanPSL2
# THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED,
# INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
# See the Mulan PSL v2 for more details.
# Author: @ylzhangah
# Create: 2022-08-11
# Modified: @ylzhangah
# Desc: DPDK_DEPLOY Tool
# Desc: This script is used to deploy dpdk automatically;


dpdk_version="20.11.6"
dpdk_name="dpdk-${dpdk_version}"

function install_dpdk()
{
	dpdk_url="http://fast.dpdk.org/rel/${dpdk_name}.tar.xz"
	yum -y install net-tools pciutils numactl numactl-devel libpcap-devel python3 python3-devel gcc gcc-c++ emacs-filesystem vim-filesystem libhugetlbfs meson
	# meson's depend is emacs-filesys, ninja's depend is vim-filesystem
	[ $? -eq 0 ] && echo "All depends has been installed!" 
	cd /root/
	if [ ! -f "/root/${dpdk_name}.tar.xz" ]; then
                wget ${dpdk_url} && echo "wget dpdk successuflly!"
		tar xf /root/${dpdk_name}.tar.xz
	elif [ -f "/root/${dpdk_name}.tar.xz" ]; then
		tar xf /root/${dpdk_name}.tar.xz
        elif [ -d "dpdk-stable-${dpdk_version}" ]; then
                echo "The dpdk package has been downloaded!"
        fi
	
	cd /root/dpdk-stable-${dpdk_version}
	meson -Denable_kmods=true build
	if [ $? -eq 0 ]; then
		ninja -C build
		ninja -C build install
	fi
	if [ $? -eq 0 ];then
		return 0
	fi
}

function configure_env()
{
	echo "/usr/local/lib64" >> /etc/ld.so.conf.d/dpdk.conf
	ldconfig
	cmd=$(ldconfig -p | grep librte | wc -l)
	if [ $cmd -gt 0 ];then
		echo "The dpdk's library is already in cache."
	else
		echo "Please check the dpdk.conf!"
	fi
	[ $? -eq 0 ] && echo "export PKG_CONFIG_PATH=/usr/local/lib64/pkgconfig" >> /root/.bashrc
	source /root/.bashrc
	version=$(pkg-config --modversion libdpdk)
	echo "DPDK is installed successfully!"
	echo "DPDK_Version: $version"
	return 0
}

function is_numa()
{
	if [ -d "/sys/devices/system/node'" ];then
		echo "NUMA is used on this system"
		return 0
	fi
	return 1
}

function set_hugepages()
{
	if [ ! -d /mnt/huge ]; then
		mkdir /mnt/huge
		chmod 777 /mnt/huge
	fi
	mount -t hugetlbfs nodev /mnt/huge
	is_numa
	if [ $? -eq 0 ];then 
        	nodes_num=$(lscpu | grep -i numa | grep "NUMA node(s)" | awk '{print $3}')
		node=$(expr $nodes_num - 1)
		for i in $(seq 0 $node);
		do
			echo 1024 > /sys/devices/system/node/node$i/hugepages/hugepages-2048kB/nr_hugepages
		done
		
	else
		echo 1024 > /sys/kernel/mm/hugepages/hugepages-2048kB/nr_hugepages
	fi
	hugePage_total=$(grep "HugePages_Total:" /proc/meminfo | awk '{print $2}')
	hugepagesize=$(grep "Hugepagesize:" /proc/meminfo | awk '{print $2}')
	if [ ${hugePage_total} == 4096 ];then
		echo "Hugepages set successfully!"
		echo "System hugepage size: $hugepagesize"
		return 0
	fi
}

function load_driver()
{
	# Load driver
	modprobe uio uio_pci_generic
	lsmod | grep uio
	if [ $? -eq 0 ]; then
		echo "DPDK driver is loaded!"
	else
		echo "Please insmod the driver manually! "
	fi
}

install_dpdk
configure_env
set_hugepages
load_driver
