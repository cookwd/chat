package com.example.backend.service;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 用户注册
    public User registerUser(String username, String password) {
        // 加密密码
        String encryptedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(encryptedPassword);
        return userRepository.save(user);
    }

    // 用户登录
    public User loginUser(String username, String password) {
        // 查找用户名是否存在
        User user = userRepository.findByUsername(username);

        // 如果用户存在，验证密码
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;  // 用户验证通过，返回 User 对象
        }

        return null;  // 用户不存在或密码不正确
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
