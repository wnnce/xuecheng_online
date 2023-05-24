package com.zeroxn.xuecheng.system.web;

import com.zeroxn.xuecheng.system.model.pojo.Dictionary;
import com.zeroxn.xuecheng.system.service.DictionaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "学成在线系统管理接口")
@RestController
@RequestMapping("/dictionary")
public class DictionaryController {
    private final DictionaryService dictionaryService;
    public DictionaryController(DictionaryService dictionaryService){
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("/all")
    @Operation(summary = "获取所有数据字典信息")
    public List<Dictionary> selectAll(){
        return dictionaryService.list();
    }
    @GetMapping("/{code}")
    @Operation(summary = "通过Code获取指定的数据字典信息")
    @Parameter(name = "code", description = "数据字典代码", required = true)
    public Dictionary selectByCode(@PathVariable("code") String code){
        return dictionaryService.getByCode(code);
    }
}
