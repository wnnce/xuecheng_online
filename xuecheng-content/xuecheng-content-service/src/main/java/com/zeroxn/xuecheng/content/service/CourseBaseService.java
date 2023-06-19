package com.zeroxn.xuecheng.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeroxn.xuecheng.base.model.PageParams;
import com.zeroxn.xuecheng.base.model.PageResult;
import com.zeroxn.xuecheng.content.model.DTO.CourseDTO;
import com.zeroxn.xuecheng.content.model.DTO.CourseBaseInfoDTO;
import com.zeroxn.xuecheng.content.model.DTO.QueryCourseParamsDTO;
import com.zeroxn.xuecheng.content.model.pojo.CourseBase;

/**
 * @Author: lisang
 * @DateTime: 2023/5/11 下午12:29
 * @Description:
 */
public interface CourseBaseService extends IService<CourseBase> {
    PageResult<CourseBase> queryCourseBaseListByPage(PageParams params, QueryCourseParamsDTO courseParamsDTO);
    CourseBaseInfoDTO addCourseBase(Long companyId, CourseDTO courseDTO);
    CourseBaseInfoDTO queryCourseBaseInfoById(Long courseId);
    CourseBaseInfoDTO updateCourseBase(Long companyId, CourseDTO courseDTO);
}
