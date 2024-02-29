package com.zeroxn.xuecheng.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeroxn.xuecheng.content.model.pojo.CoursePublishTask;

import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023-09-18 20:24:38
 * @Description:
 */
public interface CoursePublishTaskService extends IService<CoursePublishTask> {
    void addPublishTask(Long courseId);
    void handlePublishTask(Long courseId, int task1Status, int task2Status, int task3Status);
    void updateTask1Status(Long courseId, int status);
    void updateTask2Status(Long courseId, int status);
    void updateTask3Status(Long courseId, int status);
    void deletePublishTask(Long courseId);
}
