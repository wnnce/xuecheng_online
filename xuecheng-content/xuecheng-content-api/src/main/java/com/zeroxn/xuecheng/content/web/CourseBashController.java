package com.zeroxn.xuecheng.content.web;

import com.zeroxn.xuecheng.base.model.PageParams;
import com.zeroxn.xuecheng.base.model.PageResult;
import com.zeroxn.xuecheng.content.model.DTO.AddCourseDTO;
import com.zeroxn.xuecheng.content.model.DTO.CourseBaseInfoDTO;
import com.zeroxn.xuecheng.content.model.DTO.QueryCourseParamsDTO;
import com.zeroxn.xuecheng.content.model.pojo.CourseBase;
import com.zeroxn.xuecheng.content.service.CourseBaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author: lisang
 * @DateTime: 2023/5/10 下午9:59
 * @Description:
 */
@Tag(name = "课程信息管理接口")
@RestController
@RequestMapping("/course")
public class CourseBashController {
    private final CourseBaseService courseBaseService;
    public CourseBashController(CourseBaseService courseBaseService){
        this.courseBaseService = courseBaseService;
    }
    @PostMapping("/list")
    @Operation(summary = "查询课程列表")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCourseParamsDTO paramsDTO){
        return courseBaseService.queryCourseBaseListByPage(pageParams, paramsDTO);
    }
    @PostMapping
    @Operation(summary = "添加课程")
    public CourseBaseInfoDTO addCourseBase(@RequestBody AddCourseDTO courseDTO){
        return courseBaseService.addCourseBase(1L, courseDTO);
    }
}
