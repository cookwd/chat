package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.UserService;
import com.example.backend.util.JwtTokenUtil;  // 导入 JwtTokenUtil
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:8081")  // 允许来自前端 http://localhost:8081 的跨域请求
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return "Username already taken!";
        }
        userRepository.save(user);
        return "Registration successful!";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser != null && foundUser.getPassword().equals(user.getPassword())) {
            // 生成 JWT
            String token = JwtTokenUtil.generateToken(foundUser.getUsername());

            // 返回包含用户信息和 JWT 的响应
            return ResponseEntity.ok(new LoginResponse(foundUser, token,"success"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials!");
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 用于返回登录响应的 DTO 类
    @Getter
    public static class LoginResponse {
        private User user;
        private String token;
        private String status;

        public LoginResponse(User user, String token, String status) {
            this.user = user;
            this.token = token;
            this.status = status;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
