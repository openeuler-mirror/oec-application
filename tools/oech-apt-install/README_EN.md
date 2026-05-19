# oech-apt-install

## Overview

This tool is used to install the tool framework dependencies and test item dependencies of oec-hardware in debian/ubuntu systems. After installing related dependencies using this tool, download and compile the oec-hardware source code.

## Instructions

1. Configure the image source of the system. You are advised to use the Tsinghua image source.

    For details about the ubuntu image source, see https://mirrors.tuna.tsinghua.edu.cn/help/ubuntu/.

2. Install the tool framework dependencies.

    ```bash
    bash oech_install_dep.sh install
    ```

3. Install the test item dependencies.

    ```bash
    bash oech_install_dep.sh test
    ```
