package com.zeroxn.xuecheng.content.model.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @Author: lisang
 * @DateTime: 2023-09-19 20:00:55
 * @Description: 课程索引实体类
 */
@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class CourseIndex {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 机构ID
     */
    private Long companyId;
    /**
     * 机构名称
     */
    private String companyName;
    /**
     * 课程名称
     */
    private String name;
    /**
     * 使用
     */
    private String users;
    /**
     * 课程标签
     */
    private String tags;
    /**
     * 大分类
     */
    private String mt;
    /**
     * 大分类名称
     */
    private String mtName;
    /**
     * 小分类
     */
    private String st;
    /**
     * 小分类名称
     */
    private String stName;
    /**
     * 课程等级
     */
    private String grade;
    /**
     * 教育模式
     */
    private String teachmode;
    /**
     * 课程封面
     */
    private String pic;
    /**
     * 课程简介
     */
    private String description;
    /**
     * 课程创建时间
     */
    private LocalDateTime createDate;
    /**
     * 课程状态
     */
    private String status;
    /**
     * 课程备注
     */
    private String remark;
    /**
     * 收费规则
     */
    private String charge;
    /**
     * 课程现售价
     */
    private Float price;
    /**
     * 课程原售价
     */
    private Float originalPrice;
    /**
     * 课程有效天数
     */
    private Integer validDays;
}
