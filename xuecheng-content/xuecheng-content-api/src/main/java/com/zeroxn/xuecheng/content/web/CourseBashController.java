package com.zeroxn.xuecheng.content.web;

import com.zeroxn.xuecheng.base.exception.ValidationGroups;
import com.zeroxn.xuecheng.base.model.PageParams;
import com.zeroxn.xuecheng.base.model.PageResult;
import com.zeroxn.xuecheng.content.model.DTO.CourseDTO;
import com.zeroxn.xuecheng.content.model.DTO.CourseBaseInfoDTO;
import com.zeroxn.xuecheng.content.model.DTO.QueryCourseParamsDTO;
import com.zeroxn.xuecheng.content.model.pojo.CourseBase;
import com.zeroxn.xuecheng.content.service.CourseBaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


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
    public CourseBaseInfoDTO addCourseBase(@RequestBody @Validated(ValidationGroups.Insert.class) CourseDTO courseDTO){
        return courseBaseService.addCourseBase(1L, courseDTO);
    }
    @GetMapping("/{id}")
    @Operation(summary = "获取课程信息")
    @Parameter(name = "id", description = "课程id", required = true)
    public CourseBaseInfoDTO getCourseBase(@PathVariable("id") Long courseId){
        return courseBaseService.queryCourseBaseInfoById(courseId);
    }
    @PutMapping
    @Operation(summary = "修改课程信息")
    public CourseBaseInfoDTO updateCourseBase(@RequestBody @Validated(ValidationGroups.Update.class) CourseDTO courseDTO){
        return courseBaseService.updateCourseBase(1232141425L, courseDTO);
    }
}
