package com.zeroxn.xuecheng.content.service.async;

import com.zeroxn.xuecheng.content.model.DTO.CourseBaseInfoDTO;
import com.zeroxn.xuecheng.content.model.DTO.TeachPlanTreeDTO;
import com.zeroxn.xuecheng.content.model.pojo.CourseCategory;
import com.zeroxn.xuecheng.content.model.pojo.CourseMarket;
import com.zeroxn.xuecheng.content.model.pojo.CourseTeacher;
import com.zeroxn.xuecheng.content.service.*;
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
@Async
public class CourseAsyncTask {
    private final CourseBaseService courseBaseService;
    private final TeachPlanService teachPlanService;
    private final CourseTeacherService teacherService;
    private final CourseCategoryService categoryService;
    private final CourseMarketService marketService;
    public CourseAsyncTask(CourseBaseService courseBaseService, TeachPlanService teachPlanService,
                           CourseTeacherService teacherService, CourseCategoryService categoryService,
                           CourseMarketService marketService){
        this.courseBaseService = courseBaseService;
        this.teachPlanService = teachPlanService;
        this.teacherService = teacherService;
        this.categoryService = categoryService;
        this.marketService = marketService;
    }

    /**
     * 异步线程通过课程ID获取课程信息
     * @param courseId 课程ID
     * @return 返回课程详细信息或空
     */
    public CompletableFuture<CourseBaseInfoDTO> queryCourseInfoByCourseId(Long courseId){
        CourseBaseInfoDTO courseBase = courseBaseService.queryCourseBaseInfoById(courseId);
        return CompletableFuture.completedFuture(courseBase != null ? courseBase : new CourseBaseInfoDTO());
    }

    /**
     * 异步线程通过课程ID获取课程目录的属性信息
     * @param courseId 课程ID
     * @return 返回课程目录的树形信息或空
     */
    public CompletableFuture<List<TeachPlanTreeDTO>> listTeachPlanTreeByCourseId(Long courseId){
        List<TeachPlanTreeDTO> teachPlanList = teachPlanService.queryTeachPlanTree(courseId);
        return CompletableFuture.completedFuture(teachPlanList != null ? teachPlanList : new ArrayList<>());
    }

    /**
     * 异步线程通过课程ID获取课程的教师列表
     * @param courseId 课程ID
     * @return 返回课程的教师列表或空
     */
    public CompletableFuture<List<CourseTeacher>> listCourseTeacherByCourseId(Long courseId){
        List<CourseTeacher> teacherList = teacherService.listCourseTeacherByCourseId(courseId);
        return CompletableFuture.completedFuture(teacherList != null ? teacherList : new ArrayList<>());
    }

    /**
     * 异步线程通过分类ID获取课程的分类信息
     * @param id 分类ID
     * @return 返回分类信息或空
     */
    public CompletableFuture<CourseCategory> queryCourseCategoryById(String id){
        return CompletableFuture.completedFuture(categoryService.getById(id));
    }

    /**
     * 异步线程通过课程ID获取课程的营销信息
     * @param courseId 课程ID
     * @return 返回课程营销信息或空
     */
    public CompletableFuture<CourseMarket> queryCourseMarketByCourseId(Long courseId){
        return CompletableFuture.completedFuture(marketService.getById(courseId));
    }
}
