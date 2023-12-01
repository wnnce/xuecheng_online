package com.zeroxn.xuecheng.learning.web;

import com.zeroxn.xuecheng.base.exception.CustomException;
import com.zeroxn.xuecheng.base.model.PageResult;
import com.zeroxn.xuecheng.learning.model.dto.ChooseCourseDto;
import com.zeroxn.xuecheng.learning.model.dto.CourseTablesDto;
import com.zeroxn.xuecheng.learning.model.entity.CourseTables;
import com.zeroxn.xuecheng.learning.service.CourseTableService;
import com.zeroxn.xuecheng.learning.utils.SecurityUtils;
import com.zeroxn.xuecheng.learning.utils.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lisang
 * @DateTime: 2023-10-24 20:23:14
 * @Description:
 */
@RestController
@Tag(name = "我的课程表接口")
public class CourseTablesController {
    CourseTableService tableService;
    public CourseTablesController(CourseTableService tableService) {
        this.tableService = tableService;
    }
    @PostMapping("/choosecourse/{courseId}")
    public ChooseCourseDto addChooseCourse(@PathVariable("courseId") Long courseId) {
        User user = SecurityUtils.getUser();
        if (user == null) {
            throw new CustomException("请先登录");
        }
        return tableService.addChooseCourse(user.getId(), courseId);
    }
    @PostMapping("/choosecourse/learnstatus/{courseId}")
    public CourseTablesDto queryLearnStatus(@PathVariable("courseId") Long courseId) {
        User user = SecurityUtils.getUser();
        if (user == null) {
            throw new CustomException("请先登录");
        }
        return tableService.queryLearningStatus(user.getId(), courseId);
    }
    @PostMapping("/mycoursetable")
    public PageResult<CourseTables> userCourseTable() {
        return null;
    }
}
