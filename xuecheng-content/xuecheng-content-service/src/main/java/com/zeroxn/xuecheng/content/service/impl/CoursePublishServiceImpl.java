package com.zeroxn.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeroxn.xuecheng.base.exception.CustomException;
import com.zeroxn.xuecheng.content.mapper.CoursePublishMapper;
import com.zeroxn.xuecheng.content.model.DTO.CourseBaseInfoDTO;
import com.zeroxn.xuecheng.content.model.DTO.TeachPlanTreeDTO;
import com.zeroxn.xuecheng.content.model.pojo.CourseCategory;
import com.zeroxn.xuecheng.content.model.pojo.CourseMarket;
import com.zeroxn.xuecheng.content.model.pojo.CoursePublish;
import com.zeroxn.xuecheng.content.model.pojo.CourseTeacher;
import com.zeroxn.xuecheng.content.service.CourseBaseService;
import com.zeroxn.xuecheng.content.service.CoursePublishService;
import com.zeroxn.xuecheng.content.service.TeachPlanService;
import com.zeroxn.xuecheng.content.service.async.CourseAsyncTask;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @Author: lisang
 * @DateTime: 2023/6/19 下午3:09
 * @Description:
 */
@Service
public class CoursePublishServiceImpl implements CoursePublishService {
    private final CourseAsyncTask courseAsyncTask;
    private final CourseBaseService courseBaseService;
    private final TeachPlanService teachPlanService;
    private final CoursePublishMapper coursePublishMapper;
    private final ObjectMapper objectMapper;
    public CoursePublishServiceImpl(CourseAsyncTask courseAsyncTask, CourseBaseService courseBaseService,
                                    TeachPlanService teachPlanService, CoursePublishMapper coursePublishMapper,
                                    ObjectMapper objectMapper){
        this.courseAsyncTask = courseAsyncTask;
        this.courseBaseService = courseBaseService;
        this.teachPlanService = teachPlanService;
        this.coursePublishMapper = coursePublishMapper;
        this.objectMapper = objectMapper;
    }
    @Override
    @Transactional
    public void commitAudit(Long companyId, Long courseId) throws JsonProcessingException {
        CourseBaseInfoDTO courseBase = courseBaseService.queryCourseBaseInfoById(courseId);
        if (courseBase == null){
            throw new CustomException("所提交的课程不存在");
        }
        if("202003".equals(courseBase.getAuditStatus())){
            throw new CustomException("课程正在审核中");
        }
        if(!companyId.equals(courseBase.getCompanyId())){
            throw new CustomException("课程机构错误");
        }
        if(StringUtils.isEmpty(courseBase.getPic())){
            throw new CustomException("请添加课程图片");
        }
        List<TeachPlanTreeDTO> treeDTOList = teachPlanService.queryTeachPlanTree(courseId);
        if(treeDTOList == null || treeDTOList.size() == 0){
            throw new CustomException("请添加课程计划");
        }
        CoursePublish coursePublish = new CoursePublish();
        BeanUtils.copyProperties(courseBase, coursePublish);
        coursePublish.setStatus("202003");
        coursePublish.setCompanyId(companyId);
        coursePublish.setCreateDate(LocalDateTime.now());
        CompletableFuture<List<CourseTeacher>> teacherList = courseAsyncTask.listCourseTeacherByCourseId(courseId);
        CompletableFuture<CourseMarket> courseMarket = courseAsyncTask.queryCourseMarketByCourseId(courseId);
        CompletableFuture<CourseCategory> mt = courseAsyncTask.queryCourseCategoryById(courseBase.getMt());
        CompletableFuture<CourseCategory> st = courseAsyncTask.queryCourseCategoryById(courseBase.getSt());
        CompletableFuture.allOf(teacherList, courseMarket, mt, st).join();
        String courseMarketJson = objectMapper.writeValueAsString(courseMarket.join());
        coursePublish.setMarket(courseMarketJson);
        String teacherListJson = objectMapper.writeValueAsString(teacherList.join());
        coursePublish.setTeachers(teacherListJson);
        String treeDTOListJson = objectMapper.writeValueAsString(treeDTOList);
        coursePublish.setTeachplan(treeDTOListJson);
        coursePublish.setMtName(mt.join().getName());
        coursePublish.setStName(st.join().getName());
        Long count = coursePublishMapper.selectCount(new LambdaQueryWrapper<CoursePublish>().eq(CoursePublish::getId, courseId));
        if(count > 0){
            coursePublishMapper.updateById(coursePublish);
        }else {
            coursePublishMapper.insert(coursePublish);
        }
        courseBase.setAuditStatus("202003");
        courseBaseService.updateById(courseBase);
    }
}
