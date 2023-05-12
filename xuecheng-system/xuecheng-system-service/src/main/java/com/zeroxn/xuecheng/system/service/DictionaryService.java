package com.zeroxn.xuecheng.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zeroxn.xuecheng.system.model.pojo.Dictionary;

import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023/5/11 下午5:07
 * @Description:
 */
public interface DictionaryService extends IService<Dictionary> {
    Dictionary getByCode(String code);
}
