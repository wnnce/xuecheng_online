package com.zeroxn.xuecheng.learning.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
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
@TableName("xc_course_tables")
@AllArgsConstructor
@NoArgsConstructor
public class CourseTables implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 选课记录id
     */
    private Long chooseCourseId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 机构id
     */
    private Long companyId;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程类型
     */
    private String courseType;

    /**
     * 添加时间
     */
    private LocalDateTime createDate;

    /**
     * 开始服务时间
     */
    private LocalDateTime validtimeStart;

    /**
     * 到期时间
     */
    private LocalDateTime validtimeEnd;

    /**
     * 更新时间
     */
    private LocalDateTime updateDate;

    /**
     * 备注
     */
    private String remarks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChooseCourseId() {
        return chooseCourseId;
    }

    public void setChooseCourseId(Long chooseCourseId) {
        this.chooseCourseId = chooseCourseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getValidtimeStart() {
        return validtimeStart;
    }

    public void setValidtimeStart(LocalDateTime validtimeStart) {
        this.validtimeStart = validtimeStart;
    }

    public LocalDateTime getValidtimeEnd() {
        return validtimeEnd;
    }

    public void setValidtimeEnd(LocalDateTime validtimeEnd) {
        this.validtimeEnd = validtimeEnd;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "CourseTables{" +
            "id = " + id +
            ", chooseCourseId = " + chooseCourseId +
            ", userId = " + userId +
            ", courseId = " + courseId +
            ", companyId = " + companyId +
            ", courseName = " + courseName +
            ", courseType = " + courseType +
            ", createDate = " + createDate +
            ", validtimeStart = " + validtimeStart +
            ", validtimeEnd = " + validtimeEnd +
            ", updateDate = " + updateDate +
            ", remarks = " + remarks +
        "}";
    }
}
