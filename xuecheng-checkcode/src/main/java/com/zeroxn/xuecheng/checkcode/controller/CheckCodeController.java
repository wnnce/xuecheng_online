package com.zeroxn.xuecheng.checkcode.controller;

import com.zeroxn.xuecheng.checkcode.dto.CheckCodeParamsDto;
import com.zeroxn.xuecheng.checkcode.dto.CheckCodeResultDto;
import com.zeroxn.xuecheng.checkcode.service.CheckCodeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lisang
 * @DateTime: 2023-10-08 18:09:44
 * @Description:
 */
@Tag(name = "验证码接口")
@RestController
public class CheckCodeController {
    private final CheckCodeService checkCodeService;

    public CheckCodeController(CheckCodeService checkCodeService) {
        this.checkCodeService = checkCodeService;
    }

    @PostMapping("/pic")
    public CheckCodeResultDto generateCheckCode(CheckCodeParamsDto paramsDto) {
        return checkCodeService.generate(paramsDto);
    }

    @PostMapping("/verify")
    public boolean validation(String key, String code) {
        return checkCodeService.validation(key, code);
    }
}
