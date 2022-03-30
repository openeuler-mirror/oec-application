# 工具作用
批量扫描依赖springframwork的rpm包，判断这些包是否受到**spring-beans-\*.jar 格式的jar文件** 的影响，判断rpm包中的jar包是否受到**CachedIntrospectionResuLts.class** 文件影响，输出打印受到影响的rpm包。



# 使用python环境

python3.7




# 使用说明
1. 输入一个参数，第一个参数为批量扫描rpm包的路径

2. 例如执行

   ```
   python3 check_springframwork.py /root/yuanlipeng/check_springframwork/scan_path
   ```

   

