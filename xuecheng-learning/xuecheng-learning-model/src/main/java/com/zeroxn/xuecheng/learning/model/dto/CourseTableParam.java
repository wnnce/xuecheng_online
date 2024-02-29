package com.zeroxn.xuecheng.learning.model.dto;

import lombok.*;

/**
 * @Author: lisang
 * @DateTime: 2024-02-29 20:58:36
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CourseTableParam {
    private String userId;
    private String courseType;
    private String sortType;
    private String expiresType;
    private int page = 1;
    private int startIndex;
    private int size = 4;
}
