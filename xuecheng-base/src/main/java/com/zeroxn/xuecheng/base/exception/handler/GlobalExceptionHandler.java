package com.zeroxn.xuecheng.base.exception.handler;

import com.zeroxn.xuecheng.base.enums.CommonError;
import com.zeroxn.xuecheng.base.exception.CustomException;
import com.zeroxn.xuecheng.base.exception.ErrorResponse;
import com.zeroxn.xuecheng.base.exception.ParamException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;

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
     * 捕获自自定义的查询参数异常 用来提示前端提交的参数有误
     * @param ex 参数异常
     * @return 返回错误消息
     */
    @ExceptionHandler(ParamException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerParamException(ParamException ex){
        log.error("抛出参数异常，{}", ex.getMessage());
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
        ex.printStackTrace();
        log.error("抛出异常，{}", ex.getMessage());
        return new ErrorResponse(CommonError.BASE_ERROR.getMessage());
    }

    /**
     * 处理validation参数校验错误
     * @param ex 参数校验错误异常
     * @return 返回错误消息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<ObjectError> allErrors = ex.getAllErrors();
        String[] messages = allErrors.stream().map(ObjectError::getDefaultMessage).toArray(String[]::new);
        String errorMessage = String.join(",", messages);
        return new ErrorResponse(errorMessage);
    }
}
