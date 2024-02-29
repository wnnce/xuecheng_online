package com.zeroxn.xuecheng.base.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023/5/10 下午9:52
 * @Description: 统一分页响应结果封装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResult<T> {
    /**
     * 返回的数据
     */
    private List<T> items;
    /**
     * 总记录条数
     */
    private Long counts;
    /**
     * 当前页码
     */
    private Long page;
    /**
     * 每页记录数
     */
    private Long pageSize;
}
