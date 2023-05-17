package com.zeroxn.xuecheng.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zeroxn.xuecheng.content.model.DTO.TeachPlanTreeDTO;
import com.zeroxn.xuecheng.content.model.pojo.Teachplan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    List<TeachPlanTreeDTO> getTeachPlanTree(@Param("courseId") Long courseId);

    /**
     * 通过课程计划id删除课程 （逻辑删除）
     * @param teachPlanId 课程计划id
     */
    void deleteTeachPlanById(@Param("teachPlanId") Long teachPlanId);

    /**
     * 通过课程计划id列表删除多个课程计划
     * @param teachPlanIds 课程计划id列表
     */
    void deleteTeachPlanByIds(@Param("teachPlanIds") Long ...teachPlanIds);

    /**
     * 通过上级节点id获取子节点id列表
     * @param parentId 上级节点id
     * @return 子节点id列表
     */
    Long[] getTeachPlanIdsByParentId(@Param("parentId") Long parentId);

    /**
     * 更新课程计划的排序位置 向上排序 先通过父节点id、课程id和排序值更新原节点的位置 再通过课程计划id更新现节点的位置
     * @param teachPlanId 课程计划id
     * @param parentId 父节点id
     * @param courseId 课程id
     * @param orderBy 当前节点的排序值
     */
    void updateTeachPlanOnUp(@Param("teachPlanId") Long teachPlanId,@Param("parentId") Long parentId,
                             @Param("courseId") Long courseId, @Param("orderBy") Integer orderBy);

    /**
     * 更新课程计划的排序位置 向下排序 先通过父节点id、课程id和排序值更新原节点的位置 再通过课程计划id更新现节点的位置
     * @param teachPlanId 课程计划id
     * @param parentId 父节点id
     * @param courseId 课程id
     * @param orderBy 当前节点的排序值
     */
    void updateTeachPlanOnDown(@Param("teachPlanId") Long teachPlanId,@Param("parentId") Long parentId,
                               @Param("courseId") Long courseId, @Param("orderBy") Integer orderBy);
}
