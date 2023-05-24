package com.zeroxn.xuecheng.media.web;

import com.zeroxn.xuecheng.base.model.PageParams;
import com.zeroxn.xuecheng.base.model.PageResult;
import com.zeroxn.xuecheng.media.model.DTO.QueryMediaParamsDTO;
import com.zeroxn.xuecheng.media.model.pojo.MediaFiles;
import com.zeroxn.xuecheng.media.service.MediaFilesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


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
}
