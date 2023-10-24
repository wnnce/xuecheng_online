package com.zeroxn.xuecheng.learning.controller;

import com.zeroxn.xuecheng.base.model.PageResult;
import com.zeroxn.xuecheng.learning.model.dto.ChooseCourseDto;
import com.zeroxn.xuecheng.learning.model.dto.CourseTablesDto;
import com.zeroxn.xuecheng.learning.model.entity.CourseTables;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lisang
 * @DateTime: 2023-10-24 20:23:14
 * @Description:
 */
@RestController
@Tag(name = "我的课程表接口")
public class CourseTablesController {
    @PostMapping("/choosecourse/{courseId}")
    public ChooseCourseDto addChooseCourse(@PathVariable("courseId") Long courseId) {
        return null;
    }
    @PostMapping("/choosecourse/learnstatus/{courseId}")
    public CourseTablesDto queryLearnStatus(@PathVariable("courseId") Long courseId) {
        return null;
    }
    @PostMapping("/mycoursetable")
    public PageResult<CourseTables> userCourseTable() {
        return null;
    }
}
