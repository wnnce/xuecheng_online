package com.zeroxn.xuecheng.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: lisang
 * @DateTime: 2023-10-10 18:47:12
 * @Description:
 */
@FeignClient(value = "check-code-server", fallbackFactory = CheckCodeClientFallbackFactory.class)
public interface CheckCodeClient {
    @PostMapping("/checkcode/verify")
    Boolean validation(@RequestParam("key") String key, @RequestParam("code") String code);
}