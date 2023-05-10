 # SpringCloud微服务学成在线项目

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

