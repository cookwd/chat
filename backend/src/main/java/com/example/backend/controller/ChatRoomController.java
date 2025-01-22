package com.example.backend.controller;

import com.example.backend.model.ChatRoom;
import com.example.backend.model.User;
import com.example.backend.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/chatrooms")
public class ChatRoomController {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    // 创建聊天室
    @PostMapping("/create")
    public ChatRoom createChatRoom(@RequestBody ChatRoom chatRoom) {
        return chatRoomRepository.save(chatRoom);
    }

    // 加入聊天室
    @PostMapping("/join")
    public String joinChatRoom(@RequestParam Long roomId, @RequestParam Long userId) {
        // 这里可以实现用户加入聊天室的逻辑
        return "Joined chat room successfully!";
    }

    // 获取聊天室成员
    @GetMapping("/{roomId}/members")
    public Set<User> getMembers(@PathVariable Long roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElse(null);
        return chatRoom != null ? chatRoom.getMembers() : null;
    }
}
