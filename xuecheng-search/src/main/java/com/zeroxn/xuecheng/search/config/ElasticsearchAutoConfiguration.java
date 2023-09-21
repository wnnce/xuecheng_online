package com.zeroxn.xuecheng.search.config;

import org.elasticsearch.client.RestClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Author: lisang
 * @DateTime: 2023-09-20 17:13:58
 * @Description: Elasticsearch自动配置类
 */
// 使用 AutoConfiguration注解无法开启Properties类的配置文件绑定
@Configuration
@ConditionalOnClass({RestClientBuilder.class})
@EnableConfigurationProperties(ElasticsearchProperties.class)
@Import({ElasticsearchConfigurations.RestClientConfiguration.class, ElasticsearchConfigurations.ElasticsearchTransportConfiguration.class, ElasticsearchConfigurations.ElasticsearchClientConfiguration.class, ElasticsearchConfigurations.DocumentClientConfiguration.class})
public class ElasticsearchAutoConfiguration {
    public ElasticsearchAutoConfiguration() {
    }
}