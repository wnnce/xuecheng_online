package com.zeroxn.xuecheng.checkcode.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Properties;

/**
 * @Author: lisang
 * @DateTime: 2023-10-08 20:23:11
 * @Description:
 */
@Configuration
public class CheckCodeConfig {
    @Bean
    public DefaultKaptcha defaultKaptcha() {
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.textproducer.font.color", "black");
        properties.put("kaptcha.textproducer.char.space", "10");
        properties.put("kaptcha.textproducer.char.length","4");
        properties.put("kaptcha.image.height","34");
        properties.put("kaptcha.image.width","138");
        properties.put("kaptcha.textproducer.font.size","25");
        properties.put("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");
        Config config = new Config(properties);
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        kaptcha.setConfig(config);
        return kaptcha;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisSerializer serializer = new StringRedisSerializer();
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setValueSerializer(serializer);
        return redisTemplate;
    }
}
