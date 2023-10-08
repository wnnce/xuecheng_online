package com.zeroxn.xuecheng.checkcode.service.impl;

import com.zeroxn.xuecheng.checkcode.service.CheckCodeService.CheckCodeStore;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lisang
 * @DateTime: 2023-10-08 19:39:48
 * @Description:
 */
@Service("RedisCheckCodeStore")
public class RedisCheckCodeStore implements CheckCodeStore {
    private final RedisTemplate<String, String> redisTemplate;

    public RedisCheckCodeStore(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @Override
    public void set(String key, String code, Duration duration) {
        redisTemplate.opsForValue().set(key, code, duration);
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }
}
