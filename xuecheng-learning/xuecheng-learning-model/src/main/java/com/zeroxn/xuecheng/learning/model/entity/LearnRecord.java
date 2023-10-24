package com.zeroxn.xuecheng.learning.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("xc_learn_record")
public class LearnRecord implements Serializable {

    private static final long serialVersionUID = 1L;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getLearnDate() {
        return learnDate;
    }

    public void setLearnDate(LocalDateTime learnDate) {
        this.learnDate = learnDate;
    }

    public Long getLearnLength() {
        return learnLength;
    }

    public void setLearnLength(Long learnLength) {
        this.learnLength = learnLength;
    }

    public Long getTeachplanId() {
        return teachplanId;
    }

    public void setTeachplanId(Long teachplanId) {
        this.teachplanId = teachplanId;
    }

    public String getTeachplanName() {
        return teachplanName;
    }

    public void setTeachplanName(String teachplanName) {
        this.teachplanName = teachplanName;
    }

    @Override
    public String toString() {
        return "LearnRecord{" +
            "id = " + id +
            ", courseId = " + courseId +
            ", courseName = " + courseName +
            ", userId = " + userId +
            ", learnDate = " + learnDate +
            ", learnLength = " + learnLength +
            ", teachplanId = " + teachplanId +
            ", teachplanName = " + teachplanName +
        "}";
    }
}
