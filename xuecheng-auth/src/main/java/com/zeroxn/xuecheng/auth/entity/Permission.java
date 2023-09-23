package com.zeroxn.xuecheng.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author lisang
 * @since 2023-09-22
 */
@TableName("xc_permission")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String roleId;

    private String menuId;

    private LocalDateTime createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Permission{" +
            "id = " + id +
            ", roleId = " + roleId +
            ", menuId = " + menuId +
            ", createTime = " + createTime +
        "}";
    }
}
