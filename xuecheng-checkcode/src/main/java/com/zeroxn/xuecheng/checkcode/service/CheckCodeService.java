package com.zeroxn.xuecheng.checkcode.service;

import com.zeroxn.xuecheng.checkcode.dto.CheckCodeParamsDto;
import com.zeroxn.xuecheng.checkcode.dto.CheckCodeResultDto;

import java.time.Duration;

/**
 * @Author: lisang
 * @DateTime: 2023-10-08 18:14:48
 * @Description:
 */
public interface CheckCodeService {

    /**
     * 通过请求参数生成验证码
     * @param paramsDto 请求参数
     * @return 返回验证码和Key
     */
    CheckCodeResultDto generate(CheckCodeParamsDto paramsDto);

    /**
     * 校验验证码
     * @param key Key
     * @param code 验证码
     * @return 返回成功或失败
     */
    boolean validation(String key, String code);

    /**
     * 验证码生成接口
     */
    interface CheckCodeGenerate {
        String generate(int length);
    }

    /**
     * Key生成接口
     */
    interface KeyGenerate {
        String generate(String prefix);
    }

    /**
     * 验证码存储接口
     */
    interface CheckCodeStore {
        void set(String key, String code, Duration duration);
        String get(String key);
        void remove(String key);
    }
}
