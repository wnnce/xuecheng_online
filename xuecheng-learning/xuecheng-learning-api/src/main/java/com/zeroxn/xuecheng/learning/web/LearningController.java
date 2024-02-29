package com.zeroxn.xuecheng.learning.web;

import com.zeroxn.xuecheng.learning.model.dto.RestResponse;
import com.zeroxn.xuecheng.learning.service.LearningService;
import com.zeroxn.xuecheng.learning.utils.SecurityUtils;
import com.zeroxn.xuecheng.learning.utils.User;
import io.swagger.v3.oas.annotations.Operation;
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
    private final LearningService learningService;

    public LearningController(LearningService learningService) {
        this.learningService = learningService;
    }
    @Operation(summary = "获取视频")
    @GetMapping("/open/learn/getvideo/{courseId}/{teachplanId}/{mediaId}")
    public RestResponse<String> getVideo(@PathVariable("courseId") Long courseId, @PathVariable("teachplanId") Long teachplanId,
                                 @PathVariable("mediaId") String mediaId) {
        User user = SecurityUtils.getUser();
        if (user == null) {
            return RestResponse.fail("用户未登录", null);
        }
        return learningService.getVide(user.getId(), courseId, teachplanId, mediaId);
    }
}
