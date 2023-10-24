package com.zeroxn.xuecheng.content.utils;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("xc_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String username;

    private String password;

    private String salt;

    /**
     * 微信unionid
     */
    private String wxUnionid;

    /**
     * 昵称
     */
    private String nickname;

    private String name;

    /**
     * 头像
     */
    private String userpic;

    private Long companyId;

    private String utype;

    private LocalDateTime birthday;

    private String sex;

    private String email;

    private String cellphone;

    private String qq;

    /**
     * 用户状态
     */
    private String status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public User(String id, String username, String password, String wxUnionid, String nickname, String name, String userpic, String utype, String status, LocalDateTime createTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.wxUnionid = wxUnionid;
        this.nickname = nickname;
        this.name = name;
        this.userpic = userpic;
        this.utype = utype;
        this.status = status;
        this.createTime = createTime;
    }
}
