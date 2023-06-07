package com.zeroxn.xuecheng.media.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zeroxn.xuecheng.media.model.pojo.MediaProcess;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lisang
 * @since 2023-05-24
 */
@Mapper
public interface MediaProcessMapper extends BaseMapper<MediaProcess> {
    List<MediaProcess> queryShardProcess(@Param("shard") int shard, @Param("total") int total);

    int startTask(@Param("id") Long id);
}
