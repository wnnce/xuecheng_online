package com.zeroxn.xuecheng.content.service;

import com.zeroxn.xuecheng.content.model.DTO.CourseCategoryTreeDTO;

import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023/5/11 下午9:33
 * @Description:
 */
public interface CourseCategoryService{
    List<CourseCategoryTreeDTO> queryCourseCategoryTree(String id);
}
