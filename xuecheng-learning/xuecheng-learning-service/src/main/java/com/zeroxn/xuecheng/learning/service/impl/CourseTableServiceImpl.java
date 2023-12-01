package com.zeroxn.xuecheng.learning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zeroxn.xuecheng.base.exception.CustomException;
import com.zeroxn.xuecheng.base.exception.ParamException;
import com.zeroxn.xuecheng.learning.client.ContentClient;
import com.zeroxn.xuecheng.learning.mapper.ChooseCourseMapper;
import com.zeroxn.xuecheng.learning.mapper.CourseTablesMapper;
import com.zeroxn.xuecheng.learning.model.dto.ChooseCourseDto;
import com.zeroxn.xuecheng.learning.model.dto.CourseTablesDto;
import com.zeroxn.xuecheng.learning.model.entity.ChooseCourse;
import com.zeroxn.xuecheng.learning.model.entity.CoursePublish;
import com.zeroxn.xuecheng.learning.model.entity.CourseTables;
import com.zeroxn.xuecheng.learning.service.CourseTableService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @Author: lisang
 * @DateTime: 2023-10-30 19:21:00
 * @Description:
 */
@Service
public class CourseTableServiceImpl implements CourseTableService {
    private final ChooseCourseMapper courseMapper;
    private final CourseTablesMapper tablesMapper;
    private final ContentClient contentClient;

    public CourseTableServiceImpl(ChooseCourseMapper courseMapper, CourseTablesMapper tablesMapper, ContentClient contentClient) {
        this.courseMapper = courseMapper;
        this.tablesMapper = tablesMapper;
        this.contentClient = contentClient;
    }

    @Override
    @Transactional
    public ChooseCourseDto addChooseCourse(String userId, Long courseId) {
        CoursePublish coursePublish = contentClient.queryCoursePublish(courseId);
        if (coursePublish == null) {
            throw new ParamException("课程不存在");
        }
        ChooseCourse course;
        ChooseCourseDto courseDto = new ChooseCourseDto();
        // 添加免费课程的逻辑
        if (coursePublish.getCharge().equals("201000")) {
            course = addChooseCourse(userId, coursePublish, "700001", "701001");
            CourseTables courseTables = addCourseTable(userId, course);
            courseDto.setLearnStatus("702001");
        } else {
            // 添加收费课程的逻辑
            course = addChooseCourse(userId, coursePublish, "700002", "701002");
            courseDto.setLearnStatus("702002");
        }
        BeanUtils.copyProperties(course, courseDto);
        return courseDto;
    }

    @Override
    public CourseTablesDto queryLearningStatus(String userId, Long courseId) {
        // 查询我的课程表
        LambdaQueryWrapper<CourseTables> tableWrapper = new LambdaQueryWrapper<CourseTables>()
                .eq(CourseTables::getUserId, userId)
                .eq(CourseTables::getCourseId, courseId);
        CourseTables courseTables = tablesMapper.selectOne(tableWrapper);
        CourseTablesDto tablesDto = new CourseTablesDto();

        // 查询课表中当前课程为空的处理逻辑
        if (courseTables == null) {
            tablesDto.setLearnStatus("702002");
            return tablesDto;
        }
        BeanUtils.copyProperties(courseTables, tablesDto);
        if (LocalDateTime.now().isAfter(courseTables.getValidtimeEnd())) {
            tablesDto.setLearnStatus("702003");
        } else {
            tablesDto.setLearnStatus("702001");
        }
        return tablesDto;
    }

    /**
     * 添加课程选课记录
     * @param userId 用户Id
     * @param coursePublish 免费课程信息
     * @param orderType 课程类型 700001 免费 700002收费
     * @param status 课程状态 701001 选课成功 701002待支付
     * @return 返回选课记录
     */
    private ChooseCourse addChooseCourse(String userId, CoursePublish coursePublish, String orderType, String status) {
        LambdaQueryWrapper<ChooseCourse> queryWrapper = new LambdaQueryWrapper<ChooseCourse>()
                .eq(ChooseCourse::getUserId, userId)
                .eq(ChooseCourse::getCourseId, coursePublish.getId())
                .eq(ChooseCourse::getOrderType, orderType)
                .eq(ChooseCourse::getStatus, status);
        ChooseCourse chooseCourse = courseMapper.selectOne(queryWrapper);
        if (chooseCourse != null) {
            return chooseCourse;
        }
        LocalDateTime now = LocalDateTime.now();
        chooseCourse = ChooseCourse.builder()
                .courseId(coursePublish.getId())
                .courseName(coursePublish.getName())
                .userId(userId)
                .companyId(coursePublish.getCompanyId())
                .orderType(orderType)
                .createDate(now)
                .status(status)
                .coursePrice(coursePublish.getPrice())
                .validDays(365)
                .validtimeStart(now)
                .validtimeEnd(now.plusDays(365))
                .build();
        int insert = courseMapper.insert(chooseCourse);
        if (insert <= 0) {
            throw new CustomException("添加选课记录失败");
        }
        return chooseCourse;
    }

    /**
     * 将课程添加到我的课程表
     * @param userId 用户ID
     * @param chooseCourse 选课课程
     * @return 返回课程表课程对象
     */
    private CourseTables addCourseTable(String userId, ChooseCourse chooseCourse) {
        LambdaQueryWrapper<CourseTables> queryWrapper = new LambdaQueryWrapper<CourseTables>()
                .eq(CourseTables::getUserId, userId)
                .eq(CourseTables::getCourseId, chooseCourse.getCourseId());
        CourseTables courseTables = tablesMapper.selectOne(queryWrapper);
        if (courseTables != null) {
            return courseTables;
        }
        courseTables = new CourseTables();
        BeanUtils.copyProperties(chooseCourse, courseTables);
        courseTables.setChooseCourseId(chooseCourse.getId());
        courseTables.setCourseType(chooseCourse.getOrderType());
        courseTables.setUpdateDate(LocalDateTime.now());
        int insert = tablesMapper.insert(courseTables);
        if (insert <= 0) {
            throw new CustomException("添加到课程表失败");
        }
        return courseTables;
    }
}