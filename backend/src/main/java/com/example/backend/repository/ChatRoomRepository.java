package com.example.backend.repository;

import com.example.backend.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    // 可以根据需要添加查询方法
}

