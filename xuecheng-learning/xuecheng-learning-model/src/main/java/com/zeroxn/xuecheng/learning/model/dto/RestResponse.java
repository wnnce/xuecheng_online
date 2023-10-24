package com.zeroxn.xuecheng.learning.model.dto;

import lombok.Data;

@Data
public class RestResponse<T> {
    private int code;
    private String msg;
    private T result;
    public RestResponse(){
        this(0, "ok");
    }
    public RestResponse(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
    public static <T> RestResponse<T> success(String msg, T result){
        RestResponse<T> response = new RestResponse<>();
        response.setMsg(msg);
        response.setResult(result);
        return response;
    }
    public static <T> RestResponse<T> success(T result){
        RestResponse<T> response = new RestResponse<>();
        response.setResult(result);
        return response;
    }
    public static <T> RestResponse<T> fail(String msg, T result){
        RestResponse<T> response = new RestResponse<>();
        response.setCode(1);
        response.setMsg(msg);
        response.setResult(result);
        return response;
    }
}