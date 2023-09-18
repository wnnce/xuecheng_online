package com.zeroxn.xuecheng.content.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: lisang
 * @DateTime: 2023-09-18 17:13:17
 * @Description: MediaClient熔断后的降级处理策略
 */
@Component
public class MediaClientFallbackFactory implements FallbackFactory<MediaClient> {
    private static final Logger logger = LoggerFactory.getLogger(MediaClientFallbackFactory.class);
    @Override
    public MediaClient create(Throwable cause) {
        return (MultipartFile file, String objectName) -> {
            logger.error("调用MediaClient发生熔断，错误消息：{}", cause.getMessage());
            return null;
        };
    }
}
