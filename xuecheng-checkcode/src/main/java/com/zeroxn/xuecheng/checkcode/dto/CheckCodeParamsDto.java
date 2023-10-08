package com.zeroxn.xuecheng.checkcode.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: lisang
 * @DateTime: 2023-10-08 18:11:38
 * @Description:
 */
@Data
public class CheckCodeParamsDto {
    private String checkCodeType;

    private String param1;
    private String param2;
    private String param3;
}
