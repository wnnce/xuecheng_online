package com.zeroxn.xuecheng.bash.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: lisang
 * @DateTime: 2023/5/10 下午9:43
 * @Description: 分页查询参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageParams {
    /**
     * 当前页码
     */
    private Long pageNo;
    /**
     * 每页记录数
     */
    private Long pageSize;
}
