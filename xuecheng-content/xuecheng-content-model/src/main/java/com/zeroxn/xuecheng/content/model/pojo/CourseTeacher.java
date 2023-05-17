package com.zeroxn.xuecheng.content.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 课程-教师关系表
 * </p>
 *
 * @author lisang
 * @since 2023-05-10
 */
@TableName("course_teacher")
@Schema(description = "课程教师数据实体类")
public class CourseTeacher implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程标识
     */
    @NotNull(message = "课程id能为空")
    @Schema(description = "课程id")
    private Long courseId;

    /**
     * 教师标识
     */
    @NotEmpty(message = "教师姓名不能为空")
    @Schema(description = "教师姓名")
    private String teacherName;

    /**
     * 教师职位
     */
    @NotEmpty(message = "教师职位不能为空")
    @Schema(description = "教师职位")
    private String position;

    /**
     * 教师简介
     */
    @Schema(description = "教师简介")
    private String introduction;

    /**
     * 照片
     */
    @Schema(description = "教师照片地址")
    private String photograph;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间，后端生成")
    private LocalDateTime createDate;

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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPhotograph() {
        return photograph;
    }

    public void setPhotograph(String photograph) {
        this.photograph = photograph;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "CourseTeacher{" +
            "id = " + id +
            ", courseId = " + courseId +
            ", teacherName = " + teacherName +
            ", position = " + position +
            ", introduction = " + introduction +
            ", photograph = " + photograph +
            ", createDate = " + createDate +
        "}";
    }
}
