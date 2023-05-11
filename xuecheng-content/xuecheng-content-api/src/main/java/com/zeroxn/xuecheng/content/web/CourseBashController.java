package com.zeroxn.xuecheng.content.web;

import com.zeroxn.xuecheng.bash.model.PageParams;
import com.zeroxn.xuecheng.bash.model.PageResult;
import com.zeroxn.xuecheng.content.model.DTO.QueryCourseParamsDTO;
import com.zeroxn.xuecheng.content.model.pojo.CourseBase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lisang
 * @DateTime: 2023/5/10 下午9:59
 * @Description:
 */
@RestController
public class CourseBashController {
    @PostMapping()
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false)QueryCourseParamsDTO paramsDTO){

        return null;
    }
}
