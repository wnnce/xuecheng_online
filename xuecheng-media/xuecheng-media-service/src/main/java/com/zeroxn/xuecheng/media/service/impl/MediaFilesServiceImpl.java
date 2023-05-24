package com.zeroxn.xuecheng.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeroxn.xuecheng.base.model.PageParams;
import com.zeroxn.xuecheng.base.model.PageResult;
import com.zeroxn.xuecheng.media.mapper.MediaFilesMapper;
import com.zeroxn.xuecheng.media.model.DTO.QueryMediaParamsDTO;
import com.zeroxn.xuecheng.media.model.pojo.MediaFiles;
import com.zeroxn.xuecheng.media.service.MediaFilesService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 媒资信息 服务实现类
 * </p>
 *
 * @author lisang
 * @since 2023-05-24
 */
@Service
public class MediaFilesServiceImpl extends ServiceImpl<MediaFilesMapper, MediaFiles> implements MediaFilesService{

    @Override
    public PageResult<MediaFiles> listMediaFiles(Long companyId, PageParams pageParams, QueryMediaParamsDTO paramsDTO) {
        LambdaQueryWrapper<MediaFiles> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MediaFiles::getCompanyId, companyId)
                .eq(StringUtils.isNotEmpty(paramsDTO.getFileName()), MediaFiles::getAuditStatus, paramsDTO.getAuditStatus())
                .eq(StringUtils.isNotEmpty(paramsDTO.getFileType()), MediaFiles::getFileType, paramsDTO.getFileType())
                .like(StringUtils.isNotEmpty(paramsDTO.getFileType()), MediaFiles::getFilename, paramsDTO.getFileName());
        Page<MediaFiles> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        Page<MediaFiles> pageResult = this.page(page, queryWrapper);
        return new PageResult<>(pageResult.getRecords(), pageResult.getTotal(), pageResult.getCurrent(),
                pageResult.getSize());
    }
}
