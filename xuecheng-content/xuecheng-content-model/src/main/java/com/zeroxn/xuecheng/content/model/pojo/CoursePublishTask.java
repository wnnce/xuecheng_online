package com.zeroxn.xuecheng.content.model.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: lisang
 * @DateTime: 2023-09-18 20:19:39
 * @Description:
 */
@TableName(value = "course_publish_task")
public class CoursePublishTask implements Serializable {

    @TableId(value = "id")
    private Long id;

    private LocalDateTime createTime;
    private LocalDateTime executeTime;
    private Integer executeCount;
    private Integer task1Status;
    private Integer task2Status;
    private Integer task3Status;

    public CoursePublishTask() {}

    public CoursePublishTask(Long id, LocalDateTime executeTime) {
        this.id = id;
        this.executeTime = executeTime;
    }

    public CoursePublishTask(Long id, LocalDateTime createTime, LocalDateTime executeTime, Integer executeCount, Integer task1Status, Integer task2Status, Integer task3Status) {
        this.id = id;
        this.createTime = createTime;
        this.executeTime = executeTime;
        this.executeCount = executeCount;
        this.task1Status = task1Status;
        this.task2Status = task2Status;
        this.task3Status = task3Status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(LocalDateTime executeTime) {
        this.executeTime = executeTime;
    }

    public Integer getExecuteCount() {
        return executeCount;
    }

    public void setExecuteCount(Integer executeCount) {
        this.executeCount = executeCount;
    }

    public Integer getTask1Status() {
        return task1Status;
    }

    public void setTask1Status(Integer task1Status) {
        this.task1Status = task1Status;
    }

    public Integer getTask2Status() {
        return task2Status;
    }

    public void setTask2Status(Integer task2Status) {
        this.task2Status = task2Status;
    }

    public Integer getTask3Status() {
        return task3Status;
    }

    public void setTask3Status(Integer task3Status) {
        this.task3Status = task3Status;
    }

    @Override
    public String toString() {
        return "CoursePublishTask{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", executeTime=" + executeTime +
                ", executeCount=" + executeCount +
                ", task1Status=" + task1Status +
                ", task2Status=" + task2Status +
                ", task3Status=" + task3Status +
                '}';
    }
}
