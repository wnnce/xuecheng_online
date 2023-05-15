package com.zeroxn.xuecheng.base.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: lisang
 * @DateTime: 2023/5/15 下午2:22
 * @Description: 异常返回错误消息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    /**
     * 需要返回的错误消息
     */
    private String errMessage;
}
