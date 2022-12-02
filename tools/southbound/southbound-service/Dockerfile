# AS builder 起别名
FROM maven:3.6.3-openjdk-8 AS builder
# 创建临时文件
RUN mkdir /build
# 将src目录复制到临时目录
ADD src /build/src
# 将pom文件复制到临时目录
ADD pom.xml /build

# 设置变量
ENV username=""
ENV password=""
ENV url=""
# 打包
RUN cd /build && mvn -B -ntp package -DskipTests

# 获取jre
FROM openjdk:8-jre-alpine

# 解决时区问题、修改容器的时区为东八区
ENV TZ Asia/Shanghai

# 公开端口
EXPOSE 8099

# 从标记点 拷贝jar包 并改名
COPY --from=builder /build/target/SouthboundDashBoard-0.0.1-SNAPSHOT.jar /app.jar

#设置启动命令
ENTRYPOINT ["sh","-c","java -jar /app.jar --spring.datasource.username=$username --spring.datasource.url=$url --spring.datasource.password=$password"]