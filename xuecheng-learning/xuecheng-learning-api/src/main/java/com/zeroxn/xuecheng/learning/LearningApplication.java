package com.zeroxn.xuecheng.learning;

import com.zeroxn.xuecheng.learning.client.MediaClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: lisang
 * @DateTime: 2023-10-24 20:19:15
 * @Description: 学习中心启动器主类
 */
@SpringBootApplication
@EnableFeignClients(clients = { MediaClient.class })
public class LearningApplication {
    public static void main(String[] args) {
        SpringApplication.run(LearningApplication.class, args);
    }
}
