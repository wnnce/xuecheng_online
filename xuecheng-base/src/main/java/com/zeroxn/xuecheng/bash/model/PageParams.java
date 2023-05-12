package com.zeroxn.xuecheng.bash.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
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
@Schema(description = "分页查询参数")
public class PageParams {
    /**
     * 当前页码
     */
    @Schema(description = "页码", type = "int")
    private Long pageNo;
    /**
     * 每页记录数
     */
    @Schema(description = "每页记录数", type = "int")
    private Long pageSize;
}
