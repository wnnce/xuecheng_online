package com.zeroxn.xuecheng.content.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: lisang
 * @DateTime: 2023/6/29 下午7:07
 * @Description:
 */
@Configuration
public class BeanConfig {
    @Bean
    public SpringEncoder springEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new SpringEncoder(messageConverters);
    }
    @Bean
    public NewTopic newTopic(){
        return new NewTopic("xuecheng", 3, (short) 1);
    }
}
