# oech-ci

English | [简体中文](./README.md)

## Tool Introduction

This tool integrates the southbound compatibility test tool oec-hardware into compass-ci. By specifying the board configuration file, the tool automatically obtains the machine with the test board from the compass-ci test machine environment, generates the job YAML file to be submitted, and submits the task, implementing automatic oec-hardware tests.

1. For a non-NIC test, a machine that meets the requirements is randomly obtained for the test. You can also specify a machine in the configuration file for the test.

2. To test the NIC (NIC/IB), you need to specify the names of the server and client test machines in the configuration file.

- Notes:

    Currently, the hybrid cluster of VMs and physical machines is not supported because physical machines and VMs are not in the same network segment and cannot form a cluster.
    
- Optimization solution:

    Currently, the cluster information is read from the lab library, which can be optimized to be read from the data-api.

    Read interface: cci hosts

## How to Use

### Introduction to the Test Method

    * Debugging command, which is not submitted actually. It is used to check whether the submission process is correct.

            cd oech-ci/main

            python3 oech_ci.py -o /home/user/tmp -j $LKP_SRC/jobs/oec-hardware.yaml -l /home/user/lab-z9 -c ../config/test_config.json
    
    * Submission commands:

            cd oech-ci/main
            
            python3 oech_ci.py -j $LKP_SRC/jobs/oec-hardware.yaml -l /home/user/lab-z9 -c ../config/test_config.json

#### Parameter Description

```text
-o 

    Debugging path, which is not submitted actually, outputs the processed YAML file. This parameter can be used for debugging during the test.

-j

    Path of the job YAML file of the oec-hardware.

-l

    Library of the test machine environment.

-c 

    Board file configuration file. For details, see the files in the **config** directory.
```

#### Result Query

In addition to printing during submission, a directory named after the submission time is generated in the current directory. For example:

```text
2022-05-05-17-32-23
    - ethernet-SP580-14e4-16d7-1402-14e4-cs-s1-a112-c1-a113.yaml
    - submit_result
```

The **submit_result** file contains the submitted information and job_id. You can query the job execution process and result based on the job_id.

Note that this file also exists during test submission, but the job_id file does not exist.

The other YAML file is the complete job YAML file submitted (this file is available for both test and actual submission).

### Query Method

Under development
