package com.zeroxn.xuecheng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author: lisang
 * @DateTime: 2023/5/11 上午8:18
 * @Description: 学成在线内容管理系统启动类
 */

@SpringBootApplication
@MapperScan("com.zeroxn.xuecheng.content.mapper")
@EnableTransactionManagement
public class ContentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentApplication.class, args);
    }
}
