package com.zeroxn.xuecheng.checkcode.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: lisang
 * @DateTime: 2023-10-08 18:11:26
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckCodeResultDto {
    /**
     * 用于验证的Key
     */
    private String key;
    /**
     * 混淆后的内容
     * 图片验证码为图片的Base编码
     * 其它为null
     */
    private String aliasing;
}
