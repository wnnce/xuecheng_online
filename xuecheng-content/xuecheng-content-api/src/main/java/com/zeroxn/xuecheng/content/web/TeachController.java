package com.zeroxn.xuecheng.content.web;

import com.zeroxn.xuecheng.content.model.DTO.CourseTeachPlanTreeDTO;
import com.zeroxn.xuecheng.content.service.TeachPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023/5/15 下午5:17
 * @Description: 课程章节信息控制器
 */
@Tag(name = "课程计划信息管理接口")
@RestController
@RequestMapping("/teachplan")
public class TeachController {
    private final TeachPlanService teachPlanService;
    public TeachController(TeachPlanService teachPlanService){
        this.teachPlanService = teachPlanService;
    }

    /**
     * 通过课程id查询课程计划
     * @param courseId 课程id
     * @return 返回课程计划
     */
    @GetMapping("/{id}/tree-nodes")
    @Operation(summary = "获取课程计划信息的树形结构数据")
    @Parameter(name = "id", description = "课程id", required = true)
    public List<CourseTeachPlanTreeDTO> getCourseTeachPlanTree(@PathVariable("id") Long courseId){
        return teachPlanService.queryTeachPlanTree(courseId);
    }
}
