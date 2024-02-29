package com.zeroxn.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeroxn.xuecheng.content.mapper.CoursePublishTaskMapper;
import com.zeroxn.xuecheng.content.model.pojo.CoursePublishTask;
import com.zeroxn.xuecheng.content.service.CoursePublishTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023-09-18 20:25:00
 * @Description:
 */
@Service
public class CoursePublishTaskServiceImpl extends ServiceImpl<CoursePublishTaskMapper, CoursePublishTask> implements CoursePublishTaskService {
    private static final Logger logger = LoggerFactory.getLogger(CoursePublishTaskServiceImpl.class);
    private final CoursePublishTaskMapper publishTaskMapper;
    public CoursePublishTaskServiceImpl(CoursePublishTaskMapper publishTaskMapper) {
        this.publishTaskMapper = publishTaskMapper;
    }
    @Override
    public void addPublishTask(Long courseId) {
        publishTaskMapper.insert(new CoursePublishTask(courseId, LocalDateTime.now()));
    }

    @Override
    public void handlePublishTask(Long courseId, int task1Status, int task2Status, int task3Status) {
        CoursePublishTask task = publishTaskMapper.selectById(courseId);
        if (task == null) {
            logger.error("该课程发布任务不存在,课程ID:{}", courseId);
            return;
        }
        this.update(new LambdaUpdateWrapper<CoursePublishTask>().set(CoursePublishTask::getExecuteTime, LocalDateTime.now())
                .set(CoursePublishTask::getExecuteCount, task.getExecuteCount() + 1)
                .set(CoursePublishTask::getTask1Status, task1Status)
                .set(CoursePublishTask::getTask2Status, task2Status)
                .set(CoursePublishTask::getTask3Status, task3Status)
                .eq(CoursePublishTask::getId, courseId));
    }

    @Override
    public void updateTask1Status(Long courseId, int status) {
        this.update(new LambdaUpdateWrapper<CoursePublishTask>().set(CoursePublishTask::getTask1Status, status)
                .eq(CoursePublishTask::getId, courseId));
    }

    @Override
    public void updateTask2Status(Long courseId, int status) {
        this.update(new LambdaUpdateWrapper<CoursePublishTask>().set(CoursePublishTask::getTask2Status, status)
                .eq(CoursePublishTask::getId, courseId));
    }

    @Override
    public void updateTask3Status(Long courseId, int status) {
        this.update(new LambdaUpdateWrapper<CoursePublishTask>().set(CoursePublishTask::getTask3Status, status)
                .eq(CoursePublishTask::getId, courseId));
    }

    @Override
    public void deletePublishTask(Long courseId) {
        publishTaskMapper.deleteById(courseId);
    }

}
