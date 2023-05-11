package com.zeroxn.xuecheng.bash.model;

import lombok.AllArgsConstructor;
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
public class PageResult<T> {
    private List<T> items;
}
