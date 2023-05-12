package com.zeroxn.xuecheng.content.model.DTO;

import com.zeroxn.xuecheng.content.model.pojo.CourseCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023/5/11 下午9:26
 * @Description: CourseCategory树形结构DTO类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseCategoryTreeDTO extends CourseCategory {
    private List<CourseCategoryTreeDTO> childrenTreeNodes;
}
