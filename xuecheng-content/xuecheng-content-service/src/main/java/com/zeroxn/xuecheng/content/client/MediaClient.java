package com.zeroxn.xuecheng.content.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: lisang
 * @DateTime: 2023/6/23 下午2:56
 * @Description: Media模块Client
 */
@FeignClient("media-api")
public interface MediaClient {
    @PostMapping("/upload/coursefile")
    String uploadFile(@RequestPart("filedata") MultipartFile file, @RequestParam("objectName") String objectName);
}
