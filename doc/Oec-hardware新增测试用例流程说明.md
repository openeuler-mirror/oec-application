# Oec-hardware新增测试用例流程说明

### 概述

oec-hardware工具是openEuler社区提供的一款硬件兼容性测试工具，oec-hardware提供服务器整机、板卡与openEuler的兼容性验证测试，验证仅限于基本功能验证，不包括性能测试等其它测试。

硬件厂商在需要验证硬件产品与openEuler的兼容性时，可以使用oec-hardware。社区提供硬件兼容性测试流程，硬件厂商可以参考 [社区兼容性适配流程](https://gitee.com/link?target=https%3A%2F%2Fwww.openeuler.org%2Fzh%2Fcompatibility%2Fhardware%2F) 进行和openEuler的适配。

oec-hardware是一个需要持续演进并广泛适应各大硬件厂商产品特性的测试工具。因此，面对各种各样的硬件时总会出现鞭长莫及的情况。本文档正是基于此种情况，详细说明如何在oec-hardware下新增所需要的测试项。

## 新增硬件类型流程说明

此部分将详细说明oec-hardware怎样添加一个新的硬件类型，以测试工具源代码为立足点。

即：添加一个新的硬件类型应该在仓库中提交什么样的代码，为什么要写这段代码，围绕这段代码南向兼容性组又会做哪些工作。

### 测试工具代码添加[^Notices]

[^Notices]: 此处列出的代码名称及文件数量或其他信息，仅供参考，具体内容请与南向兼容性组协商后确定。

此节内容采用部分oec-hardware源码，案例测试项为network。

1. README.md
   
   * 修改点1：环境要求
     
     如有硬件相关特殊必要项目，请在此处添加对应内容，建议详细说明要求。
     
     若无特殊必要内容，可保持现状，跳过此部分。
   
   * 修改点2：离线安装环境部署要求
     
     测试硬件相关的依赖包，需要提交官方下载地址，并且需要描述对应的保存路径。
   
   * 修改点3：使用步骤-测试套项目
     
     此部分仅需在列表中新增测试项名称即可。
   
   * 修改点4：测试项介绍-已有测试项
     
     此部分需要使用较为简练的文字，说明新增测试项的功能，以及测试用例运行期间需要注意的重要点。

2. docs/design_docs/dev_design.md oec-hardware开发设计文档
   
   * 修改点1：客户端测试项依赖组件
     
     此部分请根据实际情况填写，填写内容要求简洁、明确
     
     | 测试项名称 | 组件名称 | 组件描述 |
     | ----- | ---- | ---- |

3. hwcompatible/compatibility.py
   
   此部分需要根据实际情况选择对应的板卡识别方式，具体场景请查看[oech板卡识别方式]()。
   
   * 修改点：sort_tests()
   
   ```
   def sort_tests(self, devices):
        """
        sort tests
        :param devices:
        :return:
        """
        .
        . 修改处
        .
        return sort_devices
   ```

4. hwcompatible/constants.py
   
   若需要在板卡测试中提供一些必要的值，比如RAID卡测试，需要提供测试盘区，就需要把测试盘区写到```test_config.json```文件中。调用测试套时会从该文件中获取该值，否则不需要修改```test_config.json```文件，但是需要修改```constants.py```文件。
   
   * 修改点：NO_CONFIG_DEVICES 变量
   
   ```
   NO_CONFIG_DEVICES = ("gpu", "vgpu", "nvme", "dpdk", "cdrom", "keycard", "spdk")
   ```

5. hwcompatible/device.py
   
   oech中板卡信息存储在oech文件夹下```/config/pci.ids```文件中，后期可根据板卡四元组定位板卡相关内容；若```pci.ids```文件中没有对应的板卡四元组信息，可先自行补充到文件中，后期随其他文件一同合入。
   
   而```device.py```文件需要添加获取芯片和板卡型号的方法，与```pci.ids```文件联动。
   
   * 修改点：get_model()
     
     ```
     def get_model(self, name, file):
         """
         get board model name
         :return:
         """
     ```

6. tests/****
   
   此部分为新增测试套的测试脚本，内容符合社区代码各项规范即可。
   
   由于oech工具是基于openEuler操作系统并面向多平台的测试工具，所以板卡测试脚本必须精简，目前仅需满足板卡的基本功能测试即可，不需要考虑性能方面。
   
   此部分将会作为新增测试用例代码部分重点，经过三个轮次后方可合入oech工具中。
   
   * 轮次一：南向兼容性成员评审
     
     * 由openEuler南向兼容性组成员就代码命名、函数复用等多个方面进行评论。
   
   * 轮次二：兼容性组sig例会评审
     
     * 由流程发起人员向兼容性组申报议题，并准备相应材料，在会议上向兼容性组成员讲解。经兼容性组成员评审通过后方可进入轮次三。
   
   * 轮次三：新增内容提交至oec-hardware仓库下，由openEuler社区人员提出意见。
   
   ### 南向兼容性测试用例文档补充

此部分待补充。
