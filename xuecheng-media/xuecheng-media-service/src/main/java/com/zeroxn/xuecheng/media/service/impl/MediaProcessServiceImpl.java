package com.zeroxn.xuecheng.media.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeroxn.xuecheng.media.mapper.MediaProcessMapper;
import com.zeroxn.xuecheng.media.model.pojo.MediaProcess;
import com.zeroxn.xuecheng.media.service.MediaProcessService;
import org.springframework.stereotype.Service;

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
    public MediaProcessServiceImpl(MediaProcessMapper mediaProcessMapper){
        this.mediaProcessMapper = mediaProcessMapper;
    }

    @Override
    public List<MediaProcess> queryShardProcess(int shard, int total) {
        return mediaProcessMapper.queryShardProcess(shard, total);
    }

    @Override
    public boolean startTask(Long id) {
        int result = mediaProcessMapper.startTask(id);
        return !(result <= 0);
    }
}
