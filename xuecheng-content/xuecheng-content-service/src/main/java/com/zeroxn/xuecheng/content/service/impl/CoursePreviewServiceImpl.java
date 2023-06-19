package com.zeroxn.xuecheng.content.service.impl;

import com.zeroxn.xuecheng.content.model.DTO.CourseBaseInfoDTO;
import com.zeroxn.xuecheng.content.model.DTO.CoursePreviewDTO;
import com.zeroxn.xuecheng.content.model.DTO.TeachPlanTreeDTO;
import com.zeroxn.xuecheng.content.model.pojo.CourseTeacher;
import com.zeroxn.xuecheng.content.service.CoursePreviewService;
import com.zeroxn.xuecheng.content.service.async.CourseAsyncTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @Author: lisang
 * @DateTime: 2023/6/14 下午7:17
 * @Description:
 */
@Service
public class CoursePreviewServiceImpl implements CoursePreviewService {
    private static final Logger logger = LoggerFactory.getLogger(CoursePreviewServiceImpl.class);
    private final CourseAsyncTask coursePreviewAsyncTask;
    public CoursePreviewServiceImpl(CourseAsyncTask coursePreviewAsyncTask){
        this.coursePreviewAsyncTask = coursePreviewAsyncTask;
    }
    @Override
    public CoursePreviewDTO queryCoursePreview(Long courseId) {
        CompletableFuture<CourseBaseInfoDTO> courseBase = coursePreviewAsyncTask.queryCourseInfoByCourseId(courseId);
        CompletableFuture<List<TeachPlanTreeDTO>> teachPlanList = coursePreviewAsyncTask.listTeachPlanTreeByCourseId(courseId);
        CompletableFuture<List<CourseTeacher>> courseTeacherList = coursePreviewAsyncTask.listCourseTeacherByCourseId(courseId);
        // 等待3个线程都执行完毕
        CompletableFuture.allOf(courseBase, teachPlanList, courseTeacherList).join();
        return new CoursePreviewDTO(courseBase.join(), teachPlanList.join(), courseTeacherList.join());
    }
}
