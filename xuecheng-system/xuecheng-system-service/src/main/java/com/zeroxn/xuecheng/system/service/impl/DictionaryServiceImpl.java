package com.zeroxn.xuecheng.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeroxn.xuecheng.system.mapper.DictionaryMapper;
import com.zeroxn.xuecheng.system.model.pojo.Dictionary;
import com.zeroxn.xuecheng.system.service.DictionaryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023/5/11 下午5:09
 * @Description:
 */
@Service
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements DictionaryService {
    private final DictionaryMapper dictionaryMapper;
    public DictionaryServiceImpl(DictionaryMapper dictionaryMapper){
        this.dictionaryMapper = dictionaryMapper;
    }
    @Override
    public Dictionary getByCode(String code) {
        LambdaQueryWrapper<Dictionary> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dictionary::getCode, code);
        return dictionaryMapper.selectOne(queryWrapper);
    }
}
