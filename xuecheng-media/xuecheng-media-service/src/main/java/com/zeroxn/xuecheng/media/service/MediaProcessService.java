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
    List<MediaProcess> queryShardProcess(int shard, int total, int limit);
    boolean startTask(Long id);
    void saveMediaProcessTask(Long taskId, String fileId, String url, String status, String errMsg);
}
