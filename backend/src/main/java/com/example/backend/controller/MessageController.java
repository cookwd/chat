package com.example.backend.controller;

import com.example.backend.model.Message;
import com.example.backend.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @PostMapping("/send")
    public String sendMessage(@RequestBody Message message) {
        messageRepository.save(message);
        return "Message sent!";
    }

    @GetMapping("/history")
    public List<Message> getMessages(@RequestParam Long userId, @RequestParam Long friendId) {
        // 查询与好友的消息记录
        return messageRepository.findAll(); // 这里可以根据实际需求完善查询
    }
}

