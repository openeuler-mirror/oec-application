# 一、门禁功能介绍

### 1. 门禁功能
oepkgs (Open External Packages Service) 是一个为 openEuler 操作系统以及其他 Linux 发行版提供软件包服务和容器镜像服务的公共社区。为了保证软件包质量，构建者在gitee提交PR时，会自动触发门禁执行编码规范检查，最后将门禁检查结果返回到PR评论中，帮助开发者定位问题。

门禁代码开源[https://gitee.com/openeuler/openeuler-jenkins](https://gitee.com/openeuler/openeuler-jenkins)

### 2. src-openeuler 门禁检查项
#### 2.1 门禁触发方式
首次提交 PR，或评论/retest

#### 2.2 门禁开始运行标志
![start](../image/start.png)
#### 2.3 门禁检查结果
![](../image/result.png)

# 二、执行流程

### 设计逻辑

- 部署x86-64和aarch64架构下的k8s集群
- 将集群配置为**Jenkins slave**
- **Jenkins master** 运行在x86-64架构k8s集群内

### 流水线任务

> 相同任务只运行一个实例

#### trigger

- 码云触发
- 并行跑门禁任务，cpu架构不限，失败则中止任务并对pr评论
- 成功传递参数给下游 **job**
  - 项目名(**repo**)
  - 分支(**branch**)
  - pull request id(**prid**)
  - 发起者(**committer**)

#### multiarch

- 支持x86_64和aarch64架构
- trigger成功后触发
- 执行[**`python osc_build_k8s.py $repo $arch $WORKSPACE`**](https://gitee.com/src-openeuler/ci_check/blob/k8s/private_build/build/osc_build_k8s.py)进行构建

#### comment

- 收集门禁、build结果
- 调用接口[**提交Pull Request评论**](https://gitee.com/wuyu15255872976/gitee-python-client/blob/master/docs/PullRequestsApi.md#post_v5_repos_owner_repo_pulls)反馈结果给码云
- cpu架构不限

![输入图片说明](../image/overview.png)
![输入图片说明](../image/detail.png)
#### 2.1 Trigger.sh脚本

此脚本主要分为两个步骤

1、下载oepkgs源码

2、执行静态检查（license，spec等）

ac.py文件主要在第二步中执行

```shell
function exec_check() {
  log_info "***** Start to exec static check *****"
  export PYTHONPATH=${shell_path}
  python3 ${shell_path}/src/ac/framework/ac.py \
    -w ${WORKSPACE} -r ${giteeRepoName} -o acfile -t ${GiteeToken} \
    -p ${giteePullRequestIid} -b ${giteeTargetBranch} -a ${GiteeUserPassword} \
    -x ${prCreateTime} -l ${triggerLink} -z ${jobTriggerTime} -m "${comment}" \
    -i ${commentID} -e ${giteeCommitter} --jenkins-base-url ${jenkins_api_host} \
    --jenkins-user ${jenkins_user} --jenkins-api-token ${jenkins_api_token}
  log_info "***** End to exec static check *****"
}
```

#### 2.2 spec

**check_spec.py**

![输入图片说明](../image/check_spec_problem.png)

| 类方法/属性               | 描述                         | 作用说明                                  |
| ------------------------- | ---------------------------- | ----------------------------------------- |
| __init__                  | 初始化                       | CheckSpec实例化对象，初始设置一些参数值   |
| _only_change_package_yaml | 判断是否更改了yaml文件       | 如果本次提交只变更yaml，则无需检查version |
| _is_lts_branch            | 判断是否是lts分支            |                                           |
| check_version             | 检查版本信息                 | 检查当前版本号是否比上一个commit新        |
| check_homepage            | 检查spec文件中的主页url      | 检查主页是否可访问                        |
| check_patches             | 检查spec中的patch            | 检查spec中的patch是否存在                 |
| check_changelog           | 检查changelog中的日期错误    |                                           |
| _ex_exclusive_arch        | 保存spec中exclusive_arch信息 |                                           |
| _ex_pkgship               | pkgship需求                  |                                           |
| _parse_spec               | 获取最新提交的spec文件       | 解析changelog内容                         |
| __call__                  | ·                            | 使CheckSpec的实例对象变为了可调用对象     |

#### 2.3 binary

**check_binary_file.py**
![输入图片说明](../image/check_binary.png)

检查压缩包中的二进制文件

| 类方法/属性                         | 描述                     | 作用说明                                      |
| ----------------------------------- | ------------------------ | --------------------------------------------- |
| __init__                            | 初始化                   | CheckBinaryFile实例化对象，初始设置一些参数值 |
| BINARY_LIST                         | 二进制文件后缀集         |                                               |
| check_compressed_file               | 解压缩包                 |                                               |
| check_binary                        | 检查二进制文件           |                                               |
| _upstream_community_tarball_in_spec | spec指定的上游社区压缩包 | 检查spec指定的上游社区压缩包                  |
| _get_all_file_suffixes              | 获取文件夹中文件后缀     | 获取当前文件中所有文件名后缀,并判断           |
| __call__                            | ·                        | 使CheckBinaryFile的实例对象变为了可调用对象   |

#### 2.4 license
**check_license.py**
**license分为三种：**

Not Free Licenses -> black
Free Licenses -> white
Need Review Licenses -> need review

**最终结果：**

```
self._white_black_list = {license_id: tag, ... }
self._license_translation = {alias: license_id }
```

##### license_in_spec：检查spec文件中的license是否在白名单中

```
def check_license_in_spec(self):
    """
    check whether the license in spec file is in white list
    :return
    """
    if self._spec is None:
        logger.error("spec file not find")
        return FAILED
    rs_code = self._pkg_license.check_license_safe(self._spec.license)
    if rs_code == 0:
        return SUCCESS
    elif rs_code == 1:
        return WARNING
    else:
        logger.error("licenses in spec are not in white list")
        return FAILED
```

1.获取spec中的license信息

2.通过指定接口 (https://compliance2.openeuler.org/sca) 获取相关license信息

3.从接口返回的信息license是否在白名单内

#### 2.5 package yaml

此文件夹中包含两个python文件

- **1.check_yaml.py**

![输入图片说明](../image/check_yaml.png)

检查软件包中的yaml文件

| 类方法/属性            | 描述                   | 作用说明                                                     |
| ---------------------- | ---------------------- | ------------------------------------------------------------ |
| __init__               | 初始化                 | CheckPackageYaml实例化对象，初始设置一些参数值               |
| is_change_package_yaml | 判断是否更改了yaml文件 | 如果本次提交变更了yaml，则对yaml进行检查                     |
| check_fields           | 检查fileds             | 从具体的目标分{tbranch}支下载源码及关联仓库代码，编译软件包、比较软件包差异也需目标分支参数 |
| check_repo             | 检查repo               | 检查yaml的有效性,能否从上游社区获取版本信息                  |
| check_repo_domain      | 检查repo作用域         | 检查spec中source0域名是否包含yaml的version_control,仅做日志告警只返回SUCCESS(autoconf为特例) |
| check_repo_name        | 检查repo名称           | 检查spec中是否包含yaml中src_repo字段的软件名,仅做日志告警只返回SUCCESS |
| __call__               | ·                      | 使CheckPackageYaml的实例对象变为了可调用对象                 |

- **2 check_repo.py**

获取上游社区的release tags
![输入图片说明](../image/tags.png)

| 类名                 | 方法                 | 描述                                        | 作用说明                                       |
| -------------------- | -------------------- | ------------------------------------------- | ---------------------------------------------- |
| AbsReleaseTags       |                      | 获取release tags的抽象类                    |                                                |
| DefaultReleaseTags   |                      | 获取release tags的基类                      |                                                |
|                      | url                  |                                             | 通过src_repo生成url                            |
|                      | get_tags             |                                             | 通过url获取上游社区的release tags              |
| HttpReleaseTagsMixin |                      | 通过web请求形式获取release tags             |                                                |
|                      | get_redirect_resp    |                                             | 获取重定向的url和cookie                        |
|                      | get_request_response |                                             | 获取url请求获取response                        |
| HgReleaseTags        |                      | 获取hg上游社区release tags                  |                                                |
| HgRawReleaseTags     |                      | 获取hg raw上游社区release tags              |                                                |
| MetacpanReleaseTags  |                      | 获取metacpan上游社区release tags            |                                                |
| PypiReleaseTags      |                      | 获取pypi上游社区release tags                |                                                |
| RubygemReleaseTags   |                      | 获取rubygem上游社区release tags             |                                                |
| GnuftpReleaseTags    |                      | 获取gnu-ftp上游社区release tags             |                                                |
| FtpReleaseTags       |                      | 获取ftp上游社区release tags                 |                                                |
| CmdReleaseTagsMixin  |                      | 通过shell命令获取上游社区的release tags     |                                                |
|                      | get_cmd_response     |                                             | 获取shell命令的response                        |
| SvnReleaseTags       |                      | 通过shell svn命令获取上游社区的release tags |                                                |
| GitReleaseTags       |                      | 通过shell git命令获取上游社区的release tags |                                                |
|                      | trans_reponse_tags   |                                             | 解析git命令返回值为纯数字形式的tag             |
| GithubReleaseTags    |                      | 获取github上游社区release tags              |                                                |
| GiteeReleaseTags     |                      | 获取gitee上游社区release tags               |                                                |
| GitlabReleaseTags    |                      | 获取gitlab.gnome上游社区release tags        |                                                |
| ReleaseTagsFactory   |                      | ReleaseTags及其子类的工厂类                 |                                                |
|                      | get_release_tags     |                                             | 通过version control返回对应的ReleaseTags的子类 |


#### 2.6 osc build
![输入图片说明](../image/osc_build.png)
SinglePackageBuild在main_process.sh中进行调用

| 类方法/属性              | 描述                         | 说明                                             |
| ------------------------ | ---------------------------- | ------------------------------------------------ |
| __init__                 | 初始化                       | SinglePackageBuild实例化对象，初始设置一些参数值 |
| get_need_build_obs_repos | 获取需要构建obs repo列表[注] |                                                  |
| build_obs_repos          | build                        |                                                  |
| _handle_package_meta     | _service文件重组             |                                                  |
| _prepare_build_environ   | 准备obs build环境            |                                                  |
| build                    |                              | 主入口                                           |

**注：**

**obs_repos结构：**
[{"repo": repo, "mpac": mpac=mpac, "state": state},{"repo": repo, "mpac": mpac=mpac, "state": state}]

mpac： multibuild package

**build_obs_repos 返回值意义：**

1：osc co 失败

2：准备obs build环境失败

3：osc build失败

- ##### service文件结构说明

```yaml
<services> 1
 <service name="MY_SCRIPT" 2 mode="MODE" 3>
  <param name="PARAMETER1">PARAMETER1_VALUE</param> 4
 </service>
</services>

1、_service文件的根元素。
2、服务名称。 该服务是存储在/ usr / lib / obs / service目录中的脚本。
3、服务模式
4、一个或多个传递到2中定义的脚本的参数。
```

**服务模式**

| Mode         | Runs remotely                                                | Runs locally               | Added File Handling                                          |
| ------------ | ------------------------------------------------------------ | -------------------------- | ------------------------------------------------------------ |
| Default      | After each commit                                            | Before local build         | 生成的文件以“_service：”为前缀                               |
| `trylocal`   | Yes                                                          | Yes                        | Changes are merged into commit                               |
| `localonly`  | No                                                           | Yes                        | Changes are merged into commit                               |
| `serveronly` | Yes                                                          | No                         | 生成的文件以“_service：”为前缀： 当服务不可用或无法在开发人员工作站上运行时，这可能很有用。 |
| `buildtime`  | During each build before calling the build tool (for example, rpm-build)[a] | Before each build[a]       |                                                              |
| `manual`     | No                                                           | Only via explicit CLI call | Exists since OBS 2.11                                        |
| `disabled`   | No                                                           | Only via explicit CLI call |                                                              |
|              |                                                              |                            | 注：[a] A side effect is that the service package is becoming a build dependency and must be available. |

注：[a] A side effect is that the service package is becoming a build dependency and must be available.

**osc build 相关参数说明：**

**--no-verify, --noverify** 构建包时跳过签名验证（通过PGP密钥） （在OSCRC中的全局配置：no_verify）

**--noservice, --no-service**  跳过_service文件中指定的本地源服务的运行。

**-M MPAC, --multibuild-package=MPAC** 构建指定的多个包

**--userootforbuild** 以root身份构建。默认值是构建为非特权用户。请注意，SPEC文件中的“＃norootforbuild”在“＃norootforbuild”将使此选项无效。

**--disable-debuginfo** 禁用DebugInfo软件包的构建

**--disable-cpio-bulk-download** 禁用从API中将下载的包作为CPIO存档 （cpio是个归档工具）

#### 2.7 ci_mistake
![输入图片说明](../image/ci_mistake.png)

ci_mistake.py在jenkins工程中配置调用

```shell
#!/bin/bash
shell_path=/home/jenkins/ci_check 
 export PYTHONPATH=${shell_path}
 . ${shell_path}/src/lib/lib.sh
 
 echo $comment
 #echo "[7, 8]" > "build_no_list.yaml"
 
 repo_owner=${giteeTargetNamespace} 
 
 # debug测试变量
function config_debug_variable() {
  if [[ "${repo_owner}" == "" ]]; then
    repo_owner="src-openeuler"
    repo_server_test_tail=""
  elif [[ "${repo_owner}" != "src-openeuler" && "${repo_owner}" != "openeuler" ]]; then
    repo_server_test_tail="-test"
  fi
}
config_debug_variable

build_num_file="${repo_owner}_${giteeRepoName}_${giteePullRequestIid}_build_num.yaml"
if [[ -e build_num_file ]]; then
  rm $build_num_file
fi
fileserver_tmpfile_path="/repo/soe${repo_server_test_tail}/check_item"
scp -r -i ${SaveBuildRPM2Repo} -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null root@${repo_server}:$fileserver_tmpfile_path/${build_num_file} . || log_info "file ${build_num_file} not exist"

 log_info "***** Start to exec ci_mistake, repo:${giteeRepoName} *****"
 python3 ${shell_path}/src/utils/ci_mistake.py --pr_url ${triggerLink} --mistake_comment "${comment}" \
 		--committer ${commentCommitter} --commit_at ${jobTriggerTime} --comment_id ${commentID} \
        --build_no_filepath ${build_num_file} --gitee_token ${GiteeToken}
 log_info "***** End to exec ci_mistake *****"
```

| 类方法/属性          | 描述                               | 说明                           |
| -------------------- | ---------------------------------- | ------------------------------ |
| __init__             | 初始化                             | 实例化对象，初始设置一些参数值 |
| load_build_no_list   | 加载构建号列表                     |                                |
| check_command_format | 分析ci错误评论格式                 |                                |
| get_owner_repo_id    | 从pr链接获取所有者、repo和 pr id   |                                |
| comment_to_pr        | 在相关pr里评论                     |                                |
| send_ci_mistake_data | 通过kafka向数据库发送消息          |                                |
| process              | 分析ci错误内容并保存到数据库的过程 | 主入口                         |

**说明：**

错误评论格式：/ci_unmistake build_no, /ci_mistake build_no <mistake_type> <ci_mistake_stage>

1. build_no 必须设置，并且与历史触发的构建号一致
     2. <mistake_type>参数可以设置0个或1个，但都应该在support_mistake_type中
            3. <mistake_stage>参数可以设置任意数量，但都应该在support_mistake_stage
                4. <mistake_type> 和 <mistake_stage> 可以乱序

如下所示：

```
/ci_mistake 77 obs check_build check_install
description：评论描述信息
```

**77**表示本次构建号
**check_build**和**check_install**存在误报，误报类型为**obs** 
若想取消误报标记，可以用/ci_unmistake 77取消

```python 
# 取两个集合的交集
ci_mistake_type_list = list(set(ci_mistake_type_stage).intersection(set(self.support_mistake_type)))
ci_mistake_stage = list(set(ci_mistake_type_stage).intersection(set(self.support_check_stage)))
# 取集合的差集
ci_mistake_others = list(set(ci_mistake_type_stage).difference(set(self.support_mistake_type)).difference(set(self.support_check_stage)))

```

发送给kafka的数据样例：

```
message = {
	 'pr_url': 'https://gitee.com/xxx/alsa-tools/pulls/1', 
     'build_no': 19, 
     'committer': 'xxx',
     'commit_at': 1652250626.0, # 固定时间，pr提交时间
     'update_at': 1652250654.1, # 评论的时间，每次评论都会自动触发
     'ci_mistake_type': 'ci', # （ci、obs、infra）任选其一
     'ci_mistake_stage': ['check_package_license'], # 所有类型["check_binary_file", "check_package_license", "check_package_yaml_file",
                           "check_spec_file", "check_build", "check_install", "compare_package", "build_exception"]
     'description': '测试专用\nThe clause "The Software shall be used for Good, not Evil." is impossible to parse or comply with.',
     'ci_mistake_status': True
     }
```

comment_to_dashboard.py

| 类方法/属性             | 描述                    | 说明   |
| ----------------------- | ----------------------- | ------ |
| output_build_num        | 输出构建号              |        |
| get_all_result_to_kafka | 将所有的结果发送到kafka | 主入口 |

**说明：base_dict结构及详细参数如下**

```
base_dict = {"pr_title": args_list.pr_title,
             "pr_url": args_list.pr_url,
             "pr_create_at": pr_create_time,
             "pr_committer": args_list.committer,
             "pr_branch": args_list.tbranch,
             "build_at": trigger_time,
             "update_at": current_time,
             "build_no": args_list.trigger_build_id,
             "build_time":round(current_time - trigger_time, 1),
             "build_exception":True or False
             }

注： 时间格式 ："2022-04-22T14:19:26+08:00"
	通过round函数转换之后：1650608366.0
    名称            类型   必选  说明
    pr_url          字符串  是   需要进行上报的pr地址，(pr_url, build_no)共同确定一次门禁结果
    pr_title        字符串  是   pr标题
    pr_create_at    数值    是   pr创建时间戳
    pr_committer    字符串  是   pr提交人
    pr_branch       字符串  是   pr目标分支
    build_no        数值    是   门禁评论工程构建编号，区分同一个pr的多次门禁结果
    build_at        数值    是   门禁触发时间戳
    update_at       数值    是   当前时间对应的时间戳
    build_exception 布尔    是   门禁执行是否异常，异常情况部分字段可以为空
    build_urls      字典    是   包含多个门禁工程链接和显示文本
    build_time      数值    是   整体构建时间(单位秒) trigger触发时间~comment时间
    check_total     字符串  是   门禁整体结果
    check_details   字典    是   门禁各个检查项结果 
    
base_dict = {'pr_title': '测试PR',
             'pr_url': 'https://gitee.com/zhengyaohui/pkgship/pulls/5',
             'pr_create_at': 1651044410.0,
             'pr_committer': 'xxx',
             'pr_branch': 'openEuler-22.03-LTS',
             'build_at': 1651051963.0,
             'update_at': 1651113562.5,
             'build_no': 50,
             'build_time': 0,
             'build_exception': False,
             'build_urls': {
                 'aarch64': 'https://openeulerjenkins.osinfra.cn/job/multiarch-ci-test/job/src-openeuler/job/aarch64/job/pkgship/49/',
                 'comment': 'https://openeulerjenkins.osinfra.cn/job/multiarch-ci-test/job/src-openeuler/job/comment/job/pkgship/95',
                 'trigger': 'https://openeulerjenkins.osinfra.cn/job/multiarch-ci-test/job/src-openeuler/job/trigger/job/pkgship/50/',
                 'x86-64': 'https://openeulerjenkins.osinfra.cn/job/multiarch-ci-test/job/src-openeuler/job/x86-64/job/pkgship/44/'},
             'check_details': {
                 'aarch64': {'check_build': 'SUCCESS', 'check_install': 'SUCCESS', 'compare_package': 'SUCCESS'},
                 'static_code': {'check_binary_file': 'SUCCESS', 'check_package_license': 'SUCCESS',
                                 'check_package_yaml_file': 'SUCCESS', 'check_spec_file': 'FAILED'},
                 'x86_64': {'check_build': 'SUCCESS', 'check_install': 'SUCCESS', 'compare_package': 'SUCCESS'}},
             'check_total': 'SUCCESS'}}
```

gitee_comment.py

| 类方法/属性                         | 描述                    | 说明                                  |
| ----------------------------------- | ----------------------- | ------------------------------------- |
| __init__                            | 初始化                  | Comment实例化对象，初始设置一些参数值 |
| comment_build                       | 获取构建结果            |                                       |
| comment_compare_package_details     | compare package结果上报 |                                       |
| comment_at                          | 通知committer           |                                       |
| check_build_result                  | 检查构建结果            |                                       |
| _get_upstream_builds                | 获取上游构建            |                                       |
| _comment_build_html_format          | 组装构建信息，并评论pr  |                                       |
| _comment_of_ac                      | 组装门禁检查结果        |                                       |
| _comment_of_compare_package_details | compare package详细信息 |                                       |
| _comment_of_check_item              | 检查项目评论            |                                       |
| comment_html_table_th               | 组装表格结构（表头）    | 类方法                                |
| comment_html_table_tr               | 组装表格结构（内容）    | 类方法                                |
| comment_html_table_tr_rowspan       | 组装表格结构（内容）    | 类方法                                |
| _get_job_url                        | 获取任务url             |                                       |
| _get_all_job_result                 | 获取任务结果            |                                       |
| get_all_result_to_kafka             | 发送结果到kafka         |                                       |

**说明：**

构建结果数据示例

```html
ac comment: 
['<tr><td colspan=2>check_binary_file</td> <td>:white_check_mark:<strong>SUCCESS</strong></td> <td rowspan=4><a href=https://openeulerjenkins.osinfra.cn/job/multiarch-ci-test/job/src-openeuler/job/trigger/job/pkgship/50/console>#50</a></td></tr>', '<tr><td colspan=2>check_package_license</td> <td>:white_check_mark:<strong>SUCCESS</strong></td></tr>', '<tr><td colspan=2>check_package_yaml_file</td> <td>:white_check_mark:<strong>SUCCESS</strong></td></tr>', '<tr><td colspan=2>check_spec_file</td> <td>:x:<strong>FAILED</strong></td></tr>']

check item comment: 
['<tr><td rowspan=2>x86_64</td> <td>check_build</td> <td>:white_check_mark:<strong>SUCCESS</strong></td> <td rowspan=2><a href=https://openeulerjenkins.osinfra.cn/job/multiarch-ci-test/job/src-openeuler/job/x86-64/job/pkgship/44/console>#44</a></td></tr>', '<tr><td>check_install</td> <td>:white_check_mark:<strong>SUCCESS</strong></td>', '<tr><td rowspan=2>aarch64</td> <td>check_build</td> <td>:white_check_mark:<strong>SUCCESS</strong></td> <td rowspan=2><a href=https://openeulerjenkins.osinfra.cn/job/multiarch-ci-test/job/src-openeuler/job/aarch64/job/pkgship/49/console>#49</a></td></tr>', '<tr><td>check_install</td> <td>:white_check_mark:<strong>SUCCESS</strong></td>']

compare package comment: 
['<table> <tr><th>Arch Name</th> <th>Check Items</th> <th>Rpm Name</th> <th>Check Result</th> <th>Build Details</th></tr>', '<tr><td rowspan=5>compare_package(x86_64)</td> <td>add_rpms</td> <td></td> <td>:white_check_mark:<strong>SUCCESS</strong></td> <td rowspan=5><a href=https://openeulerjenkins.osinfra.cn/job/multiarch-ci-test/job/src-openeuler/job/x86-64/job/pkgship/44/console>#44</a></td></tr>', '<tr><td>delete_rpms</td> <td></td> <td>:white_check_mark:<strong>SUCCESS</strong></td></tr>', '<tr><td>rpm_files</td> <td></td> <td>:white_check_mark:<strong>SUCCESS</strong></td></tr>', '<tr><td>rpm_provides</td> <td></td> <td>:white_check_mark:<strong>SUCCESS</strong></td></tr>', '<tr><td>rpm_requires</td> <td></td> <td>:white_check_mark:<strong>SUCCESS</strong></td></tr>', '<tr><td rowspan=5>compare_package(aarch64)</td> <td>add_rpms</td> <td></td> <td>:white_check_mark:<strong>SUCCESS</strong></td> <td rowspan=5><a href=https://openeulerjenkins.osinfra.cn/job/multiarch-ci-test/job/src-openeuler/job/aarch64/job/pkgship/49/console>#49</a></td></tr>', '<tr><td>delete_rpms</td> <td></td> <td>:white_check_mark:<strong>SUCCESS</strong></td></tr>', '<tr><td>rpm_files</td> <td></td> <td>:white_check_mark:<strong>SUCCESS</strong></td></tr>', '<tr><td>rpm_provides</td> <td></td> <td>:white_check_mark:<strong>SUCCESS</strong></td></tr>', '<tr><td>rpm_requires</td> <td></td> <td>:white_check_mark:<strong>SUCCESS</strong></td></tr>', '</table>']
```

 **网页效果如下**
![输入图片说明](../image/ac_comment.png)
![输入图片说明](../image/compare_package_comment.png)

```python
all_dict = {
    'build_urls': {
        'trigger': 'https://openeulerjenkins.osinfra.cn/job/multiarch-ci-test/job/src-openeuler/job/trigger/job/pkgship/50/',
        'comment': 'https://openeulerjenkins.osinfra.cn/job/multiarch-ci-test/job/src-openeuler/job/comment/job/pkgship/95',
        'x86-64': 'https://openeulerjenkins.osinfra.cn/job/multiarch-ci-test/job/src-openeuler/job/x86-64/job/pkgship/44/',
        'aarch64': 'https://openeulerjenkins.osinfra.cn/job/multiarch-ci-test/job/src-openeuler/job/aarch64/job/pkgship/49/'},
    'check_total': 'SUCCESS',
    'check_details': {
        'static_code': {'check_binary_file': 'SUCCESS', 'check_package_license': 'SUCCESS',
                        'check_package_yaml_file': 'SUCCESS', 'check_spec_file': 'FAILED'},
        'x86_64': {'check_build': 'SUCCESS', 'check_install': 'SUCCESS', 'compare_package': 'SUCCESS'},
        'aarch64': {'check_build': 'SUCCESS', 'check_install': 'SUCCESS', 'compare_package': 'SUCCESS'}}}
说明：
名称            类型    必选  说明
build_urls      字典    是    包含多个门禁工程链接和显示文本
check_total     字符串  是    门禁整体结果
check_details   字典    是    门禁各个检查项结果
```
# 三、trigger阶段参数列表
| 参数名               | 默认值                           | 描述                                           | 来源            |
| -------------------- | -------------------------------- | ---------------------------------------------- | --------------- |
| repo_server          | 121.36.53.23                     | repo地址，用来存储工程之间共享的文件服务器     | 自定义          |
| giteeRepoName        | repository.name                  | gitee仓库名                                    | Webhook         |
| giteePullRequestIid  | pull_request.number              | prid                                           | Webhook         |
| giteeSourceBranch    | pull_request.head.ref            | PR源代码分支                                   | Webhook         |
| giteeTargetBranch    | pull_request.base.ref            | PR目标代码分支                                 | Webhook         |
| giteeSourceNamespace | pull_request.head.repo.namespace | PR源命名空间（openeuler/src-openeuler/用户名） | Webhook         |
| giteeTargetNamespace | pull_request.base.repo.namespace | PR目标命名空间（openeuler/src-openeuler/用户名 | Webhook         |
| giteeCommitter       | pull_request.user.login          | 提交人                                         | Webhook         |
| comment              | comment.body                     | 评论内容                                       | Webhook         |
| commentID            | comment.id                       | 评论id                                         | Webhook         |
| jobTriggerTime       | comment.updated_at               | 门禁触发时间                                   | Webhook         |
| prCreateTime         | pull_request.created_at          | PR创建时间                                     | Webhook         |
| triggerLink          | comment.html_url                 | 触发门禁的评论url                              | Webhook         |
| jenkins_user         | jenkins_api_token                | jenkins api的用户名和token                     | jenkins凭证设置 |
| GiteeToken           | openeuler-ci-bot                 | openeuler-ci-bot 评论gitee api token           | jenkins凭证设置 |
| SaveBuildRPM2Repo    | jenkins凭证设置处获取            | sshkey（将打包结果保存到repo的ssh key）        | jenkins凭证设置 |
| GiteeUserPassword    | openeuler_ci_bot                 | 获取代码账号                                   | jenkins凭证设置 |