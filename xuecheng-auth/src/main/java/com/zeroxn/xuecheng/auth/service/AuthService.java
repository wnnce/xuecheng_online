package com.zeroxn.xuecheng.auth.service;

import com.zeroxn.xuecheng.auth.dto.AuthParamsDto;
import com.zeroxn.xuecheng.auth.dto.UserExtend;

/**
 * @Author: lisang
 * @DateTime: 2023-10-03 21:57:25
 * @Description:
 */
public interface AuthService {
    UserExtend execute(AuthParamsDto authParams);
}
