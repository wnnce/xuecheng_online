package com.zeroxn.xuecheng.system.web;

import com.zeroxn.xuecheng.system.model.pojo.Dictionary;
import com.zeroxn.xuecheng.system.service.DictionaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023/5/11 下午5:16
 * @Description:
 */
@RestController
@RequestMapping("/dictionary")
public class DictionaryController {
    private final DictionaryService dictionaryService;
    public DictionaryController(DictionaryService dictionaryService){
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("/all")
    public List<Dictionary> selectAll(){
        return dictionaryService.list();
    }
    @GetMapping("/{code}")
    public Dictionary selectByCode(@PathVariable("code") String code){
        return dictionaryService.getByCode(code);
    }
}
