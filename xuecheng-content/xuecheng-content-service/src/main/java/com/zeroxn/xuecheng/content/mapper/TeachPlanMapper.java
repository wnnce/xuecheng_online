package com.zeroxn.xuecheng.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zeroxn.xuecheng.content.model.DTO.CourseTeachPlanTreeDTO;
import com.zeroxn.xuecheng.content.model.pojo.Teachplan;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023/5/15 下午6:08
 * @Description:
 */
@Mapper
public interface TeachPlanMapper extends BaseMapper<Teachplan> {
    /**
     * 查询课程计划的树形结构数据
     * @param courseId 课程id
     * @return 指定课程的课程计划
     */
    List<CourseTeachPlanTreeDTO> getTeachPlanTree(Long courseId);
}
