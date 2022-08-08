# oech-ci工具

## 工具介绍

本工具将南向兼容性测试工具 oec-hardware 集成到 compass-ci ，通过指定板卡配置文件，实现自动在 compass-ci 的测试机环境中获取存在测试板卡的机器，自动生成需要提交的 job yaml 文件，自动提交任务，实现oec-hardware硬件自动化测试。

1. 非网卡测试，会随机获取一台符合条件的机器进行测试，也可以配置文件中指定机器进行测试。

2. 网卡（NIC/IB）测试，需要在配置文件中指定服务端和客户端测试机名称进行测试。

- 注意：

    目前不支持虚拟机和物理机的混合集群，因为物理机和虚拟机不在统一网段，不能组成集群。
    
- 优化方向：

    关于集群信息的获取，现在是读取lab库，可以优化为从data-api读取。

    读取接口： cci hosts


## 使用方法

### 执行测试方法介绍

    * 调试命令，不真实提交，只是看提交流程是否正确：

            python3 oech_ci.py -o /home/user/tmp \
                                    -j $LKP_SRC/jobs/oech.yaml \
                                    -l /home/user/lab-z9 \
                                    -c ./test_conf.json
    
    * 提交命令：

            python3 oech_ci.py -j $LKP_SRC/jobs/oech.yaml \
                                    -l /home/user/lab-z9 \
                                    -c ./test_conf.json


#### 参数解释

```
-o 

    调试路径，不真实提交，输出处理好的yaml,测试的时候可以用这个参数调试

-j

    oech的job yaml 文件路径

-l

    测试机环境的库

-c 

    板卡文件配置文件，可参考 config 目录下的文件
```

#### 结果查询

处理提交过程会有打印以外，会在当前的目录下生成一个以提交时间为名的目录，例如：

```
2022-05-05-17-32-23
    - ethernet-SP580-14e4-16d7-1402-14e4-cs-s1-a112-c1-a113.yaml
    - submit_result
```

这个submit_result文件包含了提交的信息和job_id,可以根据job_id来查询job的执行过程和结果。

注意测试提交时也会有这个文件，但是没有job_id的。

另一个yaml文件就是提交的完整job yaml(无论测试还是真实提交都会有这个文件)。


### 查询方法

开发中。