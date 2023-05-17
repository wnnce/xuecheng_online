package com.zeroxn.xuecheng.content.web;

import com.zeroxn.xuecheng.content.model.DTO.SaveTeachPlanDTO;
import com.zeroxn.xuecheng.content.model.DTO.TeachPlanTreeDTO;
import com.zeroxn.xuecheng.content.service.TeachPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023/5/15 下午5:17
 * @Description: 课程章节信息控制器
 */
@Tag(name = "课程计划信息管理接口")
@RestController
@RequestMapping("/teachplan")
public class TeachPlanController {
    private final TeachPlanService teachPlanService;
    public TeachPlanController(TeachPlanService teachPlanService){
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
    public List<TeachPlanTreeDTO> getCourseTeachPlanTree(@PathVariable("id") Long courseId){
        return teachPlanService.queryTeachPlanTree(courseId);
    }
    @PostMapping
    @Operation(summary = "修改课程计划接口")
    public void updateTeachPlan(@RequestBody @Validated SaveTeachPlanDTO teachPlanDTO){
        teachPlanService.saveTeachPlan(teachPlanDTO);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "删除课程计划")
    @Parameter(name = "id", description = "课程计划id", required = true)
    public void deleteTeachPlan(@PathVariable("id") Long teachPlanId){
        teachPlanService.deleteTeachPlanById(teachPlanId);
    }
    @PostMapping("/moveup/{id}")
    @Operation(summary = "上移课程小节")
    @Parameter(name = "id", description = "课程计划id", required = true)
    public void moveTeachPlanUp(@PathVariable("id") Long teachPlanId){
        teachPlanService.updateTeachPlanLocation(teachPlanId, true);
    }
    @PostMapping("/movedown/{id}")
    @Operation(summary = "下移课程小节")
    @Parameter(name = "id", description = "课程计划id", required = true)
    public void moveTeachPlanDown(@PathVariable("id") Long teachPlanId){
        teachPlanService.updateTeachPlanLocation(teachPlanId, false);
    }
}
