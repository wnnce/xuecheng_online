package com.zeroxn.xuecheng.auth.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.UUID;

/**
 * @Author: lisang
 * @DateTime: 2023-09-22 20:20:53
 * @Description:
 */
@EnableWebSecurity
@Configuration
public class XueChengSecurityConfig {
    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails user1 = User
                .withUsername("user")
                .password("123456")
                .authorities("user")
                .build();
        UserDetails user2 = User
                .withUsername("admin")
                .password("123456")
                .authorities("admin")
                .build();
        return new InMemoryUserDetailsManager(user1, user2);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/role/r1")
                .hasAuthority("user")
                .requestMatchers("/role/r2")
                .hasAuthority("admin")
                .anyRequest().authenticated()
                .and())
                .formLogin(form -> form
                        .successForwardUrl("/success")
                        .permitAll());
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
