package com.zeroxn.xuecheng.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zeroxn.xuecheng.auth.client.CheckCodeClient;
import com.zeroxn.xuecheng.auth.dto.AuthParamsDto;
import com.zeroxn.xuecheng.auth.dto.UserExtend;
import com.zeroxn.xuecheng.auth.entity.User;
import com.zeroxn.xuecheng.auth.mapper.UserMapper;
import com.zeroxn.xuecheng.auth.service.AuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;

/**
 * @Author: lisang
 * @DateTime: 2023-10-03 21:58:29
 * @Description:
 */
@Service("PasswordAuthService")
public class PasswordAuthServiceImpl implements AuthService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final CheckCodeClient checkCodeClient;

    public PasswordAuthServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder, CheckCodeClient checkCodeClient) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.checkCodeClient = checkCodeClient;
    }
    @Override
    public UserExtend execute(AuthParamsDto authParams) {

        if (StringUtils.isEmpty(authParams.getCheckcodekey()) || StringUtils.isEmpty(authParams.getCheckcode())) {
            throw new OAuth2AuthenticationException("验证码不能为空");
        }
        // 校验验证码
        boolean validation = checkCodeClient.validation(authParams.getCheckcodekey(), authParams.getCheckcode());
        if (!validation) {
            throw new OAuth2AuthenticationException("验证码错误");
        }

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, authParams.getUsername());
        User findUser = userMapper.selectOne(queryWrapper);
        if (findUser == null) {
            throw new OAuth2AuthenticationException("用户不存在");
        }
        if(!passwordEncoder.matches(authParams.getPassword(), findUser.getPassword())) {
            throw new OAuth2AuthenticationException("用户名或密码错误");
        }
        UserExtend userExtend = new UserExtend();
        BeanUtils.copyProperties(findUser, userExtend);
        return userExtend;
    }
}
