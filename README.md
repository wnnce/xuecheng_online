 # SpringCloud微服务学成在线项目
 
一个采用微服务架构的在线学习平台，主要可以分为认证授权模块、内容管理管理、媒资管理模块、课程发布模块和订单支付模块

教学机构可以在内容管理模块中添加课程信息、课程营销信息以及课程章节计划等。

针对课程的配套图片、视频资源可以在媒资模块中上传，针对大文件可以实现分片断点续传，同时还使用XxlJob的定时任务对视频进行转码处理，确保兼容性

当课程添加完成，教学机构可以选择提交课程审核，如果审核通过，那么该课程后续就可以发布。

课程发布时通过分布式事务实现上传课程静态网页到Minio、向Redis、Elasticsearch中写入缓存、索引

如果在发布过程中有任一环节失败，则会向课程发布任务表中写入数据，后续由XxlJob调度任务进行定时重试

认证模块实现了OAuth2认证，用户可以选择多种认证方式进行登录（账号密码、手机号、微信登录）

课程发布完成后，普通用户可以网站中搜索该课程并添加到我的课程表中进行学习。如果是免费课程那么可以直接添加，如果是收费课程，则需要购买成功后才可以添加学习。

## 目录结构
```yaml
xuecheng_online------------------父模块 定义依赖版本
  |
  xuecheng-auth------------------认证模块 提供用户认证和请求授权
  |
  xuecheng-bash------------------项目基础模块 提供一些通用的对象和方法
  |
  xuecheng-checkcode-------------验证码模块 负责验证码生产和校验
  |
  xuecheng-content---------------内容模块 负责课程管理
  |
  xuecheng-gateway---------------网关 负责项目统一入口 限流等
  |
  xuecheng-learning--------------学习模块 负责用户选课 学习记录 课程表信息等
  |
  xuecheng-generator-------------代码生成器 通过实体类生成CRUD代码
  |
  xuecheng-media-----------------媒资模块 负责文件的上传与下载
  |
  xuecheng-orders----------------订单支付模块 负责用户选课支付和订单服务
  |
  xuecheng-search----------------搜索模块 负责课程索引管理和用户搜索
  |
  xuecheng-system----------------系统模块 负责管理统一的数据字典
--
```

## 项目依赖的第三方工具

### MySQL

`Docker`部署

```yml
version: '3'
services:
  mysql:
    restart: always
    image: mysql
    container_name: mysql
    volumes:
      - ./data:/var/lib/mysql
    environment:
      - MYSQL_ROOT_PASSWORD=admin
      - TZ=Asia/Shanghai
    ports:
      - 3307:3306
```

```bash
docker compose up -d
```

### Redis

实机安装

```bash
sudo pacman -S redis

# 修改绑定地址和密码
sudo vim /etc/redis/redis.conf

sudo systemctl start redis
sudo systemctl enable redis
```

### Nacos

实机启动，参考`Nacos`教程

### RabbitMQ

`Docker`部署

```bash
docker run -d --hostname rabbit --name some-rabbit -e RABBITMQ_DEFAULT_USER=root -e RABBITMQ_DEFAULT_PASS=admin -p 15672:15672 -p 5672:5672 rabbitmq:3-management
```

### Elasticsearch & KIbana

`Docker`部署

```bash
# Elasticsearch
docker run --name elasticsearch --net elastic -p 9200:9200 -it -v ./data:/usr/share/elasticsearch/data -v ./plugins:/usr/share/elasticsearch/plugins docker.elastic.co/elasticsearch/elasticsearch:8.7.0

# Kibana
docker run --name kibana --net elastic -p 5601:5601 -v ./data:/usr/share/kibana/data  docker.elastic.co/kibana/kibana:8.7.0
```

### Minio

`Docker`部署

```bash
docker run \
   -p 9000:9000 \
   -p 9010:9010 \
   --name minio \
   -v ~/minio/data:/data \
   -e "MINIO_ROOT_USER=root" \
   -e "MINIO_ROOT_PASSWORD=admin123" \
   quay.io/minio/minio server /data --console-address ":9010"
```

### xxl-job-admin

`Docker`部署

```bash
docker run -e PARAMS="--spring.datasource.url=jdbc:mysql://10.10.10.10:3307/xxl_job?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=Asia/Shanghai --spring.datasource.username=root --spring.datasource.password=admin" -p 0.0.0.0:8090:8080 -v ./logs:/data/applogs --name xxl-job-admin  -d xuxueli/xxl-job-admin:2.4.0
```

