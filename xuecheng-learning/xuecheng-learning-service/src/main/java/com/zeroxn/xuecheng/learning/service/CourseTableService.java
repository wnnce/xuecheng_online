package com.zeroxn.xuecheng.learning.service;

import com.zeroxn.xuecheng.learning.model.dto.ChooseCourseDto;

/**
 * @Author: lisang
 * @DateTime: 2023-10-30 19:19:15
 * @Description:
 */
public interface CourseTableService {
    ChooseCourseDto addChooseCourse(String userId, Long courseId);
}
