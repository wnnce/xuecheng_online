package com.zeroxn.xuecheng.learning.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author lisang
 * @since 2023-10-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("xc_choose_course")
public class ChooseCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 机构id
     */
    private Long companyId;

    /**
     * 选课类型
     */
    private String orderType;

    /**
     * 添加时间
     */
    private LocalDateTime createDate;

    /**
     * 课程价格
     */
    private Double coursePrice;

    /**
     * 课程有效期(天)
     */
    private Integer validDays;

    /**
     * 选课状态
     */
    private String status;

    /**
     * 开始服务时间
     */
    private LocalDateTime validtimeStart;

    /**
     * 结束服务时间
     */
    private LocalDateTime validtimeEnd;

    /**
     * 备注
     */
    private String remarks;

}
