package com.zeroxn.xuecheng.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zeroxn.xuecheng.auth.dto.AuthParamsDto;
import com.zeroxn.xuecheng.auth.dto.UserExtend;
import com.zeroxn.xuecheng.auth.entity.User;
import com.zeroxn.xuecheng.auth.mapper.UserMapper;
import com.zeroxn.xuecheng.auth.service.AuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    public PasswordAuthServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserExtend execute(AuthParamsDto authParams) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, authParams.getUsername());
        User findUser = userMapper.selectOne(queryWrapper);
        if (findUser == null) {
            return null;
        }
        if(!passwordEncoder.matches(authParams.getPassword(), findUser.getPassword())) {
            return null;
        }
        UserExtend userExtend = new UserExtend();
        BeanUtils.copyProperties(findUser, userExtend);
        return userExtend;
    }
}
