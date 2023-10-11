package com.zeroxn.xuecheng.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: lisang
 * @DateTime: 2023-09-22 19:02:32
 * @Description:
 */
@SpringBootApplication
@MapperScan("com.zeroxn.xuecheng.auth.mapper")
@EnableFeignClients(basePackages = {"com.zeroxn.xuecheng.auth.client"})
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}