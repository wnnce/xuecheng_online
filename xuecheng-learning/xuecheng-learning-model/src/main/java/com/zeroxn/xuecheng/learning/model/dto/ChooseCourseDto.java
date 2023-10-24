package com.zeroxn.xuecheng.learning.model.dto;

import com.zeroxn.xuecheng.learning.model.entity.ChooseCourse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Mr.M
 * @version 1.0
 * @description TODO
 * @date 2022/10/2 16:10
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class ChooseCourseDto extends ChooseCourse {

    //学习资格，[{"code":"702001","desc":"正常学习"},{"code":"702002","desc":"没有选课或选课后没有支付"},{"code":"702003","desc":"已过期需要申请续期或重新支付"}]
    public String learnStatus;

}
