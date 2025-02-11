package com.example.backend.config;

import com.example.backend.filter.JwtRequestFilter;  // 导入 JwtRequestFilter
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    // 创建 JwtRequestFilter 实例
    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        return new JwtRequestFilter();
    }

    // 创建 BCryptPasswordEncoder bean
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 配置 HTTP 安全性
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())  // 禁用 CSRF 防护，适用于无状态应用（如使用 JWT）
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/api/users/login", "/api/users/register").permitAll()  // 允许匿名访问登录与注册接口
                        .anyRequest().authenticated()  // 其他接口需要认证
                )
                .cors(Customizer.withDefaults());  // 启用 CORS 支持

        // 添加 JWT 过滤器
        http.addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 配置跨域请求支持
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // 允许 /api/** 路径的请求
                .allowedOrigins("http://localhost:8081")  // 允许的前端地址
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // 允许的 HTTP 请求方法
                .allowedHeaders("*")  // 允许的请求头
                .allowCredentials(true);  // 是否允许携带 cookie
    }
}
