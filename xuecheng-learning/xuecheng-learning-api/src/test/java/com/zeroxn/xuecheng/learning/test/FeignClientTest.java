package com.zeroxn.xuecheng.learning.test;

import com.zeroxn.xuecheng.learning.client.ContentClient;
import com.zeroxn.xuecheng.learning.client.MediaClient;
import com.zeroxn.xuecheng.learning.model.dto.RestResponse;
import com.zeroxn.xuecheng.learning.model.entity.CoursePublish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: lisang
 * @DateTime: 2023-10-26 20:14:32
 * @Description:
 */
@SpringBootTest
public class FeignClientTest {
    @Autowired
    ContentClient contentClient;
    @Autowired
    MediaClient mediaClient;
    @Test
    public void testContentClient() {
        CoursePublish coursePublish = contentClient.queryCoursePublish(1L);
        System.out.println(coursePublish);
    }
}
