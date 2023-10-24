package com.zeroxn.xuecheng.content.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @Author: lisang
 * @DateTime: 2023-10-24 19:05:42
 * @Description:
 */
@Component
public class SecurityUtils {
    private static final Logger logger = LoggerFactory.getLogger(SecurityUtils.class);
    private static ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        SecurityUtils.objectMapper = objectMapper;
    }

    public static User getUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            return objectMapper.readValue(name, User.class);
        }catch (JsonProcessingException ex) {
            logger.error("反序列化用户信息失败，错误信息：{}", ex.getMessage());
        }
        return null;
    }
}
