package com.zeroxn.xuecheng.content.model.DTO;

import com.zeroxn.xuecheng.content.model.pojo.CourseBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: lisang
 * @DateTime: 2023/5/12 下午4:00
 * @Description: 课程详细信息包含价格信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseBaseInfoDTO extends CourseBase {
    /**
     * 收费规则，对应数据字典
     */
    private String charge;

    /**
     * 价格
     */
    private Float price;


    /**
     * 原价
     */
    private Float originalPrice;

    /**
     * 咨询qq
     */
    private String qq;

    /**
     * 微信
     */
    private String wechat;

    /**
     * 电话
     */
    private String phone;

    /**
     * 有效期天数
     */
    private Integer validDays;

    /**
     * 大分类名称
     */
    private String mtName;

    /**
     * 小分类名称
     */
    private String stName;
}
