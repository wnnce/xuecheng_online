package com.zeroxn.xuecheng.content.model.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @Author: lisang
 * @DateTime: 2023/6/13 下午6:41
 * @Description: 课程绑定视频的DTO类
 */
@Data
public class BindThachPlanMediaDTO {
    /**
     * 媒资文件ID
     */
    @NotBlank(message = "视频ID不能为空")
    @Schema(description = "视频文件的原始ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mediaId;
    /**
     * 课程计划ID
     */
    @NotNull(message = "课程计划ID不能为空")
    @Schema(description = "绑定视频的课程计划ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long teachplanId;
    /**
     * 媒资文件原始名称
     */
    @NotBlank(message = "视频文件名称不能为空")
    @Schema(description = "视频文件的原始名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fileName;
}
