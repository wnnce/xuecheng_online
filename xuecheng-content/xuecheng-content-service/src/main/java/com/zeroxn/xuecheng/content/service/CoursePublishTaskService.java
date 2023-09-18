package com.zeroxn.xuecheng.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeroxn.xuecheng.content.model.pojo.CoursePublishTask;

/**
 * @Author: lisang
 * @DateTime: 2023-09-18 20:24:38
 * @Description:
 */
public interface CoursePublishTaskService extends IService<CoursePublishTask> {
    void addPublishTask(Long courseId);
    void handlePublishTask(Long courseId);
    void updateTask1Status(Long courseId, Integer status);
    void updateTask2Status(Long courseId, Integer status);
    void updateTask3Status(Long courseId, Integer status);
    void deletePublishTask(Long courseId);
}
