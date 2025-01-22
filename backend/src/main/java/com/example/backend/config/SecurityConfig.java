package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()  // 禁用 CSRF 防护，适合 API 项目
                .authorizeRequests()
                .antMatchers("/api/users/register", "/api/users/login").permitAll()  // 注册与登录接口允许匿名访问
                .anyRequest().authenticated();  // 其他请求需要认证

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // 提供密码加密解密
    }
}
