package com.zeroxn.xuecheng.auth.controller;

import com.zeroxn.xuecheng.auth.entity.User;
import com.zeroxn.xuecheng.auth.mapper.UserMapper;
import com.zeroxn.xuecheng.auth.service.WechatAuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lisang
 * @DateTime: 2023-09-22 20:04:24
 * @Description:
 */
@Controller
@Tag(name = "登录接口")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final WechatAuthService wechatAuthService;
    public LoginController(WechatAuthService wechatAuthService) {
        this.wechatAuthService = wechatAuthService;
    }
    @RequestMapping("/wxLogin")
    public String wechatLogin(String code, String state) {
        logger.info("接收到微信回调参数，code：{}，state：{}", code, state);
        User user = wechatAuthService.wechatAuth(code);
        if (user == null) {
            return "redirect:http://xuecheng/ml/error.html";
        }
        return "redirect:http://www.xuecheng.ml/sign.html?username=" + user.getUsername() + "&authType=wx";
    }
}
