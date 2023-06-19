package com.zeroxn.xuecheng.content.service;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @Author: lisang
 * @DateTime: 2023/6/19 下午3:07
 * @Description:
 */
public interface CoursePublishService {
    void commitAudit(Long companyId, Long courseId) throws JsonProcessingException;
}
