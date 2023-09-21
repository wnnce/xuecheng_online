package com.zeroxn.xuecheng.search.service.impl;

import com.zeroxn.xuecheng.base.exception.CustomException;
import com.zeroxn.xuecheng.search.clients.DocumentClient;
import com.zeroxn.xuecheng.search.entity.CourseIndex;
import com.zeroxn.xuecheng.search.service.CourseIndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Author: lisang
 * @DateTime: 2023-09-19 20:45:50
 * @Description:
 */
@Service
public class CourseIndexServiceImpl implements CourseIndexService {
    private static final Logger logger = LoggerFactory.getLogger(CourseIndexServiceImpl.class);
    private final DocumentClient documentClient;

    public CourseIndexServiceImpl(DocumentClient documentClient) {
        this.documentClient = documentClient;
    }
    @Override
    public boolean addCourseIndex(CourseIndex courseIndex) {
        if (courseIndex.getId() == null) {
            logger.error("添加课程索引ID不能为空");
            throw new CustomException("课程索引ID为空");
        }
        String result = documentClient.save(courseIndex);
        return result != null;
    }

    @Override
    public boolean updateCourseIndex(CourseIndex courseIndex) {
        if (courseIndex.getId() == null){
            logger.error("更新课程索引ID不能为空");
            throw new CustomException("课程索引ID为空");
        }
        String result = documentClient.update(courseIndex);
        return result != null;
    }

    @Override
    public boolean deleteCourseIndex(Long id) {
        String result = documentClient.delete(id.toString(), CourseIndex.class);
        return result != null;
    }
}
