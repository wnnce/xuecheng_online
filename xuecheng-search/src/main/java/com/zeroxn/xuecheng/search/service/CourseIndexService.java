package com.zeroxn.xuecheng.search.service;

import com.zeroxn.xuecheng.search.entity.CourseIndex;

/**
 * @Author: lisang
 * @DateTime: 2023-09-19 20:45:11
 * @Description: 课程搜索Service层接口
 */
public interface CourseIndexService {
    boolean addCourseIndex(CourseIndex courseIndex);
    boolean updateCourseIndex(CourseIndex courseIndex);
    boolean deleteCourseIndex(Long id);
}
