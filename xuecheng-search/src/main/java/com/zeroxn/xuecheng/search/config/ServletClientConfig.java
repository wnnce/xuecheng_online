package com.zeroxn.xuecheng.search.config;

import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

import java.time.Duration;

/**
 * @Author: lisang
 * @DateTime: 2023-09-19 20:25:03
 * @Description:
 */
@Configuration
public class ServletClientConfig extends ElasticsearchConfiguration {
    private final ElasticsearchProperties properties;
    public ServletClientConfig(ElasticsearchProperties properties) {
        this.properties = properties;
    }
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration
                .builder()
                .connectedTo(properties.getUris().toArray(new String[0]))
                .withBasicAuth(properties.getUsername(), properties.getPassword())
                .withConnectTimeout(Duration.ofSeconds(5))
                .withSocketTimeout(Duration.ofSeconds(5))
                .build();
    }
}
