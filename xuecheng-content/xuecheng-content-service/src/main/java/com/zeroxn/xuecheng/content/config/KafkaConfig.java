package com.zeroxn.xuecheng.content.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: lisang
 * @DateTime: 2023/6/20 下午9:06
 * @Description: Kafka配置类
 */
@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic newTopic(){
        return new NewTopic("xuecheng", 3, (short) 1);
    }
}
