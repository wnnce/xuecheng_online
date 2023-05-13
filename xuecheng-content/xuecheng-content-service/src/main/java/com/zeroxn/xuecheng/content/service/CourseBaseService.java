package com.zeroxn.xuecheng.content.service;

import com.zeroxn.xuecheng.base.model.PageParams;
import com.zeroxn.xuecheng.base.model.PageResult;
import com.zeroxn.xuecheng.content.model.DTO.AddCourseDTO;
import com.zeroxn.xuecheng.content.model.DTO.CourseBaseInfoDTO;
import com.zeroxn.xuecheng.content.model.DTO.QueryCourseParamsDTO;
import com.zeroxn.xuecheng.content.model.pojo.CourseBase;

/**
 * @Author: lisang
 * @DateTime: 2023/5/11 下午12:29
 * @Description:
 */
public interface CourseBaseService {
    PageResult<CourseBase> queryCourseBaseListByPage(PageParams params, QueryCourseParamsDTO courseParamsDTO);
    CourseBaseInfoDTO addCourseBase(Long companyId, AddCourseDTO courseDTO);
}
