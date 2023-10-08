package com.zeroxn.xuecheng.checkcode.service.impl;

import com.zeroxn.xuecheng.checkcode.service.CheckCodeService.KeyGenerate;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Author: lisang
 * @DateTime: 2023-10-08 19:12:01
 * @Description:
 */
@Service("UUIDKeyGenerate")
public class UUIDKeyGenerate implements KeyGenerate {
    @Override
    public String generate(String prefix) {
        return prefix + UUID.randomUUID().toString().replaceAll("-", "");
    }
}