package com.zeroxn.xuecheng.learning.model.dto;

import com.zeroxn.xuecheng.learning.model.entity.CourseTables;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author Mr.M
 * @version 1.0
 * @description 我的课程查询条件
 * @date 2022/10/6 9:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class CourseTableItemDto extends CourseTables {

    /**
     * 最近学习时间
     */
    private LocalDateTime learnDate;

    /**
     * 学习时长
     */
    private Long learnLength;

    /**
     * 章节id
     */
    private Long teachplanId;

    /**
     * 章节名称
     */
    private String teachplanName;


}
