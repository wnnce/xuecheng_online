package com.zeroxn.xuecheng.learning.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeroxn.xuecheng.learning.client.ContentClient;
import com.zeroxn.xuecheng.learning.client.MediaClient;
import com.zeroxn.xuecheng.learning.model.dto.CourseTablesDto;
import com.zeroxn.xuecheng.learning.model.dto.RestResponse;
import com.zeroxn.xuecheng.learning.model.entity.CoursePublish;
import com.zeroxn.xuecheng.learning.model.entity.Teachplan;
import com.zeroxn.xuecheng.learning.service.CourseTableService;
import com.zeroxn.xuecheng.learning.service.LearningService;
import org.springframework.stereotype.Service;

/**
 * @Author: lisang
 * @DateTime: 2024-02-29 20:25:44
 * @Description:
 */
@Service
public class LearningServiceImpl implements LearningService {
    private final CourseTableService tableService;
    private final MediaClient mediaClient;
    private final ContentClient contentClient;
    private final ObjectMapper objectMapper;

    public LearningServiceImpl(CourseTableService tableService, MediaClient mediaClient, ContentClient contentClient, ObjectMapper objectMapper) {
        this.tableService = tableService;
        this.mediaClient = mediaClient;
        this.contentClient = contentClient;
        this.objectMapper = objectMapper;
    }
    @Override
    public RestResponse<String> getVide(String userId, Long courseId, Long teachplanId, String mediaId) {
        CoursePublish coursePublish = contentClient.queryCoursePublish(courseId);
        if (coursePublish == null) {
            return RestResponse.fail("课程不存在", null);
        }
        if (userId == null || userId.isEmpty()) {
            if ("201000".equals(coursePublish.getCharge())) {
                return mediaClient.getVideoUrlById(mediaId);
            }
        }

        String teachplanStr = coursePublish.getTeachplan();
        try {
            Teachplan teachplan = objectMapper.readValue(teachplanStr, Teachplan.class);
            if ("1".equals(teachplan.getIsPreview())) {
                return mediaClient.getVideoUrlById(mediaId);
            }
        } catch (JsonProcessingException ex) {

        }
        CourseTablesDto tablesDto = tableService.queryLearningStatus(userId, courseId);
        String learnStatus = tablesDto.getLearnStatus();
        if ("702002".equals(learnStatus)) {
            return RestResponse.fail("没有选课或选课后没有支付", null);
        } else if ("702003".equals(learnStatus)) {
            return RestResponse.fail("已过期需要申请续期或重新支付", null);
        } else {
            return mediaClient.getVideoUrlById(mediaId);
        }
    }
}
