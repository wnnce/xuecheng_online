package com.zeroxn.xuecheng.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeroxn.xuecheng.media.mapper.MediaProcessMapper;
import com.zeroxn.xuecheng.media.model.pojo.MediaFiles;
import com.zeroxn.xuecheng.media.model.pojo.MediaProcess;
import com.zeroxn.xuecheng.media.model.pojo.MediaProcessHistory;
import com.zeroxn.xuecheng.media.service.MediaFilesService;
import com.zeroxn.xuecheng.media.service.MediaProcessHistoryService;
import com.zeroxn.xuecheng.media.service.MediaProcessService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lisang
 * @since 2023-05-24
 */
@Service
public class MediaProcessServiceImpl extends ServiceImpl<MediaProcessMapper, MediaProcess> implements MediaProcessService {
    private final MediaProcessMapper mediaProcessMapper;
    private final MediaFilesService mediaFilesService;
    private final MediaProcessHistoryService mediaProcessHistoryService;
    public MediaProcessServiceImpl(MediaProcessMapper mediaProcessMapper, MediaFilesService mediaFilesService,
                                   MediaProcessHistoryService mediaProcessHistoryService){
        this.mediaProcessMapper = mediaProcessMapper;
        this.mediaFilesService = mediaFilesService;
        this.mediaProcessHistoryService = mediaProcessHistoryService;
    }

    @Override
    public List<MediaProcess> queryShardProcess(int shard, int total, int limit) {
        return mediaProcessMapper.queryShardProcess(shard, total, limit);
    }

    @Override
    public boolean startTask(Long id) {
        int result = mediaProcessMapper.startTask(id);
        return result > 0;
    }

    @Override
    @Transactional
    public void saveMediaProcessTask(Long taskId, String fileId, String url, String status, String errMsg) {
        MediaProcess mediaProcess = mediaProcessMapper.selectById(taskId);
        if(mediaProcess == null){
            return;
        }
        // 视频转码失败处理
        if("3".equals(status)){
            int failCount = mediaProcess.getFailCount() + 1;
            LambdaUpdateWrapper<MediaProcess> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(MediaProcess::getFailCount, failCount)
                    .set(MediaProcess::getFinishDate, LocalDateTime.now())
                    .set(MediaProcess::getStatus, status)
                    .set(MediaProcess::getErrormsg, errMsg)
                    .eq(MediaProcess::getId, taskId);
            this.update(updateWrapper);
            return;
        }
        // 视频转码成功处理
        if("2".equals(status)){
            // 更新文件链接
            LambdaUpdateWrapper<MediaFiles> filesUpdate = new LambdaUpdateWrapper<>();
            filesUpdate.set(MediaFiles::getUrl, url).eq(MediaFiles::getId, fileId);
            mediaFilesService.update(filesUpdate);
            // 保存到视频任务历史表
            mediaProcess.setFinishDate(LocalDateTime.now());
            mediaProcess.setStatus(status);
            mediaProcess.setUrl(url);
            MediaProcessHistory mediaProcessHistory = new MediaProcessHistory();
            BeanUtils.copyProperties(mediaProcess, mediaProcessHistory);
            mediaProcessHistory.setId(null);
            mediaProcessHistoryService.save(mediaProcessHistory);
            // 删除视频任务表中当前记录
            mediaProcessMapper.deleteById(taskId);
        }
    }
}
