package com.zeroxn.xuecheng.learning.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lisang
 * @DateTime: 2023-10-24 20:29:54
 * @Description:
 */
@RestController
@Tag(name = "学习过程管理接口")
public class LearningController {
    @GetMapping("/open/learn/getvideo/{courseId}/{teachplanId}/{mediaId}")
    public String getVideo(@PathVariable("courseId") Long courseId, @PathVariable("teachplanId") Long teachplanId,
                           @PathVariable("mediaId") String mediaId) {
        return null;
    }
}
