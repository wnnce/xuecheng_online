package com.zeroxn.xuecheng.auth.service.impl;

import com.zeroxn.xuecheng.auth.dto.AuthParamsDto;
import com.zeroxn.xuecheng.auth.dto.UserExtend;
import com.zeroxn.xuecheng.auth.service.AuthService;
import org.springframework.stereotype.Service;

/**
 * @Author: lisang
 * @DateTime: 2023-10-03 21:59:25
 * @Description:
 */
@Service("SMSAuthService")
public class SMSAuthServiceImpl implements AuthService {
    @Override
    public UserExtend execute(AuthParamsDto authParams) {
        return null;
    }
}
