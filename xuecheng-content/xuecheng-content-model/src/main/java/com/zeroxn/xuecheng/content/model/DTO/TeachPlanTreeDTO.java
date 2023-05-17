package com.zeroxn.xuecheng.content.model.DTO;

import com.zeroxn.xuecheng.content.model.pojo.Teachplan;
import com.zeroxn.xuecheng.content.model.pojo.TeachplanMedia;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023/5/15 下午5:19
 * @Description: 课程计划树形结构DTO类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TeachPlanTreeDTO extends Teachplan {
    /**
     * 课程对应的媒体资源信息
     */
    private TeachplanMedia teachplanMedia;
    /**
     * 课程计划的子章节 树形节点
     */
    private List<TeachPlanTreeDTO> teachPlanTreeNodes;
}
