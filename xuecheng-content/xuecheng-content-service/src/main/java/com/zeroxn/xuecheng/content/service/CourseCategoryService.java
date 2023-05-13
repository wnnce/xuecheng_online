package com.zeroxn.xuecheng.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeroxn.xuecheng.content.model.DTO.CourseCategoryTreeDTO;
import com.zeroxn.xuecheng.content.model.pojo.CourseCategory;

import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023/5/11 下午9:33
 * @Description:
 */
public interface CourseCategoryService extends IService<CourseCategory>{
    List<CourseCategoryTreeDTO> queryCourseCategoryTree(String id);
}
