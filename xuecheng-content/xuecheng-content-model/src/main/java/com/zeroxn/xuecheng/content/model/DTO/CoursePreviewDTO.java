package com.zeroxn.xuecheng.content.model.DTO;

import com.zeroxn.xuecheng.content.model.pojo.CourseTeacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023/6/14 下午7:14
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursePreviewDTO {
    /**
     * 课程基本信息
     */
    private CourseBaseInfoDTO courseBase;
    /**
     * 课程计划
     */
    private List<TeachPlanTreeDTO> teachPlanList;
    /**
     * 课程的教师信息
     */
    private List<CourseTeacher> teacherList;
}
