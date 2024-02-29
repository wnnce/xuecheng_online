package com.zeroxn.xuecheng.content.handler;

import com.xxl.job.core.handler.annotation.XxlJob;
import com.zeroxn.xuecheng.content.model.pojo.CoursePublishTask;
import com.zeroxn.xuecheng.content.service.CoursePublishService;
import com.zeroxn.xuecheng.content.service.CoursePublishTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2024-02-29 18:37:44
 * @Description:
 */
@Component
public class CoursePublishHandler {
    private final Logger logger = LoggerFactory.getLogger(CoursePublishHandler.class);

    private final CoursePublishTaskService taskService;
    private final CoursePublishService publishService;

    public CoursePublishHandler(CoursePublishTaskService taskService, CoursePublishService publishService) {
        this.taskService = taskService;
        this.publishService = publishService;
    }

    /**
     * 课程发布失败重试任务
     * 查询所有课程发布失败的任务，通过字段确认是哪个具体任务执行失败然后重试
     * 如果重试成功那么删除任务，失败就更新执行时间和重试次数
     */
    @XxlJob("coursePublishTask")
    public void coursePublishTask() {
        List<CoursePublishTask> taskList = taskService.list();
        if (taskList == null || taskList.isEmpty()) {
            return;
        }
        for (CoursePublishTask task : taskList) {
            int task1 = 1, task2 = 1, task3 = 1;
            if (task.getTask1Status() == 0) {
                logger.info("courseId {} 保存Elasticsearch索引失败，开始重试", task.getId());
                boolean result = publishService.saveCourseIndex(task.getId());
                if (!result) {
                    logger.error("保存elasticsearch索引失败，courseId:{}", task.getId());
                    task1 = 0;
                }
            }
            if (task.getTask2Status() == 0) {
                logger.info("courseId {} 保存Redis缓存失败，开始重试", task.getId());
                boolean result = publishService.saveCourseCache(task.getId());
                if (!result) {
                    logger.error("保存Redis缓存失败，courseId:{}", task.getId());
                    task2 = 0;
                }
            }
            if (task.getTask3Status() == 0) {
                logger.info("courseId {} 生成静态网页失败，开始重试", task.getId());
                MultipartFile multipartFile = publishService.generateHtml(task.getId());
                if (multipartFile == null) {
                    task3 = 0;
                }
                boolean result = publishService.uploadHtmlToMinio(task.getId(), multipartFile);
                if (!result) {
                    task3 = 0;
                }
            }
            if (task1 == 1 && task2 == 1 && task3 == 1) {
                taskService.deletePublishTask(task.getId());
            } else {
                taskService.handlePublishTask(task.getId(), task1, task2, task3);
            }
        }
    }
}
