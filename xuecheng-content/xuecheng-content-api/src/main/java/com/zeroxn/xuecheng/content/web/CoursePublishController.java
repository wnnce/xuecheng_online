package com.zeroxn.xuecheng.content.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zeroxn.xuecheng.content.model.pojo.CoursePublish;
import com.zeroxn.xuecheng.content.service.CoursePublishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: lisang
 * @DateTime: 2023/6/19 下午3:05
 * @Description:
 */
@RestController
@Tag(name = "课程操作接口", description = "负责课程的审核、发布、下架等")
public class CoursePublishController {
    private final CoursePublishService coursePublishService;
    public CoursePublishController(CoursePublishService coursePublishService){
        this.coursePublishService = coursePublishService;
    }
    @PostMapping("/courseaudit/commit/{courseId}")
    @Operation(description = "课程审核接口")
    @Parameter(name = "courseId", description = "课程ID", required = true)
    public void commitAudit(@PathVariable("courseId") Long courseId) throws JsonProcessingException {
        coursePublishService.commitAudit(1232141425L, courseId);
    }
    @PostMapping("/coursepublish/{courseId}")
    @Operation(description = "课程发布接口")
    @Parameter(name = "courseId", description = "课程ID", required = true)
    public void coursePublish(@PathVariable("courseId") Long courseId){
        coursePublishService.coursePublish(1232141425L, courseId);
    }

    @GetMapping("/r/coursepublish/{courseId}")
    @Operation(description = "查询发布课程接口")
    @Parameter(name = "courseId", description = "课程Id", required = true)
    public CoursePublish queryCoursePublish(@PathVariable("courseId") Long courseId) {
        return coursePublishService.queryCoursePublish(courseId);
    }

}
