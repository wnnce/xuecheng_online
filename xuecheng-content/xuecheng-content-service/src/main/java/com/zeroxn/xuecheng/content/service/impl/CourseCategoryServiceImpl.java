package com.zeroxn.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeroxn.xuecheng.content.mapper.CourseCategoryMapper;
import com.zeroxn.xuecheng.content.model.DTO.CourseCategoryTreeDTO;
import com.zeroxn.xuecheng.content.model.pojo.CourseCategory;
import com.zeroxn.xuecheng.content.service.CourseCategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: lisang
 * @DateTime: 2023/5/11 下午9:33
 * @Description:
 */
@Service
public class CourseCategoryServiceImpl extends ServiceImpl<CourseCategoryMapper, CourseCategory> implements CourseCategoryService {
    private final CourseCategoryMapper categoryMapper;
    public CourseCategoryServiceImpl(CourseCategoryMapper categoryMapper){
        this.categoryMapper = categoryMapper;
    }
    @Override
    public List<CourseCategoryTreeDTO> queryCourseCategoryTree(String id) {
        List<CourseCategoryTreeDTO> categoryTreeDTOList = categoryMapper.queryCourseCategoryTree(id);
        Map<String, CourseCategoryTreeDTO> tempMap = categoryTreeDTOList.stream().filter(item -> !item.getId().equals(id))
                .collect(Collectors.toMap(CourseCategoryTreeDTO::getId, Function.identity()));
        List<CourseCategoryTreeDTO> categoryTreeDTOS = new ArrayList<>();
        categoryTreeDTOList.stream().filter(item -> !item.getId().equals(id)).forEach(item -> {
            if(id.equals(item.getParentid())){
                categoryTreeDTOS.add(item);
            }
            CourseCategoryTreeDTO categoryTreeDTO = tempMap.get(item.getParentid());
            if(categoryTreeDTO != null){
                if(categoryTreeDTO.getChildrenTreeNodes() == null){
                    categoryTreeDTO.setChildrenTreeNodes(new ArrayList<>());
                }
                categoryTreeDTO.getChildrenTreeNodes().add(item);
            }
        });
        return categoryTreeDTOS;
    }
}
