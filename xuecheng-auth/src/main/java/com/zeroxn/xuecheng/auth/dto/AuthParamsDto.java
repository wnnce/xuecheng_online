package com.zeroxn.xuecheng.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: lisang
 * @DateTime: 2023-10-03 21:39:40
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthParamsDto {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 手机号
     */
    private String cellphone;
    /**
     * 验证码
     */
    private String checkcode;
    /**
     * 验证码Key
     */
    private String checkcodekey;
    /**
     * 认证类型
     */
    private String authType;
    /**
     * 附加数据
     */
    private Map<String, Object> payload = new HashMap<>();
}
