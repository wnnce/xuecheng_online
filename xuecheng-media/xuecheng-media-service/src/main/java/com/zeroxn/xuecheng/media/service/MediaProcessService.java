package com.zeroxn.xuecheng.media.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeroxn.xuecheng.media.model.pojo.MediaProcess;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lisang
 * @since 2023-05-24
 */
public interface MediaProcessService extends IService<MediaProcess> {
    /**
     * 通过任务执行器的分片和总数以及处理器核心数量获取待处理的视频处理任务
     * @param shard 任务执行器分片
     * @param total 任务执行器总数
     * @param limit 处理器核心数量
     * @return 待执行的视频处理任务列表或空
     */
    List<MediaProcess> queryShardProcess(int shard, int total, int limit);

    /**
     * 开始任务 拿到数据库乐观锁
     * @param id 视频处理任务ID
     * @return true/false
     */
    boolean startTask(Long id);

    /**
     * 保存处理视频任务的结果
     * @param taskId 任务ID
     * @param fileId 文件ID
     * @param url 文件转码成功后的URL
     * @param status 任务执行状态
     * @param errMsg 执行任务错误时的错误消息
     */
    void saveMediaProcessTask(Long taskId, String fileId, String url, String status, String errMsg);
}
