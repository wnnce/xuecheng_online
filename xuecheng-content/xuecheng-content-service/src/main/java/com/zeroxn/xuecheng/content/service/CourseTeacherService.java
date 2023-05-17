package com.zeroxn.xuecheng.content.service;

import com.zeroxn.xuecheng.content.model.pojo.CourseTeacher;

import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023/5/17 下午3:48
 * @Description: 课程教师管理服务层
 */
public interface CourseTeacherService{
    /**
     * 通过课程id获取该课程的教师信息
     * @param courseId 课程id
     * @return 该课程的教师列表或空
     */
    List<CourseTeacher> listCourseTeacherByCourseId(Long courseId);

    /**
     * 新增/修改课程教师
     * @param courseTeacher 课程教师
     */
    CourseTeacher saveCourseTeacher(CourseTeacher courseTeacher);

    /**
     * 删除指定课程的指定教师
     * @param courseId 课程id
     * @param teacherId 教师id
     */
    void deleteCourseTeacher(Long courseId, Long teacherId);
}
