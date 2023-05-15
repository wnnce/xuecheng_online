package com.zeroxn.xuecheng.content.service;

import com.zeroxn.xuecheng.content.model.DTO.CourseTeachPlanTreeDTO;

import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023/5/15 下午6:55
 * @Description:
 */
public interface TeachPlanService {
    /**
     * 通过课程id查询课程计划
     * @param courseId 课程id
     * @return 指定课程的课程计划
     */
    List<CourseTeachPlanTreeDTO> queryTeachPlanTree(Long courseId);
}
