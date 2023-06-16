package com.zeroxn.xuecheng.media.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeroxn.xuecheng.base.model.PageParams;
import com.zeroxn.xuecheng.base.model.PageResult;
import com.zeroxn.xuecheng.media.model.DTO.QueryMediaParamsDTO;
import com.zeroxn.xuecheng.media.model.DTO.RestResponse;
import com.zeroxn.xuecheng.media.model.DTO.UploadFileArgsDTO;
import com.zeroxn.xuecheng.media.model.DTO.UploadFileResultDTO;
import com.zeroxn.xuecheng.media.model.pojo.MediaFiles;

import java.io.IOException;

/**
 * <p>
 * 媒资信息 服务类
 * </p>
 *
 * @author lisang
 * @since 2023-05-24
 */
public interface MediaFilesService extends IService<MediaFiles> {
    /**
     * 通过机构ID，分页参数，查询条件获取文件列表
     * @param companyId 机构ID
     * @param pageParams 分页参数
     * @param paramsDTO 查询条件
     * @return 文件列表或空
     */
    PageResult<MediaFiles> listMediaFiles(Long companyId, PageParams pageParams, QueryMediaParamsDTO paramsDTO);

    /**
     * 上传文件
     * @param companyId 机构ID
     * @param argsDTO 上传文件的参数
     * @param tempFilePath 文件的临时路径
     * @return 上传成功则返回链接 失败则返回错误信息
     */
    UploadFileResultDTO uploadFile(Long companyId, UploadFileArgsDTO argsDTO, String tempFilePath);

    /**
     * 通过文件的MD5码检查文件是否已经存在
     * @param fileMd5 待上传文件的MD5
     * @return 存在返回true 否则返回false
     * @throws IOException
     */
    RestResponse<Boolean> checkFile(String fileMd5) throws IOException;

    /**
     * 通过文件的md5码和分块索引检查分块文件是否已经上传
     * @param fileMd5 待上传文件的MD码
     * @param chunk 分块文件的索引
     * @return 以上传返回true 否则false
     * @throws IOException
     */
    RestResponse<Boolean> checkChunk(String fileMd5, int chunk) throws IOException;

    /**
     * 上传文件的分块文件
     * @param fileMd5 待上传文件的MD5码
     * @param chunk 分块文件的索引
     * @param filePath 分块文件的临时路径
     * @return 上传成功返回true 否则false
     */
    RestResponse<Boolean> uploadChunk(String fileMd5, int chunk, String filePath);

    /**
     * 通过文件的MD5码和分块文件数量来合并文件
     * @param companyId 机构ID
     * @param fileMd5 上传文件的MD5码
     * @param chunkTotal 文件分块数量
     * @param fileName 原文件名称
     * @return 合并成功返回true 否则false
     * @throws IOException
     */
    RestResponse<Boolean> chunkMerge(Long companyId, String fileMd5, int chunkTotal, String fileName) throws IOException;

    RestResponse<String> getVideoUrlById(String mediaId);
}
