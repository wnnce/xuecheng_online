package com.zeroxn.xuecheng.content.model.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @Author: lisang
 * @DateTime: 2023/5/17 上午11:33
 * @Description: 保存课程计划数据接收DTO类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SaveTeachPlanDTO {

    private Long id;
    /**
     * 课程计划名称
     */
    @NotEmpty(message = "课程计划名称不能为空")
    private String pname;

    /**
     * 课程计划父级Id
     */
    @NotNull(message = "课程计划父级ID不能为空")
    private Long parentid;

    /**
     * 层级，分为1、2、3级
     */
    @NotNull(message = "计划层级不能为空")
    private Integer grade;

    /**
     * 课程类型:1视频、2文档
     */
    private String mediaType;

    /**
     * 开始直播时间
     */
    private LocalDateTime startTime;

    /**
     * 直播结束时间
     */
    private LocalDateTime endTime;

    /**
     * 章节及课程时介绍
     */
    private String description;

    /**
     * 时长，单位时:分:秒
     */
    private String timelength;

    /**
     * 排序字段
     */
    private Integer orderby;

    /**
     * 课程标识
     */
    @NotNull(message = "课程ID不能为空")
    private Long courseId;

    /**
     * 课程发布标识
     */
    private Long coursePubId;

    /**
     * 状态（1正常  0删除）
     */
    private Integer status;

    /**
     * 是否支持试学或预览（试看）
     */
    private String isPreview;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    private LocalDateTime changeDate;

    private Boolean ctlBarShow;
    private Boolean ctlEditTitle;
    private Long teachplanId;
}
