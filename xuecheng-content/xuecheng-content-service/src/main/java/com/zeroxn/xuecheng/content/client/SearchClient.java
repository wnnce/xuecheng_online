package com.zeroxn.xuecheng.content.client;

import com.zeroxn.xuecheng.content.model.pojo.CourseIndex;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: lisang
 * @DateTime: 2023-09-21 20:42:59
 * @Description:
 */
@FeignClient(value = "search", fallbackFactory = SearchClientFallbackFactory.class)
public interface SearchClient {
    @PostMapping("/search/index/course")
    boolean addIndex(@RequestBody CourseIndex courseIndex);
}
