package com.zeroxn.xuecheng.content.service.impl;

import com.zeroxn.xuecheng.content.mapper.TeachPlanMapper;
import com.zeroxn.xuecheng.content.model.DTO.CourseTeachPlanTreeDTO;
import com.zeroxn.xuecheng.content.service.TeachPlanService;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Service;

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
    public List<CourseTeachPlanTreeDTO> queryTeachPlanTree(Long courseId) {
        return teachPlanMapper.getTeachPlanTree(courseId);
    }
}
