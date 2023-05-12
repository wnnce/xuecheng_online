package com.zeroxn.xuecheng.content.web;

import com.zeroxn.xuecheng.content.model.DTO.CourseCategoryTreeDTO;
import com.zeroxn.xuecheng.content.service.CourseCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023/5/11 下午9:28
 * @Description: 课程分类控制器
 */
@Tag(name = "课程分类管理接口")
@RestController
@RequestMapping("/course-category")
public class CourseCategoryController {
    private final CourseCategoryService courseCategoryService;
    public CourseCategoryController(CourseCategoryService courseCategoryService){
        this.courseCategoryService = courseCategoryService;
    }
    @GetMapping("/tree-nodes")
    @Operation(summary = "获取课程分类信息的树形结构数据")
    public List<CourseCategoryTreeDTO> getCourseCategoryTree(){
        return courseCategoryService.queryCourseCategoryTree("1");
    }
}
