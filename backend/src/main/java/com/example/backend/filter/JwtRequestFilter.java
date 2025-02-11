package com.example.backend.filter;

import com.example.backend.util.JwtTokenUtil;  // 导入 JwtTokenUtil 类
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 获取请求头中的 Authorization 字段
        final String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            try {
                // 提取 token 中的用户名
                String username = JwtTokenUtil.extractUsername(token.substring(7));  // 去掉 "Bearer " 前缀

                if (username != null && !JwtTokenUtil.isTokenExpired(token)) {
                    // 在 Spring Security 上下文中设置用户信息（认证）
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, null);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JWTVerificationException e) {
                // 如果验证失败（例如过期或无效 token），返回 401 状态码
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or expired JWT token");
                return;
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("JWT token validation error");
                return;
            }
        }

        // 继续执行请求链
        chain.doFilter(request, response);
    }
}
