package com.zeroxn.xuecheng.media.service.jobhandler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.zeroxn.xuecheng.media.model.pojo.MediaProcess;
import com.zeroxn.xuecheng.media.service.MediaProcessService;
import com.zeroxn.xuecheng.media.utils.MinioUtils;
import com.zeroxn.xuecheng.media.utils.Mp4VideoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: lisang
 * @DateTime: 2023/6/13 下午3:34
 * @Description: 视频处理任务执行器
 */
@Component
@Slf4j
public class VideoTask {
    private final MediaProcessService mediaProcessService;
    private final MinioUtils minioUtils;
    /**
     * ffmpeg文件路径 通过nacos下发配置
     */
    @Value("${video-process.ffmpeg-path}")
    public String ffmpegPath;

    public VideoTask(MediaProcessService mediaProcessService, MinioUtils minioUtils){
        this.mediaProcessService = mediaProcessService;
        this.minioUtils = minioUtils;
    }
    @XxlJob("videoHandler")
    public void videoToMp4Task() throws InterruptedException {
        // 获取执行器总数 分片和处理器核心数量
        int total = XxlJobHelper.getShardTotal();
        int index = XxlJobHelper.getShardIndex();
        int processors = Runtime.getRuntime().availableProcessors();
        List<MediaProcess> mediaProcessList = mediaProcessService.queryShardProcess(index, total, processors);
        int size = mediaProcessList.size();
        // 判断待处理任务列表是不是为空
        if(size == 0){
            log.info("待处理的视频任务为空");
            return;
        }
        // 开启一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(size);
        // 计时器 用于阻塞线程 否则任务管理中心会一直下发任务
        CountDownLatch countDownLatch = new CountDownLatch(size);
        mediaProcessList.forEach(mediaProcess -> {
            // 调用线程池中的线程执行
            executorService.execute(() -> {
                try{
                    Long taskId = mediaProcess.getId();
                    boolean result = mediaProcessService.startTask(taskId);
                    if(!result){
                        log.info("抢占任务失败，任务ID：{}", taskId);
                        return;
                    }
                    String bucket = mediaProcess.getBucket();
                    String filePath = mediaProcess.getFilePath();
                    String fileId = mediaProcess.getFileId();
                    // 下载需要转码的原文件
                    File tempFile = minioUtils.cloneFile(bucket, filePath);
                    if(tempFile == null){
                        log.info("从Minio下载文件失败，bucket：{}， objectName：{}", bucket, filePath);
                        mediaProcessService.saveMediaProcessTask(taskId, fileId, null, "3", "从Minio下载文件失败");
                        return;
                    }
                    File toFile = null;
                    try {
                        toFile = File.createTempFile("minioTo", ".mp4");
                    } catch (IOException e) {
                        log.error("创建临时文件失败");
                        mediaProcessService.saveMediaProcessTask(taskId, fileId, null, "3", "创建临时文件失败");
                        return;
                    }
                    // 视频转码
                    String fileName = fileId + ".mp4";
                    Mp4VideoUtil mp4VideoUtil = new Mp4VideoUtil(ffmpegPath, tempFile.getAbsolutePath(), fileName, toFile.getAbsolutePath());
                    String result1 = mp4VideoUtil.generateMp4();
                    if(!result1.equals("success")){
                        log.error("转换视频格式失败，错误消息：{}，文件ID：{}", result1, fileId);
                        mediaProcessService.saveMediaProcessTask(taskId, fileId, null, "3", "转换视频格式失败");
                        return;
                    }
                    // 将转码后的视频上传到Minio
                    String objectName = minioUtils.getMinioFilePath(fileId) + fileId + ".mp4";
                    boolean uploaded = minioUtils.uploadFile(bucket, toFile.getAbsolutePath(), objectName);
                    if(!uploaded){
                        log.error("Minio上传转换后的文件失败，BUCKET：{}，文件ID：{}", bucket, fileId);
                        mediaProcessService.saveMediaProcessTask(taskId, fileId, null, "3", "Minio上传转换后的文件失败");
                    }
                    String url = bucket + objectName;
                    mediaProcessService.saveMediaProcessTask(taskId, fileId, url, "2", null);
                }finally {
                    countDownLatch.countDown();
                }
            });
        });
        // 阻塞线程 等待所有进程全部执行完 最长阻塞时间30分钟
        countDownLatch.await(30, TimeUnit.MINUTES);
    }

    public String getFfmpegPath() {
        return ffmpegPath;
    }

    public void setFfmpegPath(String ffmpegPath) {
        this.ffmpegPath = ffmpegPath;
    }
}
