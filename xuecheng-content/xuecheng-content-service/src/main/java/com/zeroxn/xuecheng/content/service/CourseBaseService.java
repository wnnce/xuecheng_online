package com.zeroxn.xuecheng.content.service;

import com.zeroxn.xuecheng.bash.model.PageParams;
import com.zeroxn.xuecheng.bash.model.PageResult;
import com.zeroxn.xuecheng.content.model.DTO.QueryCourseParamsDTO;
import com.zeroxn.xuecheng.content.model.pojo.CourseBase;

/**
 * @Author: lisang
 * @DateTime: 2023/5/11 下午12:29
 * @Description:
 */
public interface CourseBaseService {
    PageResult<CourseBase> queryCourseBaseListByPage(PageParams params, QueryCourseParamsDTO courseParamsDTO);
}
