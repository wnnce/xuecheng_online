package com.zeroxn.xuecheng.auth.dto;

import com.zeroxn.xuecheng.auth.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023-10-03 21:38:50
 * @Description:
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class UserExtend extends User {
    private List<String> permissions = new ArrayList<>();
}
