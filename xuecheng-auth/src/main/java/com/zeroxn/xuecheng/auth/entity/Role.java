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
@TableName("xc_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String roleName;

    private String roleCode;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Role{" +
            "id = " + id +
            ", roleName = " + roleName +
            ", roleCode = " + roleCode +
            ", description = " + description +
            ", createTime = " + createTime +
            ", updateTime = " + updateTime +
            ", status = " + status +
        "}";
    }
}
