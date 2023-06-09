package com.zeroxn.xuecheng.media.web;

import com.zeroxn.xuecheng.base.model.PageParams;
import com.zeroxn.xuecheng.base.model.PageResult;
import com.zeroxn.xuecheng.media.model.DTO.QueryMediaParamsDTO;
import com.zeroxn.xuecheng.media.model.DTO.UploadFileArgsDTO;
import com.zeroxn.xuecheng.media.model.DTO.UploadFileResultDTO;
import com.zeroxn.xuecheng.media.model.pojo.MediaFiles;
import com.zeroxn.xuecheng.media.service.MediaFilesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


/**
 * @Author: lisang
 * @DateTime: 2023/5/24 下午7:47
 * @Description:
 */
@Tag(name = "媒体资源管理接口")
@RestController
public class MediaFilesController {
    private final MediaFilesService mediaFilesService;
    public MediaFilesController(MediaFilesService mediaFilesService){
        this.mediaFilesService = mediaFilesService;
    }
    @PostMapping("/files")
    @Operation(summary = "获取文件列表")
    public PageResult<MediaFiles> listMediaFiles(PageParams pageParams, @RequestBody QueryMediaParamsDTO paramsDTO){
        Long companyId = 1232141425L;
        return mediaFilesService.listMediaFiles(companyId, pageParams, paramsDTO);
    }
    @PostMapping("/upload/coursefile")
    @Operation(summary = "上传文件（视频、文档）接口")
    public UploadFileResultDTO uploadFile(@RequestPart("filedata")MultipartFile file,
                                          @RequestParam(value = "objectName", required = false) String objectName) throws IOException {
        String filename = file.getOriginalFilename();
        File tempFile = File.createTempFile("upload", ".temp");
        Long companyId = 1232141425L;
        long size = file.getSize();
        file.transferTo(tempFile);
        UploadFileArgsDTO argsDTO = new UploadFileArgsDTO();
        argsDTO.setFilename(filename);
        argsDTO.setFileSize(size);
        argsDTO.setFileType("001001");
        return mediaFilesService.uploadFile(companyId, argsDTO, tempFile.getAbsolutePath(), objectName);
    }
}
