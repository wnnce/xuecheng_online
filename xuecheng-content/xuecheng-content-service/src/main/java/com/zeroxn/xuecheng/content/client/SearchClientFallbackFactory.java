package com.zeroxn.xuecheng.content.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: lisang
 * @DateTime: 2023-09-21 20:45:16
 * @Description:
 */
@Component
public class SearchClientFallbackFactory implements FallbackFactory<SearchClient> {
    private static final Logger logger = LoggerFactory.getLogger(SearchClientFallbackFactory.class);
    @Override
    public SearchClient create(Throwable cause) {
        return courseIndex -> {
            logger.error("保存课程索引错误，错误消息：{}，课程ID：{}", cause.getMessage(), courseIndex.getId());
            return false;
        };
    }
}
