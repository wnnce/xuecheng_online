package com.zeroxn.xuecheng.search.controller;

import com.zeroxn.xuecheng.base.exception.CustomException;
import com.zeroxn.xuecheng.search.entity.CourseIndex;
import com.zeroxn.xuecheng.search.service.CourseIndexService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lisang
 * @DateTime: 2023-09-19 20:38:29
 * @Description: 课程索引接口
 */
@Tag(name = "课程索引接口")
@RestController
@RequestMapping("/index")
public class CourseIndexController {
    private final CourseIndexService indexService;
    public CourseIndexController(CourseIndexService indexService) {
        this.indexService = indexService;
    }
    @Operation(description = "新增课程索引接口")
    @PostMapping("/course")
    public boolean addIndex(@RequestBody CourseIndex courseIndex) {
        boolean result = indexService.addCourseIndex(courseIndex);
        if (!result) {
            throw new CustomException("添加课程索引失败");
        }
        return true;
    }
}
