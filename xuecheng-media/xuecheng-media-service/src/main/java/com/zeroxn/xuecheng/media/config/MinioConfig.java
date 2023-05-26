package com.zeroxn.xuecheng.media.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: lisang
 * @DateTime: 2023/5/25 下午8:22
 * @Description:
 */
@Configuration
@ConfigurationProperties(value = "spring.minio")
@Data
public class MinioConfig {
    /**
     * minio地址
     */
    private String endpoint = "localhost:9000";
    /**
     * minio 用户名
     */
    private String username = "minioadmin";
    /**
     * minio 密码
     */
    private String password = "minioadmin";
    /**
     * 上传普通文件的存储桶
     */
    private String fileBucket;
    /**
     * 上传视频文件的存储桶
     */
    private String videoBucket;

    @Bean
    public MinioClient minioClient(){
        return new MinioClient.Builder()
                .endpoint(endpoint)
                .credentials(username, password)
                .build();
    }

}
