package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 配置允许跨域请求的路径
        registry.addMapping("/api/**")  // 允许所有以 "/api/" 开头的路径跨域访问
                .allowedOrigins("*")  // 允许所有来源的请求
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // 允许的请求方法
                .allowedHeaders("*")  // 允许的请求头
                .allowCredentials(true)  // 是否允许携带 cookie
                .maxAge(3600);  // 设置预检请求的缓存时间，单位是秒
    }
}
