package com.zeroxn.xuecheng.auth.controller;

import com.zeroxn.xuecheng.auth.entity.User;
import com.zeroxn.xuecheng.auth.mapper.UserMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lisang
 * @DateTime: 2023-09-22 20:04:24
 * @Description:
 */
@RestController
@Tag(name = "登录接口")
public class LoginController {

    private final UserMapper userMapper;

    private LoginController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    @RequestMapping("/success")
    public String loginSuccess() {
        return "登陆成功";
    }
    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('admin')")
    public User getUser(@PathVariable("id") String id) {
        return userMapper.selectById(id);
    }
    @GetMapping("/role/r1")
    public String testRoleR1() {
        return "r1";
    }
    @GetMapping("/role/r2")
    public String testRoleR2() {
        return "r2";
    }
}
