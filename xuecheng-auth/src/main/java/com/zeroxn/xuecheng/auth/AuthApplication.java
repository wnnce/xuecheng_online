package com.zeroxn.xuecheng.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: lisang
 * @DateTime: 2023-09-22 19:02:32
 * @Description:
 */
@SpringBootApplication
@MapperScan("com.zeroxn.xuecheng.auth.mapper")
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}