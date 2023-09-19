package com.zeroxn.xuecheng.search.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: lisang
 * @DateTime: 2023-09-19 19:55:10
 * @Description: 搜索课程参数DTO类
 */
@Data
@ToString
public class SearchCourseParamDto {
    /**
     * 搜索关键字
     */
    private String keywords;
    /**
     * 大分类
     */
    private String mt;
    /**
     * 小分类
     */
    private String st;
    /**
     * 难度等级
     */
    private String grade;
}
