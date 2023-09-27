package com.zeroxn.xuecheng.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @Author: lisang
 * @DateTime: 2023-09-27 13:11:19
 * @Description:
 */
@Configuration
public class GatewaySecurityConfig {
    @Bean
    SecurityWebFilterChain gatewayExchangeSecurityFilterChain(ServerHttpSecurity http) {
        return http.authorizeExchange(exchanges -> exchanges
                .pathMatchers("/auth/**", "/content/open/**", "/media/open/**").permitAll()
                .anyExchange().authenticated())
                .csrf().disable()
                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt).build();
    }
}
