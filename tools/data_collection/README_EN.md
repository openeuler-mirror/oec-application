# File Description

## find_symbolic_links.py

It stores the soft links of all `.so` files in the current system and their mappings to the **symbolic-links.json** file in the current execution path.

### Usage

`python3 find_symbolic_links.py`

### Description

Search for all soft links in the root directory (/). If the file corresponding to the soft link is an `.so` file, record the mapping to the **symbolic-links.json** file.

For example:

```text
{
  ...
  "libipt.so": "libipt.so.0",
  ...
}
```

The recorded name is the basic name of the path, not the full path.

To determine whether a file is an `.so` file, perform the following step:

```text
[root@localhost]# file /usr/lib64/libmpfr.so.4.1.6
libmpfr.so.4.1.6: ELF 64-bit LSB shared object, x86-64, version 1 (SYSV), dynamically linked, BuildID[sha1]=7e9058bfa2d1d5a55b8023044ffe28482c5f658d, stripped
```

Run the `file` command. If the command output contains "shared object", the file is an `.so` file (although not all are, but this includes such cases).

## install_available_packages

### Usage

```shell
./install_available_packages
    Install and display all installation processes on the foreground.

nohup ./install_available_packages &> install_process.log &
    Perform the installation in the background without being disconnected and record the error output and standard output to the **install_process.log** file.
```

### Description

Search for available packages in all enabled repositories and install them.

```shell
dnf list --available           show only available packages

dnf install
    -y                         automatically answer yes for all questions
    --skip-broken              resolve depsolve problems by skipping packages
```

## find_exe_file_names

### Usage

```shell
./find_exe_file_names
    Record the executable files in all paths specified by the environment variable **PATH** to the **exe_names.json** file.
```

### Description

The format of the **exe_names.json** file is as follows:

```shell
{
  <Name of the executable file>: {
    "dir": < directory where the executable file is located>,
    "rpmName": < RPM package that provides the executable file. An empty string ("") indicates that no mapping is found. If multiple packages are found, separate them with spaces. >
  },
  ...
  ...
}
```

## get_ecology_compatiable_list.py

### Usage

```shell
python3 get_ecology_compatiable_list.py
    Export the compatibility list from https://ecology.chinauos.com to an Excel file: ecology_uos_compatiable_list.xls.
```

## get_kylinos_compatiable_list.py

### Usage

```shell
python3 get_kylinos_compatiable_list.py
    Export the compatibility list from https://eco.kylinos.cn to an Excel file: kylinos_compatiable_list.xls.
```
