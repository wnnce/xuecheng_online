package com.zeroxn.xuecheng.base.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * @Author: lisang
 * @DateTime: 2023/5/12 下午5:07
 * @Description: 通用工具类
 */
@Slf4j
public class CommonUtils {
    /**
     * 判断对象中指定的属性名是否为空
     * @param object 对象
     * @param fieldNames 需要判空的属性名
     * @return 若存在空属性则返回 true
     */
    public static boolean  checkObjectFieldIsEmpty(Object object, String ...fieldNames) {
        Class objClass =  object.getClass();
        for (String fieldName : fieldNames) {
            try{
                Field field = objClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object fieldObj = field.get(object);
                if(fieldObj == null || isObjectIsEmpty(fieldObj)){
                    return true;
                }
            }catch (NoSuchFieldException ex){
                log.error("{} 类中指定的 {} 属性不存在", objClass.getName(), fieldName);
                return true;
            }catch (IllegalAccessException ex){
                log.error("获取 {} 属性的值错误", fieldName);
                return true;
            }
        }
        return false;
    }
    private static boolean isObjectIsEmpty(Object obj){
        if(obj.getClass().equals(String.class)){
            return obj.toString().length() == 0;
        }
        return false;
    }
}
