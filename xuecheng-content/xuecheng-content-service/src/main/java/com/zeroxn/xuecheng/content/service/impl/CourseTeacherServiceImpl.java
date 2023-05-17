package com.zeroxn.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zeroxn.xuecheng.base.exception.CustomException;
import com.zeroxn.xuecheng.content.mapper.CourseTeacherMapper;
import com.zeroxn.xuecheng.content.model.pojo.CourseTeacher;
import com.zeroxn.xuecheng.content.service.CourseTeacherService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023/5/17 下午3:49
 * @Description:
 */
@Service
public class CourseTeacherServiceImpl implements CourseTeacherService {
    private final CourseTeacherMapper teacherMapper;
    public CourseTeacherServiceImpl(CourseTeacherMapper teacherMapper){
        this.teacherMapper = teacherMapper;
    }
    @Override
    public List<CourseTeacher> listCourseTeacherByCourseId(Long courseId) {
        LambdaQueryWrapper<CourseTeacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CourseTeacher::getCourseId, courseId);
        return teacherMapper.selectList(queryWrapper);
    }

    @Override
    public CourseTeacher saveCourseTeacher(CourseTeacher courseTeacher) {
        if(courseTeacher.getId() == null){
            courseTeacher.setCreateDate(LocalDateTime.now());
            teacherMapper.insert(courseTeacher);
        }else {
            teacherMapper.updateById(courseTeacher);
        }
        return courseTeacher;
    }

    @Override
    public void deleteCourseTeacher(Long courseId, Long teacherId) {
        LambdaQueryWrapper<CourseTeacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CourseTeacher::getCourseId, courseId).eq(CourseTeacher::getId, teacherId);
        int num = teacherMapper.delete(queryWrapper);
        if(num < 1){
            throw new CustomException("教师删除失败");
        }
    }
}
