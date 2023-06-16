package com.zeroxn.xuecheng.content.service;

import com.zeroxn.xuecheng.content.model.DTO.CoursePreviewDTO;

/**
 * @Author: lisang
 * @DateTime: 2023/6/14 下午7:16
 * @Description:
 */
public interface CoursePreviewService {
    CoursePreviewDTO queryCoursePreview(Long courseId);
}
