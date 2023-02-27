#### 流程：

1.获取[https://gitee.com/oepkgs/release-management.git](http://gitee.com/oepkgs/release-management.git)仓库中的配置文件。

2.获取配置文件中name字段，name字段为仓库名，可从[https://gitee.com/oepkgs/oepkgs-management.git](http://gitee.com/oepkgs/oepkgs-management.git)中查询到。

3.oepkgs-management的yaml文件中获取upstream仓库地址，并且在该仓库中根据source_dir(openEuler-20.03-LTS-Next)新建分支。

4.在[https://gitee.com/src-openeuler](https://gitee.com/src-openeuler)组织中，获取仓库openEuler-20.03-LTS-Next分支中源码包。

5.将获取到的源码包放入src-oepkgs中openEuler-20.03-LTS-Next分支中。



