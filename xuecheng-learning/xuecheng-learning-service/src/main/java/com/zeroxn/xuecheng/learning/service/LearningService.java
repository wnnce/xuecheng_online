package com.zeroxn.xuecheng.learning.service;

import com.zeroxn.xuecheng.learning.model.dto.RestResponse;

/**
 * @Author: lisang
 * @DateTime: 2024-02-29 20:10:03
 * @Description:
 */
public interface LearningService {
    RestResponse<String> getVide(String userId, Long courseId, Long teachplanId, String mediaId);
}
