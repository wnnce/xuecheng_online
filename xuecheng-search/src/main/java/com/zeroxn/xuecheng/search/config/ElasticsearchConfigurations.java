package com.zeroxn.xuecheng.search.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeroxn.xuecheng.search.clients.DocumentClient;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: lisang
 * @DateTime: 2023-09-20 17:14:16
 * @Description: Elasticsearch配置类
 */
public class ElasticsearchConfigurations {

    @Configuration(
            proxyBeanMethods = false
    )
    @ConditionalOnClass(RestClient.class)
    static class RestClientConfiguration {
        @Bean
        RestClient restClient(ElasticsearchProperties properties) {
            BasicCredentialsProvider provider = new BasicCredentialsProvider();
            provider.setCredentials(
                    AuthScope.ANY, new UsernamePasswordCredentials(properties.getUsername(), properties.getPassword())
            );
            RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(properties.getHost(), properties.getPort()));
            restClientBuilder.setRequestConfigCallback(r -> r
                    .setConnectTimeout(properties.getConnectTimeout())
                    .setSocketTimeout(properties.getSocketTimeout()));
            restClientBuilder.setHttpClientConfigCallback(h -> h.setDefaultCredentialsProvider(provider));
            return restClientBuilder.build();
        }
    }
    @Configuration(
            proxyBeanMethods = false
    )
    @ConditionalOnClass({ElasticsearchTransport.class})
    static class ElasticsearchTransportConfiguration {
        @Bean
        ElasticsearchTransport elasticsearchTransport(RestClient restClient, ObjectMapper objectMapper) {
            return new RestClientTransport(restClient, new JacksonJsonpMapper(objectMapper));
        }
    }
    @Configuration(
            proxyBeanMethods = false
    )
    @ConditionalOnClass({ElasticsearchClient.class})
    static class ElasticsearchClientConfiguration {
        @Bean
        ElasticsearchClient elasticsearchClient(ElasticsearchTransport transport) {
            return new ElasticsearchClient(transport);
        }
    }

    @Configuration(
            proxyBeanMethods = false
    )
    @ConditionalOnClass(DocumentClient.class)
    static class DocumentClientConfiguration {
        @Bean
        DocumentClient documentClient(ElasticsearchClient elasticsearchClient) {
            return new DocumentClient(elasticsearchClient);
        }
    }
}