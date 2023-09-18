package com.zeroxn.xuecheng.content.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zeroxn.xuecheng.content.client.MediaClient;
import com.zeroxn.xuecheng.content.config.FileToMultipartFile;
import com.zeroxn.xuecheng.content.model.DTO.CoursePreviewDTO;
import com.zeroxn.xuecheng.content.model.pojo.CourseBase;
import com.zeroxn.xuecheng.content.service.CourseBaseService;
import com.zeroxn.xuecheng.content.service.CoursePreviewService;
import com.zeroxn.xuecheng.content.service.CoursePublishService;
import com.zeroxn.xuecheng.content.service.CoursePublishTaskService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: lisang
 * @DateTime: 2023/6/20 下午9:12
 * @Description: 处理Kafka任务
 */
@Component
public class CourseKafkaHandler {
    private static final Logger logger = LoggerFactory.getLogger(CourseKafkaHandler.class);
    private final CourseBaseService baseService;
    private final CoursePublishService publishService;
    private final CoursePublishTaskService publishTaskService;
    private final ExecutorService taskService = Executors.newFixedThreadPool(3);
    public CourseKafkaHandler(CourseBaseService baseService, CoursePublishService publishService,
                              CoursePublishTaskService publishTaskService) {
        this.baseService = baseService;
        this.publishService = publishService;
        this.publishTaskService = publishTaskService;
    }

    /**
     * kafka消费者 监听课程发布topic 启动必须要groupID
     * @param courseId 课程ID
     */
    @KafkaListener(topics = "xuecheng")
    public void handlerKafkaListener(Long courseId){
        logger.info("监听到Kafka消息，课程ID：{}", courseId);
        long count = baseService.count(new LambdaQueryWrapper<CourseBase>().eq(CourseBase::getId, courseId));
        if (count <= 0){
            logger.info("该课程ID对应的课程不存在，结束运行");
            return;
        }
        // 使用异步任务同时完成三个线程
        publishTaskService.addPublishTask(courseId);
        CompletableFuture<Boolean> task1 = saveCourseIndex(courseId);
        CompletableFuture<Boolean> task2 = saveCourseCache(courseId);
        CompletableFuture<Boolean> task3 = generateHtml(courseId);
        // 等待所有线程执行完毕
        CompletableFuture.allOf(task1, task2, task3);
        if(task1.join() && task2.join() && task3.join()){
            // TODO: 所有操作都成功后执行的后置操作
            publishTaskService.deletePublishTask(courseId);
        }


    }

    private CompletableFuture<Boolean> saveCourseIndex(Long courseId){
        // TODO:将课程索引保存到Elasticsearch 返回保存结果 如果保存失败则往 xxx表中添加数据
        return CompletableFuture.supplyAsync(() -> {
            publishTaskService.updateTask1Status(courseId, 1);
            return true;
        }, taskService);
    }
    private CompletableFuture<Boolean> saveCourseCache(Long courseId){
        // TODO:将课程信息缓存到Redis 返回保存结果
        return CompletableFuture.supplyAsync(() -> {
            publishTaskService.updateTask2Status(courseId, 1);
            return true;
        }, taskService);
    }
    private CompletableFuture<Boolean> generateHtml(Long courseId) {
        return CompletableFuture.supplyAsync(() -> {
            MultipartFile multipartFile = publishService.generateHtml(courseId);
            if (multipartFile == null){
                return false;
            }
            boolean result = publishService.uploadHtmlToMinio(courseId, multipartFile);
            if (result) {
                publishTaskService.updateTask3Status(courseId, 1);
            }
            return result;
        }, taskService);
    }
}
