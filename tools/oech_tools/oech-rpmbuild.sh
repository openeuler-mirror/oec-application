#!/usr/bin/bash
# This script is used to automatically download git_repo,
# and install oech/cstch tools

cstch()
{
	git_url="https://gitee.com/cuixucui/cstc-hardware-cap"
	filename=${git_url##*/}
	work_dir="/root/work"
	rpmbuild_dir="/root/rpmbuild"
	source_name="CSTC-hardwareCap"
	spec_name="cstchc-hardware"
}

oech()
{
	git_url="https://gitee.com/cuixucui/oec-hardware"
	filename=${git_url##*/}
	work_dir="/root/work"
	rpmbuild_dir="/root/rpmbuild"
	source_name="oec-hardware"
	spec_name="oec-hardware"
}

parameter()
{
	if [[ "$@" == "cstch" ]]; then
		cstch
	elif [[ "$@" == "oech" ]]; then
		oech
	else
		echo "Please enter parameter: oech or cstchc" &
		exit
	fi
}

init_work_dir()
{
	parameter "$@"
	if [ -d ${work_dir} ] & [ -d ${rpmbuld_dir} ]; then	
		rm -rf ${work_dir} ${rpmbuild_dir}
	fi
	mkdir -p ${work_dir} 
	yum -y install git python rpm-build rpmdevtools
	rpmdev-setuptree
	cd ${work_dir}
	git clone -q ${git_url}
	if [ $? -eq 0 ]; then
		echo "clone git  repo $filename successfully"
	else
		echo "clone git repo $filename failed:${git_url}" & 
		return
	fi
}

do_file()
{
	echo ${work_dir}/$filename
	cd ${work_dir}/$filename || exit
	tar jcvf ${source_name}-1.0.0.tar.bz2 *
	cp ${source_name}-1.0.0.tar.bz2 ${rpmbuild_dir}/SOURCES/
	cp ${spec_name}.spec ${rpmbuild_dir}/SPECS/
	rpmbuild -ba ${rpmbuild_dir}/SPECS/*.spec
	if [ $? -eq 0 ]; then
		echo "$filename rpmbuild successfully"
	else
		echo "$filename rpmbuild failed" & 
		return
	fi

}

install()
{
	echo -e "y\n" | yum remove ${source_name}.$(arch) ${source_name}-server.$(arch)
	echo -e "y\n" | yum install ${rpmbuild_dir}/RPMS/$(arch)/${source_name}-1.0.0-2.$(arch).rpm
	echo -e "y\n" | yum install ${rpmbuild_dir}/RPMS/$(arch)/${source_name}-server-1.0.0-2.$(arch).rpm
	pip3 install Flask Flask-bootstrap uwsgi
	systemctl start "$@"-server.service
	systemctl start nginx.service
	systemctl stop firewalld
	iptables -F
	setenforce 0
}

init_work_dir "$@"
do_file
install "$@"
