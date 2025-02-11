package com.example.backend.repository;

import com.example.backend.model.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    // 查找用户发送的所有好友请求
    List<FriendRequest> findByUserId(Long userId);

    // 查找指定用户与指定好友的请求状态
    String findStatusByUserIdAndFriendId(Long userId, Long friendId);

    // 查找好友请求
    Optional<FriendRequest> findByUserIdAndFriendId(Long userId, Long friendId);
}

