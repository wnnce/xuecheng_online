package com.zeroxn.xuecheng.content.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zeroxn.xuecheng.content.service.CoursePublishService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lisang
 * @DateTime: 2023/6/19 下午3:05
 * @Description:
 */
@RestController
@Tag(name = "课程审核接口")
public class CoursePublishController {
    private final CoursePublishService coursePublishService;
    public CoursePublishController(CoursePublishService coursePublishService){
        this.coursePublishService = coursePublishService;
    }
    @PostMapping("/courseaudit/commit/{courseId}")
    public void commitAudit(@PathVariable("courseId") Long courseId) throws JsonProcessingException {
        coursePublishService.commitAudit(1232141425L, courseId);
    }
}
