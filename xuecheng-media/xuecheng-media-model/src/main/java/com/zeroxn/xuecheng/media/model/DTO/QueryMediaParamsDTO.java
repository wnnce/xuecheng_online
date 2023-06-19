package com.zeroxn.xuecheng.media.model.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: lisang
 * @DateTime: 2023/5/24 下午7:45
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryMediaParamsDTO {
    /**
     * 文件审核状态
     */
    @Schema(description = "文件审核状态")
    private String auditStatus;
    /**
     * 文件类型
     */
    @Schema(description = "文件类型")
    private String fileType;
    /**
     * 文件名称
     */
    @Schema(description = "文件名称")
    private String filename;
}
