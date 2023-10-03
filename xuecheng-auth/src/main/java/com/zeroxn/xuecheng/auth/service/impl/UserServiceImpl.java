package com.zeroxn.xuecheng.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zeroxn.xuecheng.auth.entity.User;
import com.zeroxn.xuecheng.auth.mapper.UserMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023-10-03 11:31:22
 * @Description:
 */
@Service
public class UserServiceImpl implements UserDetailsService {
    private final UserMapper userMapper;
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User findUser = userMapper.selectOne(queryWrapper);
        if (findUser == null) {
            return null;
        }
        return new org.springframework.security.core.userdetails.User(findUser.getUsername(), findUser.getPassword(),
                List.of(new SimpleGrantedAuthority("user")));
    }
}
