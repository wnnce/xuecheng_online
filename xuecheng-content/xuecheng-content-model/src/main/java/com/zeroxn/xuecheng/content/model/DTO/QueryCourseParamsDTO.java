package com.zeroxn.xuecheng.content.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: lisang
 * @DateTime: 2023/5/10 下午9:49
 * @Description: 查询课程 查询参数封装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryCourseParamsDTO {
    private String auditStatus;
    private String courseName;
    private String publishStatus;
}
