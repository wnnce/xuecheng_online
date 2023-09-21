package com.zeroxn.xuecheng.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchClientAutoConfiguration;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientAutoConfiguration;
import org.springframework.boot.autoconfigure.elasticsearch.ReactiveElasticsearchClientAutoConfiguration;

/**
 * @Author: lisang
 * @DateTime: 2023-09-19 19:17:08
 * @Description:
 */
@SpringBootApplication(
        exclude = {
                ElasticsearchRestClientAutoConfiguration.class,
                ElasticsearchClientAutoConfiguration.class,
                ReactiveElasticsearchClientAutoConfiguration.class
        }
)
public class SearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }
}
