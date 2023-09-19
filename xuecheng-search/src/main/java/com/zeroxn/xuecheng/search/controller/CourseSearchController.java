package com.zeroxn.xuecheng.search.controller;

import com.zeroxn.xuecheng.base.model.PageParams;
import com.zeroxn.xuecheng.search.dto.SearchCourseParamDto;
import com.zeroxn.xuecheng.search.dto.SearchPageResultDto;
import com.zeroxn.xuecheng.search.entity.CourseIndex;
import com.zeroxn.xuecheng.search.service.CourseSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lisang
 * @DateTime: 2023-09-19 20:42:14
 * @Description: 课程搜索接口
 */
@Tag(name = "课程搜索接口")
@RestController
@RequestMapping("/course")
public class CourseSearchController {
    private final CourseSearchService searchService;
    public CourseSearchController(CourseSearchService searchService) {
        this.searchService = searchService;
    }
    @Operation(description = "搜索课程接口")
    @GetMapping("/list")
    public SearchPageResultDto<CourseIndex> searchCourse(PageParams params, SearchCourseParamDto searchDto) {

    }
}
