package com.zeroxn.xuecheng.search.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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
@Document(indexName = "course_publish")
public class CourseIndex {
    /**
     * 主键ID
     */
    @Id
    private Long id;
    /**
     * 机构ID
     */
    @Field(type = FieldType.Keyword)
    private Long companyId;
    /**
     * 机构名称
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String companyName;
    /**
     * 课程名称
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String name;
    /**
     * 使用
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String users;
    /**
     * 课程标签
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String tags;
    /**
     * 大分类
     */
    @Field(type = FieldType.Keyword)
    private String mt;
    /**
     * 大分类名称
     */
    @Field(type = FieldType.Keyword)
    private String mtName;
    /**
     * 小分类
     */
    @Field(type = FieldType.Keyword)
    private String st;
    /**
     * 小分类名称
     */
    @Field(type = FieldType.Keyword)
    private String stName;
    /**
     * 课程等级
     */
    @Field(type = FieldType.Keyword)
    private String grade;
    /**
     * 教育模式
     */
    @Field(type = FieldType.Keyword)
    private String teachmode;
    /**
     * 课程封面
     */
    @Field(type = FieldType.Text, index = false)
    private String pic;
    /**
     * 课程简介
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String description;
    /**
     * 课程创建时间
     */
    @Field(type = FieldType.Date, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    /**
     * 课程状态
     */
    @Field(type = FieldType.Keyword)
    private String status;
    /**
     * 课程备注
     */
    @Field(type = FieldType.Text, index = false)
    private String remark;
    /**
     * 收费规则
     */
    @Field(type = FieldType.Keyword)
    private String charge;
    /**
     * 课程现售价
     */
    @Field(type = FieldType.Scaled_Float, scalingFactor = 100)
    private Float price;
    /**
     * 课程原售价
     */
    @Field(type = FieldType.Scaled_Float, scalingFactor = 100)
    private Float originalPrice;
    /**
     * 课程有效天数
     */
    @Field(type = FieldType.Integer)
    private Integer validDays;
}
