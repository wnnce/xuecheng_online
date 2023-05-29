package com.zeroxn.xuecheng.system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: lisang
 * @DateTime: 2023/5/11 下午5:49
 * @Description: 自定义MVC配置类
 */
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {
    // 添加跨域配置
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8602")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
