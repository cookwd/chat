package com.example.backend.repository;

import java.util.List;

import com.example.backend.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    // 查询用户的好友关系
    boolean existsByUserIdAndFriendId(Long userId, Long friendId);

    // 删除好友关系
    void deleteByUserIdAndFriendId(Long userId, Long friendId);

    // 根据用户ID查询所有好友
    List<Friend> findByUserId(Long userId);

    // 根据用户ID和好友ID查询状态
    String findRequestStatusByUserIdAndFriendId(Long userId, Long friendId);
}

