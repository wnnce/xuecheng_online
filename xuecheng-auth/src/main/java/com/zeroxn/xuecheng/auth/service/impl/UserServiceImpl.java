package com.zeroxn.xuecheng.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeroxn.xuecheng.auth.entity.User;
import com.zeroxn.xuecheng.auth.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserMapper userMapper;
    private final ObjectMapper objectMapper;
    public UserServiceImpl(UserMapper userMapper, ObjectMapper objectMapper) {
        this.userMapper = userMapper;
        this.objectMapper = objectMapper;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User findUser = userMapper.selectOne(queryWrapper);
        if (findUser == null) {
            logger.info("查询用户为空，用户名：{}", username);
            return null;
        }
        String password = findUser.getPassword();
        findUser.setPassword(null);
        try{
            String jsonUser = objectMapper.writeValueAsString(findUser);
            return new org.springframework.security.core.userdetails.User(jsonUser, password,
                    List.of(new SimpleGrantedAuthority("user")));
        }catch (JsonProcessingException e) {
            logger.error("序列化用户信息失败，错误消息：{}", e.getMessage());
        }
        return null;
    }
}
