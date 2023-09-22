package com.zeroxn.xuecheng.content.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: lisang
 * @DateTime: 2023/6/19 下午3:07
 * @Description:
 */
public interface CoursePublishService {
    void commitAudit(Long companyId, Long courseId) throws JsonProcessingException;
    void coursePublish(Long companyId, Long courseId);
    MultipartFile generateHtml(Long courseId);
    boolean uploadHtmlToMinio(Long courseId, MultipartFile htmlFile);
    boolean saveCourseIndex(Long courseId);
}
