package com.zeroxn.xuecheng.content.web;

import com.zeroxn.xuecheng.content.model.DTO.CoursePreviewDTO;
import com.zeroxn.xuecheng.content.service.CoursePreviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: lisang
 * @DateTime: 2023/6/14 下午6:46
 * @Description: 课程预览控制器
 */
@Controller
@Tag(name = "课程预览接口", description = "负责渲染预览课程页面并返回渲染后的页面给前端")
public class CoursePublishController {
    private final CoursePreviewService coursePreviewService;
    public CoursePublishController(CoursePreviewService coursePreviewService){
        this.coursePreviewService = coursePreviewService;
    }
    @GetMapping("/coursepreview/{courseId}")
    @Operation(description = "通过课程ID返回渲染好的课程预览页面")
    @Parameter(name = "courseId", description = "课程ID", required = true)
    public ModelAndView preview(@PathVariable("courseId") Long courseId, ModelAndView modelAndView){
        CoursePreviewDTO previewDTO = coursePreviewService.queryCoursePreview(courseId);
        modelAndView.addObject("course", previewDTO);
        modelAndView.setViewName("course_template");
        return modelAndView;
    }
    @GetMapping("/open/course/whole/{courseId}")
    @Operation(description = "通过课程ID返回课程数据")
    @Parameter(name = "courseId", description = "课程ID", required = true)
    public CoursePreviewDTO queryCoursePreview(@PathVariable("courseId") Long courseId){
        return coursePreviewService.queryCoursePreview(courseId);
    }
}
