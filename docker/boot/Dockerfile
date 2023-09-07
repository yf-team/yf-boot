# 英特尔芯片用这个，默认
FROM centos:7
# ARM架构的芯片，Mac系统M1/M2...
# FROM amd64/centos:7

# 添加时区文件
RUN echo "Asia/Shanghai" > /etc/timezone


# 安装java
RUN yum install -y java-1.8.0-openjdk-devel.x86_64

#清理缓存,减少镜像大小
RUN yum clean all

# 运行项目
VOLUME /data/run/api
CMD sleep 10s && cd /data/run/api && java -jar yf-boot-api.jar --spring.config.location=application-local.yml
