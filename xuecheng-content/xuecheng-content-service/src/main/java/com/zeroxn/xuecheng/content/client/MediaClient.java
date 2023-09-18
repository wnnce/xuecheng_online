package com.zeroxn.xuecheng.content.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: lisang
 * @DateTime: 2023/6/23 下午2:56
 * @Description: Media模块Client fallbackFactory熔断后的降级处理策略，可以拿到错误消息。使用fallback可以进行降级处理 拿不到错误消息
 */
@FeignClient(value = "media-api", fallbackFactory = MediaClientFallbackFactory.class)
public interface MediaClient {
    @PostMapping(value = "/media/upload/coursefile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String uploadFile(@RequestPart("filedata") MultipartFile file, @RequestParam("objectName") String objectName);
}
