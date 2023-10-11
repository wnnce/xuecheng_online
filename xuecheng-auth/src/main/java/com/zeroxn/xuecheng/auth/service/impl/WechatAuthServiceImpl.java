package com.zeroxn.xuecheng.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zeroxn.xuecheng.auth.dto.AuthParamsDto;
import com.zeroxn.xuecheng.auth.dto.UserExtend;
import com.zeroxn.xuecheng.auth.entity.User;
import com.zeroxn.xuecheng.auth.entity.UserRole;
import com.zeroxn.xuecheng.auth.mapper.UserMapper;
import com.zeroxn.xuecheng.auth.mapper.UserRoleMapper;
import com.zeroxn.xuecheng.auth.service.AuthService;
import com.zeroxn.xuecheng.auth.service.WechatAuthService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.http.*;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * @Author: lisang
 * @DateTime: 2023-10-03 21:59:10
 * @Description:
 */
@Service("WechatAuthService")
public class WechatAuthServiceImpl implements AuthService, WechatAuthService {
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    private static final String USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo";
    private static final String APPID = "";
    private static final String SECRET = "";
    private static final String GRANT_TYPE = "authorization_code";
    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final RestTemplate restTemplate;
    public WechatAuthServiceImpl(UserMapper userMapper, UserRoleMapper userRoleMapper, RestTemplate restTemplate) {
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
        this.restTemplate = restTemplate;
    }
    @Override
    public UserExtend execute(AuthParamsDto authParams) {
        String username = authParams.getUsername();
        User findUser = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        if (findUser == null) {
            throw new OAuth2AuthenticationException("用户不存在");
        }
        UserExtend userExtend = new UserExtend();
        BeanUtils.copyProperties(findUser, userExtend);
        return userExtend;
    }

    @Override
    public User wechatAuth(String code) {
        AccessTokenResult result = getAccessToken(code);
        WechatUserInfo userInfo = getUserInfo(result.access_token, result.openid);
        WechatAuthServiceImpl self = (WechatAuthServiceImpl) AopContext.currentProxy();
        return self.saveUser(userInfo);
    }

    @Transactional
    public User saveUser(WechatUserInfo userInfo) {
        String unionId = userInfo.unionid();
        User findUser = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getWxUnionid, unionId));
        if (findUser == null) {
            String userId = UUID.randomUUID().toString();
            findUser = new User(userId, unionId, unionId, unionId, userInfo.nickname, userInfo.nickname, userInfo.headimgurl, "101001", "1", LocalDateTime.now());
            userMapper.insert(findUser);
            UserRole userRole = UserRole.builder()
                    .id(UUID.randomUUID().toString())
                    .userId(userId)
                    .roleId("17")
                    .createTime(LocalDateTime.now())
                    .build();
            userRoleMapper.insert(userRole);
        }
        return findUser;
    }
    private AccessTokenResult getAccessToken(String code) {
        String requestUrl = UriComponentsBuilder.fromHttpUrl(ACCESS_TOKEN_URL)
                .queryParam("appid", APPID)
                .queryParam("secret", SECRET)
                .queryParam("code", code)
                .queryParam("grant_type", GRANT_TYPE)
                .toUriString();
        ResponseEntity<AccessTokenResult> responseEntity = restTemplate.getForEntity(requestUrl, AccessTokenResult.class);
        return responseEntity.getBody();
    }

    private WechatUserInfo getUserInfo(String accessToken, String openId) {
        String requestUrl = UriComponentsBuilder.fromHttpUrl(USERINFO_URL)
                .queryParam("access_token", accessToken)
                .queryParam("openid", openId)
                .queryParam("lang", "zh_CN")
                .toUriString();
        ResponseEntity<WechatUserInfo> response = restTemplate.getForEntity(requestUrl, WechatUserInfo.class);
        return response.getBody();
    }
    public record AccessTokenResult(String access_token, Integer expires_in, String refresh_token, String openid, String scope,
                             String unionid) {}
    public record WechatUserInfo(String openid, String nickname, Integer sex, String province, String city, String country,
                          String headimgurl, String[] privilege, String unionid) {}
}