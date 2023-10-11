package com.zeroxn.xuecheng.auth.service;

import com.zeroxn.xuecheng.auth.entity.User;

/**
 * @Author: lisang
 * @DateTime: 2023-10-11 17:31:03
 * @Description:
 */
public interface WechatAuthService {
    User wechatAuth(String code);
}
