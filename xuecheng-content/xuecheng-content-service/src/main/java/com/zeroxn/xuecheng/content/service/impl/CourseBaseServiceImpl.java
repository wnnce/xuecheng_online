package com.zeroxn.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeroxn.xuecheng.bash.model.PageParams;
import com.zeroxn.xuecheng.bash.model.PageResult;
import com.zeroxn.xuecheng.content.mapper.CourseBaseMapper;
import com.zeroxn.xuecheng.content.model.DTO.QueryCourseParamsDTO;
import com.zeroxn.xuecheng.content.model.pojo.CourseBase;
import com.zeroxn.xuecheng.content.service.CourseBaseService;
import org.springframework.stereotype.Service;

/**
 * @Author: lisang
 * @DateTime: 2023/5/11 下午12:31
 * @Description:
 */
@Service
public class CourseBaseServiceImpl implements CourseBaseService {
    private final CourseBaseMapper courseBaseMapper;
    public CourseBaseServiceImpl(CourseBaseMapper courseBaseMapper){
        this.courseBaseMapper = courseBaseMapper;
    }
    @Override
    public PageResult<CourseBase> queryCourseBaseListByPage(PageParams params, QueryCourseParamsDTO courseParamsDTO) {
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        if(courseParamsDTO != null){
            queryWrapper.like(StringUtils.isNotEmpty(courseParamsDTO.getCourseName()), CourseBase::getName,
                    courseParamsDTO.getCourseName());
            queryWrapper.eq(StringUtils.isNotEmpty(courseParamsDTO.getAuditStatus()), CourseBase::getAuditStatus,
                    courseParamsDTO.getAuditStatus());
            queryWrapper.eq(StringUtils.isNotEmpty(courseParamsDTO.getPublishStatus()),
                    CourseBase::getStatus, courseParamsDTO.getPublishStatus());
        }
        Page<CourseBase> basePage = new Page<>(params.getPageNo(), params.getPageSize());
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(basePage, queryWrapper);
        return new PageResult<>(pageResult.getRecords(), pageResult.getTotal(), params.getPageNo(),
                params.getPageSize());
    }
}
