# 文件说明

## find_symbolic_links.py

将当前系统中所有so文件的软链接及其对应关系存储到当前执行路径下的'symbolic-links.json'文件中.

### 用法

`python3 find_symbolic_links.py`

### 描述

查找根目录(/)下所有的软链接, 如果该软链接对应的文件为so文件, 则记录对应关系到'symbolic-links.json'文件中.

例如:

```
{
  ...
  "libipt.so": "libipt.so.0",
  ...
}
```

记录的是路径的基本名称, 并非全路径.


当前如何判断文件为so文件:

```
[root@localhost]# file /usr/lib64/libmpfr.so.4.1.6
libmpfr.so.4.1.6: ELF 64-bit LSB shared object, x86-64, version 1 (SYSV), dynamically linked, BuildID[sha1]=7e9058bfa2d1d5a55b8023044ffe28482c5f658d, stripped
```

使用`file`命令, 若结果中包含'shared object', 则认为该文件为so文件(并不全是但包括).


## install_available_packages

### 用法

```
./install_available_packages
    在前台安装并显示所有的安装过程

nohup ./install_available_packages &> install_process.log &
    在后台不受挂断地安装并将错误输出和标准输出都记录到'install_process.log'
```

### 描述

在所有已启用的仓库中查找可用的包并安装.

```
dnf list --available           show only available packages

dnf install
    -y                         automatically answer yes for all questions
    --skip-broken              resolve depsolve problems by skipping packages
```


## find_exe_file_names

### 用法

```
./find_exe_file_names
    将环境变量 PATH 中的所有路径下的可执行文件记录到文件 exe_names.json
```

### 描述

'exe_names.json'的文件格式为:

```
{
  <可执行文件的文件名>: {
    "dir": <该可执行文件所在的目录>,
    "rpmName": <该可执行文件由哪个RPM包提供, ""(空字符串)表示未找到对应关系; 若找到多个包, 之间使用空格分隔>
  },
  ...
  ...
}
```
