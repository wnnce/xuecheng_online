package com.zeroxn.xuecheng.media.model.DTO;

import lombok.Data;

/**
 * @Author: lisang
 * @DateTime: 2023/5/26 下午5:34
 * @Description:
 */
@Data
public class RestResponse <T> {
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
