package com.zeroxn.xuecheng.learning.client;

import com.zeroxn.xuecheng.learning.model.dto.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: lisang
 * @DateTime: 2023-10-24 20:38:19
 * @Description:
 */
@FeignClient(value = "media-api", fallbackFactory = MediaClientFallbackFactory.class)
public interface MediaClient {
    @GetMapping("/media/open/preview/{mediaId}")
    RestResponse<String> getVideoUrlById(@PathVariable("mediaId") String mediaId);
}
