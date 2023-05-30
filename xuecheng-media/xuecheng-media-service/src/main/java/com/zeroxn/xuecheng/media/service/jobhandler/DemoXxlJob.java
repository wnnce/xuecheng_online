package com.zeroxn.xuecheng.media.service.jobhandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: lisang
 * @DateTime: 2023/5/30 下午8:49
 * @Description: 测试xxl-job任务管理
 */

@Component
public class DemoXxlJob {
    private static final Logger logger = LoggerFactory.getLogger(DemoXxlJob.class);
    @XxlJob("testXxlJob")
    public void testXxlJobHandler(){
        DemoXxlJob.logger.info("任务被执行了...");
    }
}
