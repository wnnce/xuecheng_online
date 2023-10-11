package com.zeroxn.xuecheng.auth.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: lisang
 * @DateTime: 2023-10-10 18:50:21
 * @Description:
 */

@Component
public class CheckCodeClientFallbackFactory implements FallbackFactory<CheckCodeClient> {
    private static final Logger logger = LoggerFactory.getLogger(CheckCodeClientFallbackFactory.class);
    @Override
    public CheckCodeClient create(Throwable cause) {
        return (String key, String code) -> {
            logger.error("调用验证码服务报错，key:{},code:{}", key, code);
            return false;
        };
    }
}
