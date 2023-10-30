package com.zeroxn.xuecheng.learning.client;

import com.zeroxn.xuecheng.learning.model.entity.CoursePublish;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: lisang
 * @DateTime: 2023-10-24 20:36:49
 * @Description:
 */
@FeignClient(value = "content-api", fallbackFactory = ContentClientFallbackFactory.class)
public interface ContentClient {
    @GetMapping("/content/r/coursepublish/{courseId}")
    CoursePublish queryCoursePublish(@PathVariable("courseId") Long courseId);
}
