package com.zeroxn.xuecheng.content.service.async;

import com.zeroxn.xuecheng.content.model.DTO.CourseBaseInfoDTO;
import com.zeroxn.xuecheng.content.model.DTO.TeachPlanTreeDTO;
import com.zeroxn.xuecheng.content.model.pojo.CourseTeacher;
import com.zeroxn.xuecheng.content.service.CourseBaseService;
import com.zeroxn.xuecheng.content.service.CourseTeacherService;
import com.zeroxn.xuecheng.content.service.TeachPlanService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @Author: lisang
 * @DateTime: 2023/6/15 下午6:06
 * @Description:
 */
@Component
public class CoursePreviewAsyncTask {
    private final CourseBaseService courseBaseService;
    private final TeachPlanService teachPlanService;
    private final CourseTeacherService teacherService;
    public CoursePreviewAsyncTask(CourseBaseService courseBaseService, TeachPlanService teachPlanService, CourseTeacherService teacherService){
        this.courseBaseService = courseBaseService;
        this.teachPlanService = teachPlanService;
        this.teacherService = teacherService;
    }

    /**
     * 异步线程通过课程ID获取课程信息
     * @param courseId 课程ID
     * @return 返回课程详细信息或空
     */
    @Async
    public CompletableFuture<CourseBaseInfoDTO> queryCourseInfoByCourseId(Long courseId){
        CourseBaseInfoDTO courseBase = courseBaseService.queryCourseBaseInfoById(courseId);
        return CompletableFuture.completedFuture(courseBase != null ? courseBase : new CourseBaseInfoDTO());
    }

    /**
     * 异步线程通过课程ID获取课程目录的属性信息
     * @param courseId 课程ID
     * @return 返回课程目录的树形信息或空
     */
    @Async
    public CompletableFuture<List<TeachPlanTreeDTO>> listTeachPlanTreeByCourseId(Long courseId){
        List<TeachPlanTreeDTO> teachPlanList = teachPlanService.queryTeachPlanTree(courseId);
        return CompletableFuture.completedFuture(teachPlanList != null ? teachPlanList : new ArrayList<>());
    }

    /**
     * 异步线程通过课程ID获取课程的教师列表
     * @param courseId 课程ID
     * @return 返回课程的教师列表或空
     */
    @Async
    public CompletableFuture<List<CourseTeacher>> listCourseTeacherByCourseId(Long courseId){
        List<CourseTeacher> teacherList = teacherService.listCourseTeacherByCourseId(courseId);
        return CompletableFuture.completedFuture(teacherList != null ? teacherList : new ArrayList<>());
    }

}
