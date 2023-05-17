package com.zeroxn.xuecheng.content.web;

import com.zeroxn.xuecheng.content.model.pojo.CourseTeacher;
import com.zeroxn.xuecheng.content.service.CourseTeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023/5/17 下午3:41
 * @Description: 课程教师管理控制器
 */
@RestController
@RequestMapping("/courseTeacher")
@Tag(name = "课程教师管理接口")
public class CourseTeacherController {
    private final CourseTeacherService teacherService;
    public CourseTeacherController (CourseTeacherService teacherService){
        this.teacherService = teacherService;
    }
    @GetMapping("/list/{id}")
    @Operation(summary = "获取指定课程的教师列表")
    @Parameter(name = "id", description = "课程id", required = true)
    public List<CourseTeacher> listCourseTeacher(@PathVariable("id") Long courseId){
        return teacherService.listCourseTeacherByCourseId(courseId);
    }
    @PostMapping
    @Operation(summary = "添加或者修改教师")
    public CourseTeacher saveCourseTeacher(@RequestBody @Validated CourseTeacher courseTeacher){
        return teacherService.saveCourseTeacher(courseTeacher);
    }
    @DeleteMapping("/course/{courseId}/{teacherId}")
    @Operation(summary = "删除指定课程的指定教师")
    @Parameter(name = "courseId", description = "课程id", required = true)
    @Parameter(name = "teacherId", description = "教师id", required = true)
    public void deleteCourseTeacher(@PathVariable("courseId") Long courseId, @PathVariable("teacherId") Long teacherId){
        teacherService.deleteCourseTeacher(courseId, teacherId);
    }
}
