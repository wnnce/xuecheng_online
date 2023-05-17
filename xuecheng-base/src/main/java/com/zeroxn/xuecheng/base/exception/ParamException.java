package com.zeroxn.xuecheng.base.exception;

/**
 * @Author: lisang
 * @DateTime: 2023/5/17 下午1:06
 * @Description: 查询参数异常
 */
public class ParamException extends RuntimeException{
    public ParamException(String message){
        super(message);
    }
}
