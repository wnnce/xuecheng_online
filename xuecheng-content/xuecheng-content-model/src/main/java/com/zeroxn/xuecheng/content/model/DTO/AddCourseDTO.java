package com.zeroxn.xuecheng.content.model.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: lisang
 * @DateTime: 2023/5/12 下午3:53
 * @Description: 添加课程接收数据实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCourseDTO {
    @NotEmpty(message = "课程名称不能为空")
    @Schema(description = "课程名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotEmpty(message = "适用人群不能为空")
    @Size(message = "适用人群内容过少",min = 10)
    @Schema(description = "适用人群", requiredMode = Schema.RequiredMode.REQUIRED)
    private String users;

    @Schema(description = "课程标签")
    private String tags;

    @NotEmpty(message = "课程分类不能为空")
    @Schema(description = "大分类", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mt;

    @NotEmpty(message = "课程分类不能为空")
    @Schema(description = "小分类", requiredMode = Schema.RequiredMode.REQUIRED)
    private String st;

    @NotEmpty(message = "课程等级不能为空")
    @Schema(description = "课程等级", requiredMode = Schema.RequiredMode.REQUIRED)
    private String grade;

    @Schema(description = "教学模式（普通，录播，直播等）", requiredMode = Schema.RequiredMode.REQUIRED)
    private String teachmode;

    @Schema(description = "课程介绍")
    private String description;

    @Schema(description = "课程图片", requiredMode = Schema.RequiredMode.REQUIRED)
    private String pic;

    @NotEmpty(message = "收费规则不能为空")
    @Schema(description = "收费规则，对应数据字典", requiredMode = Schema.RequiredMode.REQUIRED)
    private String charge;

    @Schema(description = "价格")
    private Double price;

    @Schema(description = "原价")
    private Double originalPrice;

    @Schema(description = "QQ")
    private String qq;

    @Schema(description = "微信")
    private String wechat;

    @Schema(description = "电话")
    private String phone;

    @Schema(description = "有效期")
    private Integer validDays;
}
