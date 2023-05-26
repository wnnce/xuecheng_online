package com.zeroxn.xuecheng.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeroxn.xuecheng.base.exception.CustomException;
import com.zeroxn.xuecheng.base.model.PageParams;
import com.zeroxn.xuecheng.base.model.PageResult;
import com.zeroxn.xuecheng.media.config.MinioConfig;
import com.zeroxn.xuecheng.media.mapper.MediaFilesMapper;
import com.zeroxn.xuecheng.media.model.DTO.QueryMediaParamsDTO;
import com.zeroxn.xuecheng.media.model.DTO.UploadFileArgsDTO;
import com.zeroxn.xuecheng.media.model.DTO.UploadFileResultDTO;
import com.zeroxn.xuecheng.media.model.pojo.MediaFiles;
import com.zeroxn.xuecheng.media.service.MediaFilesService;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * 媒资信息 服务实现类
 * </p>
 *
 * @author lisang
 * @since 2023-05-24
 */
@Service
@Slf4j
public class MediaFilesServiceImpl extends ServiceImpl<MediaFilesMapper, MediaFiles> implements MediaFilesService{
    private final MediaFilesMapper mediaFilesMapper;
    private final MinioClient minioClient;
    private final MinioConfig minioConfig;
    public MediaFilesServiceImpl(MediaFilesMapper mediaFilesMapper, MinioClient minioClient, MinioConfig minioConfig){
        this.mediaFilesMapper = mediaFilesMapper;
        this.minioClient = minioClient;
        this.minioConfig = minioConfig;
    }

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

    @Override
    public UploadFileResultDTO uploadFile(Long companyId, UploadFileArgsDTO argsDTO, String tempFilePath) {
        String fileMd5 = getFileMd5(tempFilePath);
        MediaFiles mediaFiles = mediaFilesMapper.selectById(fileMd5);
        if(mediaFiles != null){
            UploadFileResultDTO resultDTO = new UploadFileResultDTO();
            BeanUtils.copyProperties(mediaFiles, resultDTO);
            return resultDTO;
        }
        String fileName = argsDTO.getFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String fileObject = getFileObject(fileMd5, suffix);
        boolean result = minioUploadFile(minioConfig.getFileBucket(), tempFilePath, fileObject);
        if(!result){
            throw new CustomException("文件上传失败");
        }
        UploadFileResultDTO resultDTO = saveMediaFiles(companyId, argsDTO, minioConfig.getFileBucket(), fileMd5, fileObject);
        if(resultDTO == null){
            throw new CustomException("文件保存失败");
        }
        return resultDTO;

    }
    private String getFileMd5(String filePath) {
        try{
            File file = new File(filePath);
            FileInputStream stream = new FileInputStream(file);
            return DigestUtils.md5DigestAsHex(stream);
        }catch (IOException ex){
            log.error("获取文件MD5出错，错误消息：{}", ex.getMessage());
        }
        return null;
    }
    private String getFileObject(String fileMd5, String fileSuffix){
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        date = date.replace("-", "/");
        return date + "/" + fileMd5 + fileSuffix;
    }
    private boolean minioUploadFile(String bucket, String fileName, String object){
        try{
            minioClient.uploadObject(UploadObjectArgs.builder()
                    .bucket(bucket)
                    .filename(fileName)
                    .object(object)
                    .build());
            return true;
        }catch (Exception ex){
            log.error("minio上传文件报错，错误消息：{}", ex.getMessage());
        }
        return false;
    }
    private UploadFileResultDTO saveMediaFiles(Long companyId, UploadFileArgsDTO argsDTO, String bucket, String fileMd5,
                                               String filePath){
        MediaFiles mediaFiles = new MediaFiles();
        BeanUtils.copyProperties(argsDTO, mediaFiles);
        mediaFiles.setId(fileMd5);
        mediaFiles.setFileId(fileMd5);
        mediaFiles.setFilePath(filePath);
        mediaFiles.setUrl("/" + bucket + "/" + filePath);
        mediaFiles.setStatus("1");
        mediaFiles.setCreateDate(LocalDateTime.now());
        mediaFiles.setAuditStatus("002003");
        mediaFiles.setCompanyId(companyId);
        mediaFiles.setBucket(bucket);
        int insert = mediaFilesMapper.insert(mediaFiles);
        if(insert <= 0){
            log.error("文件数据保存到数据库失败");
            return null;
        }
        UploadFileResultDTO resultDTO = new UploadFileResultDTO();
        BeanUtils.copyProperties(mediaFiles, resultDTO);
        return resultDTO;
    }

}
