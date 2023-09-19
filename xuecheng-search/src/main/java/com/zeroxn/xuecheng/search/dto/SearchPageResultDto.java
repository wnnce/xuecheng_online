package com.zeroxn.xuecheng.search.dto;

import com.zeroxn.xuecheng.base.model.PageResult;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023-09-19 19:57:37
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class SearchPageResultDto<T> extends PageResult<T> {
    /**
     * 大分类列表
     */
    private List<String> mtList;
    /**
     * 小分类列表
     */
    private List<String> stList;

    public SearchPageResultDto(List<T> items, Long counts, Long page, Long pageSize) {
        super(items, counts, page, pageSize);
    }
}
