package com.zeroxn.xuecheng.base.exception;

/**
 * @Author: lisang
 * @DateTime: 2023/5/15 下午2:24
 * @Description: 项目自定义异常
 */
public class CustomException extends RuntimeException{
    public CustomException() {
        super();
    }

    public CustomException(String message) {
        super(message);
    }
}
