package com.zeroxn.xuecheng.content.service;

import com.zeroxn.xuecheng.content.model.DTO.SaveTeachPlanDTO;
import com.zeroxn.xuecheng.content.model.DTO.TeachPlanTreeDTO;

import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023/5/15 下午6:55
 * @Description: 课程计划管理
 */
public interface TeachPlanService {
    /**
     * 通过课程id查询课程计划
     * @param courseId 课程id
     * @return 指定课程的课程计划
     */
    List<TeachPlanTreeDTO> queryTeachPlanTree(Long courseId);

    /**
     * 添加/修改课程计划
     * @param teachPlanDTO 课程计划数据封装类
     */
    void saveTeachPlan(SaveTeachPlanDTO teachPlanDTO);

    /**
     * 通过课程计划id删除课程计划，如果是小节课程那么直接删除即可，如果是大章节则需要将课程小节一起删除
     * @param teachPlanId 课程计划id
     */
    void deleteTeachPlanById(Long teachPlanId);

    /**
     * 更新课程章节或者小节的位置
     * @param teachPlanId 课程计划id
     */
    void updateTeachPlanLocation(Long teachPlanId, boolean upOrDown);
}
