package com.zeroxn.xuecheng.learning.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * @Author: lisang
 * @DateTime: 2023-10-24 20:42:35
 * @Description:
 */
public class MediaClientFallbackFactory implements FallbackFactory<MediaClient> {
    private static final Logger logger = LoggerFactory.getLogger(MediaClientFallbackFactory.class);
    @Override
    public MediaClient create(Throwable cause) {
        return (mediaId -> {
            logger.error("获取媒体信息错误，错误消息:{}", cause.getMessage());
            return null;
        });
    }
}
