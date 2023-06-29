package com.zeroxn.xuecheng.content.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zeroxn.xuecheng.content.client.MediaClient;
import com.zeroxn.xuecheng.content.config.FileToMultipartFile;
import com.zeroxn.xuecheng.content.model.DTO.CoursePreviewDTO;
import com.zeroxn.xuecheng.content.model.pojo.CourseBase;
import com.zeroxn.xuecheng.content.service.CourseBaseService;
import com.zeroxn.xuecheng.content.service.CoursePreviewService;
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

/**
 * @Author: lisang
 * @DateTime: 2023/6/20 下午9:12
 * @Description: 处理Kafka任务
 */
@Component
public class CourseKafkaHandler {
    private static final Logger logger = LoggerFactory.getLogger(CourseKafkaHandler.class);
    private final CourseBaseService baseService;
    private final CoursePreviewService previewService;
    private final Configuration configuration;
    private final MediaClient mediaClient;
    public CourseKafkaHandler(CourseBaseService baseService, CoursePreviewService previewService, MediaClient mediaClient) throws IOException {
        this.baseService = baseService;
        this.previewService = previewService;
        this.mediaClient = mediaClient;
        configuration = new Configuration(Configuration.VERSION_2_3_31);
        String classpath = this.getClass().getResource("/").getPath();
        configuration.setDirectoryForTemplateLoading(new File(classpath + "/templates"));
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
        boolean result1 = saveCourseIndex(courseId);
        boolean result2 = saveCourseCache(courseId);
        boolean result3 = generateHtml(courseId);

        if(result1 || result2 || result3){
            // TODO: 所有操作都成功后执行的后置操作

        }


    }

    private boolean saveCourseIndex(Long courseId){
        // TODO:将课程索引保存到Elasticsearch 返回保存结果 如果保存失败则往 xxx表中添加数据

        return true;
    }
    private boolean saveCourseCache(Long courseId){
        // TODO:将课程信息缓存到Redis 返回保存结果

        return true;
    }
    private boolean generateHtml(Long courseId) {
        // TODO: 课程信息生成静态页面 保存到Minio
        CoursePreviewDTO coursePreview = previewService.queryCoursePreview(courseId);
        Writer writer = null;
        try{
            Template template = configuration.getTemplate("course_template.ftl");
            File htmlFile = new File("/home/lisang/Documents/html/" + courseId + ".html");
            writer = new FileWriter(htmlFile.getAbsolutePath());
            template.process(new HashMap<String, Object>(Map.of("course", coursePreview)), writer);
            writer.write(writer.toString());
            MultipartFile multipartFile = new FileToMultipartFile(htmlFile);
            String objectName = "course/" + courseId + ".html";
            String result = mediaClient.uploadFile(multipartFile, objectName);
            if (!StringUtils.isEmpty(result)){
                logger.info("静态网页上传成功！，响应参数：{}", result);
                return true;
            }
        }catch (IOException | TemplateException e){
            logger.error("生成HTML报错，错误消息：{}", e.getMessage());
        }finally {
            try{
                if(writer != null){
                    writer.close();
                }
            }catch (IOException ex){
                logger.warn("流关闭失败！");
            }
        }
        return false;
    }
}
