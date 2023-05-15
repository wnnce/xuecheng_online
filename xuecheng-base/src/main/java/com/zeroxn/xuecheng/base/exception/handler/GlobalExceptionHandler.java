package com.zeroxn.xuecheng.base.exception.handler;

import com.zeroxn.xuecheng.base.enums.CommonError;
import com.zeroxn.xuecheng.base.exception.CustomException;
import com.zeroxn.xuecheng.base.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: lisang
 * @DateTime: 2023/5/15 下午2:25
 * @Description: 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 捕获自定义异常
     * @param ex 自定义异常
     * @return 给客户端返回错误消息
     */
    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerCustomException(CustomException ex){
        log.error("抛出自定义异常，{}", ex.getMessage());
        return new ErrorResponse(ex.getMessage());
    }

    /**
     * 捕获全局异常
     * @param ex 全局异常
     * @return 返回通用错误消息
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerException(Exception ex){
        log.error("抛出异常，{}", ex.getMessage());
        return new ErrorResponse(CommonError.BASE_ERROR.getMessage());
    }
}
