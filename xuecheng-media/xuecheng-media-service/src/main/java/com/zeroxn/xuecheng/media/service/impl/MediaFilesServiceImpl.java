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
import com.zeroxn.xuecheng.media.mapper.MediaProcessMapper;
import com.zeroxn.xuecheng.media.model.DTO.QueryMediaParamsDTO;
import com.zeroxn.xuecheng.media.model.DTO.RestResponse;
import com.zeroxn.xuecheng.media.model.DTO.UploadFileArgsDTO;
import com.zeroxn.xuecheng.media.model.DTO.UploadFileResultDTO;
import com.zeroxn.xuecheng.media.model.pojo.MediaFiles;
import com.zeroxn.xuecheng.media.model.pojo.MediaProcess;
import com.zeroxn.xuecheng.media.service.MediaFilesService;
import com.zeroxn.xuecheng.media.utils.MinioUtils;
import io.minio.ComposeSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

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
    private final MediaProcessMapper mediaProcessMapper;
    private final MinioConfig minioConfig;
    private final MinioUtils minioUtils;
    public MediaFilesServiceImpl(MediaFilesMapper mediaFilesMapper, MinioConfig minioConfig, MinioUtils minioUtils,
                                 MediaProcessMapper mediaProcessMapper){
        this.mediaFilesMapper = mediaFilesMapper;
        this.minioConfig = minioConfig;
        this.minioUtils = minioUtils;
        this.mediaProcessMapper = mediaProcessMapper;
    }

    @Override
    public PageResult<MediaFiles> listMediaFiles(Long companyId, PageParams pageParams, QueryMediaParamsDTO paramsDTO) {
        LambdaQueryWrapper<MediaFiles> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MediaFiles::getCompanyId, companyId)
                .eq(StringUtils.isNotEmpty(paramsDTO.getFileName()), MediaFiles::getAuditStatus, paramsDTO.getAuditStatus())
                .eq(StringUtils.isNotEmpty(paramsDTO.getFileType()), MediaFiles::getFileType, paramsDTO.getFileType())
                .like(StringUtils.isNotEmpty(paramsDTO.getFileName()), MediaFiles::getFilename, paramsDTO.getFileName());
        Page<MediaFiles> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        Page<MediaFiles> pageResult = this.page(page, queryWrapper);
        return new PageResult<>(pageResult.getRecords(), pageResult.getTotal(), pageResult.getCurrent(),
                pageResult.getSize());
    }

    /**
     * 先通过文件的MD5查询数据库 如果记录存在 那么直接返回  不存在就从请求参数中取出需要的值然后将文件上传到minio并保存记录到数据库
     * @param companyId 机构ID
     * @param argsDTO 上传文件的参数
     * @param tempFilePath 文件的临时路径
     * @return
     */
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
        boolean result = minioUtils.uploadFile(minioConfig.getFileBucket(), tempFilePath, fileObject);
        if(!result){
            throw new CustomException("文件上传失败");
        }
        UploadFileResultDTO resultDTO = saveMediaFiles(companyId, argsDTO, minioConfig.getFileBucket(), fileMd5, fileObject);
        if(resultDTO == null){
            throw new CustomException("文件保存失败");
        }
        return resultDTO;

    }

    /**
     * 先通过MD5查询数据库 如果数据库中存在再查询minio的存储桶中是否存在
     * @param fileMd5 待上传文件的MD5
     * @return
     * @throws IOException
     */
    @Override
    public RestResponse<Boolean> checkFile(String fileMd5) throws IOException {
        MediaFiles mediaFiles = mediaFilesMapper.selectById(fileMd5);
        if(mediaFiles != null){
            String bucket = mediaFiles.getBucket();
            String filePath = mediaFiles.getFilePath();
            File tempFile = minioUtils.cloneFile(bucket, filePath);
            if(tempFile != null){
                return RestResponse.success(true);
            }
        }
        return RestResponse.success(false);
    }

    @Override
    public RestResponse<Boolean> checkChunk(String fileMd5, int chunk) throws IOException {
        String chunkFilePath = minioUtils.getMinioFileChunkPath(fileMd5) + chunk;
        File tempFile = minioUtils.cloneFile(minioConfig.getVideoBucket(), chunkFilePath);
        if(tempFile != null){
            return RestResponse.success(true);
        }
        return RestResponse.success(false);
    }

    @Override
    public RestResponse<Boolean> uploadChunk(String fileMd5, int chunk, String filePath) {
        String chunkFilePath = minioUtils.getMinioFileChunkPath(fileMd5) + chunk;
        boolean result = minioUtils.uploadFile(minioConfig.getVideoBucket(), filePath, chunkFilePath);
        if(!result){
            return RestResponse.fail("文件上传失败", false);
        }
        return RestResponse.success(true);
    }

    /**
     * 先构造合并文件需要的请求参数 然后请求合并文件 合并成功后下载文件校验MD5码 校验成功再将记录保存到数据库 然后返回
     * @param companyId 机构ID
     * @param fileMd5 上传文件的MD5码
     * @param chunkTotal 文件分块数量
     * @param fileName 原文件名称
     * @return
     * @throws IOException
     */
    @Override
    public RestResponse<Boolean> chunkMerge(Long companyId, String fileMd5, int chunkTotal, String fileName) throws IOException {
        String chunkFilePath = minioUtils.getMinioFileChunkPath(fileMd5);
        List<ComposeSource> sourceList = Stream.iterate(0, i -> ++i).limit(chunkTotal).map(i ->
                ComposeSource.builder().bucket(minioConfig.getVideoBucket()).object(chunkFilePath + i).build()).toList();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String target = minioUtils.getMinioFilePath(fileMd5) + fileMd5 + suffix;
        // 合并文件
        boolean result = minioUtils.mergeFile(minioConfig.getVideoBucket(), sourceList, target);
        if(!result){
            return RestResponse.fail("文件合并失败", false);
        }
        // 下载文件并校验MD5码
        File tempFile = minioUtils.cloneFile(minioConfig.getVideoBucket(), target);
        String minioMd5 = getFileMd5(tempFile.getAbsolutePath());
        if(!fileMd5.equals(minioMd5)){
            log.error("文件合并MD5错误，原文件MD5：{}，合并后文件MD5：{}，文件名：{}", fileMd5, minioMd5, fileName);
            return RestResponse.fail("文件合并失败", false);
        }
        UploadFileArgsDTO argsDTO = new UploadFileArgsDTO();
        argsDTO.setFileSize(tempFile.length());
        argsDTO.setFilename(fileName);
        argsDTO.setFileType("001002");
        // 记录保存到数据库
        UploadFileResultDTO resultDTO = saveMediaFiles(companyId, argsDTO, minioConfig.getVideoBucket(), fileMd5, target);
        // 删除分块文件
        boolean deleteResult = clearChunkFile(chunkFilePath, chunkTotal);
        if(!deleteResult){
            return RestResponse.fail("删除分块文件失败", false);
        }
        // 将avi视频文件保存到待处理任务库
        if(resultDTO.getFilePath().endsWith("avi")){
            MediaProcess mediaProcess = new MediaProcess();
            mediaProcess.setFileId(resultDTO.getFileId());
            mediaProcess.setBucket(resultDTO.getBucket());
            mediaProcess.setFilePath(resultDTO.getFilePath());
            mediaProcess.setFilename(resultDTO.getFilename());
            mediaProcess.setStatus("1");
            mediaProcess.setCreateDate(LocalDateTime.now());
            int insert = mediaProcessMapper.insert(mediaProcess);
            if(insert <= 0){
                log.error("保存到待处理任务库失败，文件ID：{}", mediaProcess.getFileId());
            }
        }
        return RestResponse.success(true);
    }

    private boolean clearChunkFile(String chunkFilePath, int chunkTotal){
        List<String> chunkFilePathList = Stream.iterate(0, i -> ++i).limit(chunkTotal)
                .map(i -> chunkFilePath + i).toList();
        return minioUtils.deleteFiles(minioConfig.getVideoBucket(), chunkFilePathList);
    }

    private String getFileMd5(String filePath) {
        try{
            File file = new File(filePath);
            FileInputStream stream = new FileInputStream(file);
            String fileMd5 = DigestUtils.md5DigestAsHex(stream);
            stream.close();
            return fileMd5;
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
