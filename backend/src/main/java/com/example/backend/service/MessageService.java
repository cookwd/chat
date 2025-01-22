package com.example.backend.service;

import com.example.backend.model.Message;
import com.example.backend.model.ChatRoom;
import com.example.backend.repository.MessageRepository;
import com.example.backend.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, ChatRoomRepository chatRoomRepository) {
        this.messageRepository = messageRepository;
        this.chatRoomRepository = chatRoomRepository;
    }

    // 发送消息
    public Message sendMessage(Long roomId, String sender, String content) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("ChatRoom not found"));

        Message message = new Message();
        message.setChatRoom(chatRoom);
        message.setSender(sender);
        message.setContent(content);
        return messageRepository.save(message);
    }

    // 获取聊天室中的历史消息
    public List<Message> getMessages(Long roomId) {
        return messageRepository.findByChatRoomId(roomId);
    }
}
