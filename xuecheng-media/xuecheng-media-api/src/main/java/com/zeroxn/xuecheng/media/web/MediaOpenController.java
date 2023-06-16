package com.zeroxn.xuecheng.media.web;

import com.zeroxn.xuecheng.media.model.DTO.RestResponse;
import com.zeroxn.xuecheng.media.service.MediaFilesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lisang
 * @DateTime: 2023/6/15 下午8:44
 * @Description:
 */
@RestController("/open")
public class MediaOpenController {
    private final MediaFilesService mediaFilesService;
    public MediaOpenController(MediaFilesService mediaFilesService){
        this.mediaFilesService = mediaFilesService;
    }
    @GetMapping("/preview/{mediaId}")
    public RestResponse<String> getVideoUrlById(@PathVariable("mediaId") String mediaId){
        return mediaFilesService.getVideoUrlById(mediaId);
    }
}
