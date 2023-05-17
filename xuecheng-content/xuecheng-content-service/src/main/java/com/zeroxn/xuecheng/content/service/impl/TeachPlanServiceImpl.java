package com.zeroxn.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zeroxn.xuecheng.base.enums.CommonError;
import com.zeroxn.xuecheng.base.exception.CustomException;
import com.zeroxn.xuecheng.base.exception.ParamException;
import com.zeroxn.xuecheng.content.mapper.TeachPlanMapper;
import com.zeroxn.xuecheng.content.model.DTO.SaveTeachPlanDTO;
import com.zeroxn.xuecheng.content.model.DTO.TeachPlanTreeDTO;
import com.zeroxn.xuecheng.content.model.pojo.Teachplan;
import com.zeroxn.xuecheng.content.service.TeachPlanService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023/5/15 下午6:57
 * @Description:
 */
@Service
public class TeachPlanServiceImpl implements TeachPlanService {
    private final TeachPlanMapper teachPlanMapper;
    public TeachPlanServiceImpl(TeachPlanMapper teachPlanMapper){
        this.teachPlanMapper = teachPlanMapper;
    }
    @Override
    public List<TeachPlanTreeDTO> queryTeachPlanTree(Long courseId) {
        return teachPlanMapper.getTeachPlanTree(courseId);
    }

    @Override
    @Transactional
    public void saveTeachPlan(SaveTeachPlanDTO teachPlanDTO) {
        if (teachPlanDTO.getId() == null){
            Teachplan teachplan = new Teachplan();
            BeanUtils.copyProperties(teachPlanDTO, teachplan);
            teachplan.setCreateDate(LocalDateTime.now());
            int count = queryTeachPlanCountByParentIdAndCourseId(teachPlanDTO.getParentid(), teachPlanDTO.getCourseId());
            teachplan.setOrderby(count + 1);
            teachPlanMapper.insert(teachplan);
        }else {
            Teachplan teachplan = new Teachplan();
            BeanUtils.copyProperties(teachPlanDTO, teachplan);
            teachplan.setChangeDate(LocalDateTime.now());
            teachPlanMapper.updateById(teachplan);
        }
    }

    @Override
    @Transactional
    public void deleteTeachPlanById(Long teachPlanId) {
        Teachplan teachplan = teachPlanMapper.selectById(teachPlanId);
        if(teachplan == null){
            throw new ParamException(CommonError.PARAM_ERROR.getMessage());
        }
        if(teachplan.getParentid() != 0){
            teachPlanMapper.deleteById(teachPlanId);
        }else {
            deleteTeachPlanParent(teachPlanId);
        }
    }

    /**
     * 上移/下移课程计划
     * @param teachPlanId 课程计划id
     * @param upOrDown 上移或者下移 true：上移 false:下移
     */
    @Override
    public void updateTeachPlanLocation(Long teachPlanId, boolean upOrDown) {
        Teachplan teachplan = teachPlanMapper.selectById(teachPlanId);
        if(teachplan == null){
            throw new ParamException(CommonError.PARAM_ERROR.getMessage());
        }
        if (upOrDown){
            if(teachplan.getOrderby() <= 1){
                throw new CustomException("该课程计划无法上移");
            }
            teachPlanMapper.updateTeachPlanOnUp(teachPlanId, teachplan.getParentid(), teachplan.getCourseId(),
                    teachplan.getOrderby());
        }else {
            int count = queryTeachPlanCountByParentIdAndCourseId(teachplan.getParentid(), teachplan.getCourseId());
            if(teachplan.getOrderby() >= count){
                throw new CustomException("该课程计划无法下移");
            }
            teachPlanMapper.updateTeachPlanOnDown(teachPlanId, teachplan.getParentid(), teachplan.getCourseId(),
                    teachplan.getOrderby());
        }
    }

    /**
     * 删除课程计划大章节 先删除大章节 再删除大章节对应的小节
     * @param teachPlanId 课程计划id
     */
    private void deleteTeachPlanParent(Long teachPlanId) {
        Long[] ids = teachPlanMapper.getTeachPlanIdsByParentId(teachPlanId);
        teachPlanMapper.deleteById(teachPlanId);
        if (ids != null && ids.length > 0){
            teachPlanMapper.deleteTeachPlanByIds(ids);
        }
    }

    private int queryTeachPlanCountByParentIdAndCourseId(Long parentId, Long courseId){
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getParentid, parentId)
                .eq(Teachplan::getCourseId, courseId);
        return teachPlanMapper.selectCount(queryWrapper).intValue();
    }
}
