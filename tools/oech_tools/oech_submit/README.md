# 脚本功能：
读取板卡配置文件，与测试机的板卡匹配，选出测试机两台，组成一个测试集群。
再根据oech_yaml自动生成需要提交的yaml,自动提交。

非ethernet卡,只需要一台机器符合，另一台机器可以任选。
优先找两台ethernet卡的机器测试，然后测试剩下的卡

也可以在conf里指定测试机集群测试

- 注意：
    目前不支持虚拟机和物理机的混合集群，因为物理机和虚拟机不在统一网段，不能组成集群
    
- 优化方向：
    关于集群信息的获取，现在是读取lab库，可以优化为从data-api读取，
    读取接口： cci hosts


# 使用方法：
```
    本地测试命令，不真实提交，只是看提交流程是否正确：
    python3 oech_submit.py -o /home/user/tmp \
                            -j $LKP_SRC/jobs/oech.yaml \
                            -l /home/user/lab-z9 \
                            -c ./test_conf.json
    提交命令：
    python3 oech_submit.py -j $LKP_SRC/jobs/oech.yaml \
                            -l /home/user/lab-z9 \
                            -c ./test_conf.json
```
### 参数解释
```
-o 
    不真实提交，输出处理好的yaml,测试的时候可以用这个参数调试
-j
    oech的job yaml
-l
    测试机环境的库
-c 
    板卡文件，文件示例：
        {
        "os":"openeuler",
        "os_arch":"aarch64",
        "os_version":"20.03-LTS-SP3",
        "card_info":
        [
                {

                        "name":"ethernet",
                        "boardModel":"SP580",
                        "vendorID":"14e4",
                        "deviceID":"16d7",
                        "svID":"14e4",
                        "ssID":"1402",
                        "test_para":"",
                        "driverlink":"inbox",
                        "server": "taishan200-2280-2s64p-128g--a112",
                        "client": "taishan200-2280-2s64p-128g--a113"
                }
        ]
}
```

# 结果查询
处理提交过程会有打印以外，会在当前的目录下生成一个以提交时间为名的目录，例如：
```
2022-05-05-17-32-23
    - ethernet-SP580-14e4-16d7-1402-14e4-cs-s1-a112-c1-a113.yaml
    - submit_result
```
这个submit_result文件包含了提交的信息和job_id,可以根据job_id来查询job的执行过程和结果。
注意测试提交时也会有这个文件，但是没有job_id的。

另一个yaml文件就是提交的完整job yaml(无论测试还是真实提交都会有这个文件)。
    