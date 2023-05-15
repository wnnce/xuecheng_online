package com.zeroxn.xuecheng.base.enums;

/**
 * @Author: lisang
 * @DateTime: 2023/5/15 下午2:26
 * @Description: 通用异常枚举
 */
public enum CommonError {
    BASE_ERROR("系统错误，请重试"),
    PARAM_ERROR("查询参数错误"),
    OBJECT_NULL("对象为空"),
    QUERY_NULL("查询为空");
    private final String message;
    private CommonError(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
