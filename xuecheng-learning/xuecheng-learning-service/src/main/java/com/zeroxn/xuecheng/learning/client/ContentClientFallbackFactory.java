package com.zeroxn.xuecheng.learning.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: lisang
 * @DateTime: 2023-10-26 20:12:23
 * @Description:
 */
@Component
public class ContentClientFallbackFactory implements FallbackFactory<ContentClient> {
    private static final Logger logger = LoggerFactory.getLogger(ContentClientFallbackFactory.class);
    @Override
    public ContentClient create(Throwable cause) {
        logger.error("调用查询课程发布接口失败，错误信息：{}", cause.getMessage());
        return null;
    }
}
