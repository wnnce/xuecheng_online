package com.zeroxn.xuecheng.learning;

import com.zeroxn.xuecheng.learning.client.ContentClient;
import com.zeroxn.xuecheng.learning.client.MediaClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: lisang
 * @DateTime: 2023-10-24 20:19:15
 * @Description: 学习中心启动器主类
 */
@SpringBootApplication
@MapperScan("com.zeroxn.xuecheng.learning.mapper")
@EnableFeignClients(clients = { MediaClient.class, ContentClient.class})
public class LearningApplication {
    public static void main(String[] args) {
        SpringApplication.run(LearningApplication.class, args);
    }
}
