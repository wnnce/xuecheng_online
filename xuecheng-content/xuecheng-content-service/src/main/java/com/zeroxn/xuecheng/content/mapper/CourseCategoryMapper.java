package com.zeroxn.xuecheng.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zeroxn.xuecheng.content.model.DTO.CourseCategoryTreeDTO;
import com.zeroxn.xuecheng.content.model.pojo.CourseCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023/5/11 下午9:31
 * @Description:
 */
@Mapper
public interface CourseCategoryMapper extends BaseMapper<CourseCategory> {
    List<CourseCategoryTreeDTO> queryCourseCategoryTree(String id);
}
