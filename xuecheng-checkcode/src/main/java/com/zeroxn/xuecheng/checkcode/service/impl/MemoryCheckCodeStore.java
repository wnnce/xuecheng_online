package com.zeroxn.xuecheng.checkcode.service.impl;

import com.zeroxn.xuecheng.checkcode.service.CheckCodeService.CheckCodeStore;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: lisang
 * @DateTime: 2023-10-08 19:26:14
 * @Description:
 */
@Service("MemoryCheckCodeStore")
public class MemoryCheckCodeStore implements CheckCodeStore {
    private static final Map<String, CodeItem> codeCache = new ConcurrentHashMap<>();

    @Override
    public void set(String key, String code, Duration duration) {
        long expireTime = (duration.getSeconds() * 1000) + System.currentTimeMillis();
        CodeItem item = new CodeItem(code, expireTime);
        codeCache.put(key, item);
    }

    @Override
    public String get(String key) {
        CodeItem codeItem = codeCache.get(key);
        if (codeItem == null) {
            return null;
        }
        if (codeItem.expireTime > System.currentTimeMillis()) {
            this.remove(key);
            return null;
        }
        return codeItem.code;
    }

    @Override
    public void remove(String key) {
        codeCache.remove(key);
    }

    private record CodeItem(String code, long expireTime) {}
}