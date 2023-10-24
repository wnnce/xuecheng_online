package com.zeroxn.xuecheng.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zeroxn.xuecheng.auth.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lisang
 * @since 2023-09-22
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    @Select("select m1.* from xc_menu m1 where m1.id in (select p1.menu_id from xc_permission as p1 where p1.role_id in (select r1.role_id from xc_user_role as r1 where r1.user_id = #{userId}))")
    List<Menu> listUserAuthorities(@Param("userId") String userId);
}
