package com.example.backend.service;

import com.example.backend.model.ChatRoom;
import com.example.backend.model.User;
import com.example.backend.repository.ChatRoomRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    @Autowired
    public ChatRoomService(ChatRoomRepository chatRoomRepository, UserRepository userRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
    }

    // 创建聊天室
    public ChatRoom createChatRoom(String name, Set<User> members) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(name);
        chatRoom.setMembers(members);
        return chatRoomRepository.save(chatRoom);
    }

    // 获取聊天室信息
    public Optional<ChatRoom> getChatRoom(Long id) {
        return chatRoomRepository.findById(id);
    }

    // 获取聊天室成员
    public Set<User> getMembers(Long roomId) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findById(roomId);
        return chatRoom.map(ChatRoom::getMembers).orElseThrow(() -> new RuntimeException("ChatRoom not found"));
    }

    // 添加成员到聊天室
    public ChatRoom addMember(Long roomId, Long userId) {
        Optional<ChatRoom> chatRoomOpt = chatRoomRepository.findById(roomId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (chatRoomOpt.isPresent() && userOpt.isPresent()) {
            ChatRoom chatRoom = chatRoomOpt.get();
            chatRoom.getMembers().add(userOpt.get());
            return chatRoomRepository.save(chatRoom);
        }

        throw new RuntimeException("ChatRoom or User not found");
    }
}
