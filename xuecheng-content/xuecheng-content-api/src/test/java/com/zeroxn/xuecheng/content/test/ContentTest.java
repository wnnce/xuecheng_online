package com.zeroxn.xuecheng.content.test;

import com.zeroxn.xuecheng.base.model.PageParams;
import com.zeroxn.xuecheng.base.utils.CommonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: lisang
 * @DateTime: 2023/5/12 下午6:05
 * @Description:
 */
@SpringBootTest
public class ContentTest {

    @Test
    public void textFun(){
        PageParams params = new PageParams();
        params.setPageNo(1L);
        params.setPageSize(2L);
        boolean bl = CommonUtils.checkObjectFieldIsEmpty(params, "pageNo", "pageSize");
        System.out.println(bl);
    }
}
