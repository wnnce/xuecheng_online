package com.zeroxn.xuecheng.learning.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: lisang
 * @DateTime: 2023-10-24 20:36:49
 * @Description:
 */
@FeignClient(value = "content-api")
public interface ContentClient {

}
