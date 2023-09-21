package com.zeroxn.xuecheng.search.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * @Author: lisang
 * @DateTime: 2023-09-20 17:16:35
 * @Description:
 */
@Getter
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticsearchProperties {
    /**
     * 地址
     */
    private final String host;
    /**
     * 端口
     */
    private final Integer port;
    /**
     * 用户名
     */
    private final String username;
    /**
     * 密码
     */
    private final String password;
    /**
     * 连接超时时间
     */
    private final Integer connectTimeout;
    /**
     * socket超时时间
     */
    private final Integer socketTimeout;

    @ConstructorBinding
    public ElasticsearchProperties(@DefaultValue("localhost") String host, @DefaultValue("9200") Integer port,
                                   @DefaultValue("elastic") String username, String password, @DefaultValue("5000") Integer connectTimeout,
                                   @DefaultValue("300000") Integer socketTimeout) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.connectTimeout = connectTimeout;
        this.socketTimeout = socketTimeout;
    }
}
