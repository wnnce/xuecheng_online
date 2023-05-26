package com.zeroxn.xuecheng.media.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeroxn.xuecheng.base.model.PageParams;
import com.zeroxn.xuecheng.base.model.PageResult;
import com.zeroxn.xuecheng.media.model.DTO.QueryMediaParamsDTO;
import com.zeroxn.xuecheng.media.model.DTO.UploadFileArgsDTO;
import com.zeroxn.xuecheng.media.model.DTO.UploadFileResultDTO;
import com.zeroxn.xuecheng.media.model.pojo.MediaFiles;

import java.io.FileInputStream;
import java.util.List;

/**
 * <p>
 * 媒资信息 服务类
 * </p>
 *
 * @author lisang
 * @since 2023-05-24
 */
public interface MediaFilesService extends IService<MediaFiles> {
    PageResult<MediaFiles> listMediaFiles(Long companyId, PageParams pageParams, QueryMediaParamsDTO paramsDTO);
    UploadFileResultDTO uploadFile(Long companyId, UploadFileArgsDTO argsDTO, String tempFilePath);
}
