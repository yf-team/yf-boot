# 使用说明

> 项目支持使用docker一键启动，集成了MySQL、Nginx、Redis以及做好了相关配置，启动后项目运行在：http://localhost:8686上面，您可以直接访问到项目，但在那之前，您需要自行编译文件及做一些简单的配置；

## 修改Dockerfile

Dockerfile一般情况下您无需修改，但是如果您使用的是arm架构的CPU或者是Macbook M1 M2 系列的电脑，您需要修改一下此文件：

boot/Dockerfile

```shell
# 英特尔芯片用这个，默认
FROM centos:7
# ARM架构的芯片，Mac系统M1/M2...
# FROM amd64/centos:7
```





## 放置后端JAVA文件

您需要在编译项目后得到yf-boot-api.jar文件，位于：yf-boot-api/target/目录下面；将此文件复制到本目录的：boot/api中，目录结构为：

```
.
├── application-local.yml
└── yf-boot-api.jar

```



## 放置前端编译文件

前端项目，使用```pnpm run build:pro``` 在yf-boot-vue根目录下会出现dist-pro文件夹，将dist-pro文件夹下的全部内容复制到：boot/dist中，目录结构为：

```
.
├── assets
├── favicon.ico
├── index.html
└── logo.png
```



## 修改数据库初始化脚本

数据库会跟随docker的启动而初始化好，脚本位置为：mysql/init/init.sql，注意：此文件中包含了创建数据库和初始化数据库的逻辑，一般情况下您无需操作此文件，如果您要自信修改此文件，请用文本工具打开init.sql文件，在说明的下方粘贴您的SQL，如下：

``` sql
-- 开启远程访问，解决无法访问问题
use mysql;
update user set host='%' where user='root';
flush privileges;


-- 创建数据库
create database yf_boot default character set utf8mb4 collate utf8mb4_general_ci;

-- 使用数据库
use yf_boot;

-- 数据库脚本：请将数据库脚本复制到下面
-- 如：create table ...
```



## 整体文件结构预览

假如您仍然对文件结构不太清楚，那么下面是整个docker文件夹的目录结构，如下：

```
.
├── boot
│   ├── Dockerfile
│   ├── api
│   │   ├── application-local.yml
│   │   └── yf-boot-api.jar
│   └── dist
│       ├── assets
│       ├── favicon.ico
│       ├── index.html
│       └── logo.png
├── docker-compose.yml
├── mysql
│   ├── conf
│   │   └── my.cnf
│   ├── db
│   └── init
│       └── init.sql
├── nginx
│   ├── cert
│   └── conf.d
│       └── yf-boot.conf
├── redis
│   └── db
└── startup.sh
```

