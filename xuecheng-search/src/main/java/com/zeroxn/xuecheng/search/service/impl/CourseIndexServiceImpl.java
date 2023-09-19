package com.zeroxn.xuecheng.search.service.impl;

import com.zeroxn.xuecheng.base.exception.CustomException;
import com.zeroxn.xuecheng.search.entity.CourseIndex;
import com.zeroxn.xuecheng.search.service.CourseIndexService;
import org.springframework.data.elasticsearch.core.DocumentOperations;
import org.springframework.data.elasticsearch.core.query.UpdateResponse;
import org.springframework.stereotype.Service;

/**
 * @Author: lisang
 * @DateTime: 2023-09-19 20:45:50
 * @Description:
 */
@Service
public class CourseIndexServiceImpl implements CourseIndexService {

    private final DocumentOperations documentOperations;

    public CourseIndexServiceImpl(DocumentOperations documentOperations) {
        this.documentOperations = documentOperations;
    }

    @Override
    public boolean addCourseIndex(CourseIndex courseIndex) {
        if (courseIndex.getId() == null) {
            throw new CustomException("课程Id为空");
        }
        CourseIndex result = documentOperations.save(courseIndex);
        return result.getId() != null;
    }

    @Override
    public boolean updateCourseIndex(CourseIndex courseIndex) {
        if (courseIndex.getId() == null) {
            throw new CustomException("课程Id为空");
        }
        UpdateResponse response = documentOperations.update(courseIndex);
        System.out.println(response);
        return true;
    }

    @Override
    public boolean deleteCourseIndex(String id) {
        String delete = documentOperations.delete(id, CourseIndex.class);
        System.out.println(delete);
        return true;
    }
}
