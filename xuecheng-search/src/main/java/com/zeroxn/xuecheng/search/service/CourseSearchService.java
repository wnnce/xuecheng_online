package com.zeroxn.xuecheng.search.service;

import com.zeroxn.xuecheng.base.model.PageParams;
import com.zeroxn.xuecheng.search.dto.SearchCourseParamDto;
import com.zeroxn.xuecheng.search.dto.SearchPageResultDto;
import com.zeroxn.xuecheng.search.entity.CourseIndex;

/**
 * @Author: lisang
 * @DateTime: 2023-09-19 20:45:35
 * @Description:
 */
public interface CourseSearchService {
    SearchPageResultDto<CourseIndex> queryCourseIndex(PageParams params, SearchCourseParamDto courseDto);
}
