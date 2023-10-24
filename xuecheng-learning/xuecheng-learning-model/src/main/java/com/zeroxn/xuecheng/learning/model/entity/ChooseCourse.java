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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Double getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(Double coursePrice) {
        this.coursePrice = coursePrice;
    }

    public Integer getValidDays() {
        return validDays;
    }

    public void setValidDays(Integer validDays) {
        this.validDays = validDays;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "ChooseCourse{" +
            "id = " + id +
            ", courseId = " + courseId +
            ", courseName = " + courseName +
            ", userId = " + userId +
            ", companyId = " + companyId +
            ", orderType = " + orderType +
            ", createDate = " + createDate +
            ", coursePrice = " + coursePrice +
            ", validDays = " + validDays +
            ", status = " + status +
            ", validtimeStart = " + validtimeStart +
            ", validtimeEnd = " + validtimeEnd +
            ", remarks = " + remarks +
        "}";
    }
}
