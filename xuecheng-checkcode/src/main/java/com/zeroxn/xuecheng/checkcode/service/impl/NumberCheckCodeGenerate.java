package com.zeroxn.xuecheng.checkcode.service.impl;

import com.zeroxn.xuecheng.checkcode.service.CheckCodeService.CheckCodeGenerate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * @Author: lisang
 * @DateTime: 2023-10-08 19:15:22
 * @Description:
 */
@Service("NumberCheckCodeGenerate")
public class NumberCheckCodeGenerate implements CheckCodeGenerate {
    private final String DICTIONARY = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    @Override
    public String generate(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        IntStream.range(0, length).forEach(i -> {
            int index = random.nextInt(DICTIONARY.length());
            char c = DICTIONARY.charAt(index);
            stringBuilder.append(c);
        });
        return stringBuilder.toString();
    }
}
