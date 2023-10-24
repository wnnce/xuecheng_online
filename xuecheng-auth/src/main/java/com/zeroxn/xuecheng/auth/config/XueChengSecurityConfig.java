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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @Author: lisang
 * @DateTime: 2023-09-22 20:20:53
 * @Description:
 */
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class XueChengSecurityConfig {
    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User
                .withUsername("user")
                .password("123456")
                .roles("user")
                .authorities("list")
                .build();
        UserDetails user2 = User
                .withUsername("admin")
                .password("123456")
                .roles("admin")
                .build();
        return new InMemoryUserDetailsManager(user1, user2);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/role/r1").hasRole("user")
                .requestMatchers("/role/r2").hasRole("admin")
                .requestMatchers("/oauth2/**").permitAll()
                .anyRequest().authenticated())
                .csrf().disable()
                .formLogin(Customizer.withDefaults());
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
