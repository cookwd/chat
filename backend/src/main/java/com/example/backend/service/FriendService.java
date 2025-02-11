package com.example.backend.service;

import com.example.backend.model.Friend;
import com.example.backend.model.FriendRequest;
import com.example.backend.model.User;
import com.example.backend.repository.FriendRepository;
import com.example.backend.repository.FriendRequestRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendService {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private UserRepository userRepository;

    // 添加好友
    public void addFriend(Long userId, Long friendId) {
        if (!friendRepository.existsByUserIdAndFriendId(userId, friendId)) {
            friendRepository.save(new Friend(userId, friendId, "accepted"));
            friendRepository.save(new Friend(friendId, userId, "accepted"));  // 双向添加好友
        }
    }

    // 删除好友
    public void removeFriend(Long userId, Long friendId) {
        friendRepository.deleteByUserIdAndFriendId(userId, friendId);
        friendRepository.deleteByUserIdAndFriendId(friendId, userId); // 删除双方关系
    }

    // 获取好友列表
    public List<User> getFriends(Long userId) {
        List<Friend> friends = friendRepository.findByUserId(userId);
        return friends.stream()
                .map(friend -> userRepository.findById(friend.getFriendId()).orElse(null))
                .collect(Collectors.toList());
    }

    // 发送好友请求
    public void sendFriendRequest(Long userId, Long friendId) {
        FriendRequest friendRequest = new FriendRequest(userId, friendId, "pending", getCurrentTime());
        friendRequestRepository.save(friendRequest);
    }

    // 接受好友请求
    public void acceptFriendRequest(Long userId, Long friendId) {
        friendRequestRepository.findByUserIdAndFriendId(userId, friendId).ifPresent(request -> {
            request.setStatus("accepted");
            friendRequestRepository.save(request);
            addFriend(userId, friendId);
        });
    }

    // 拒绝好友请求
    public void rejectFriendRequest(Long userId, Long friendId) {
        friendRequestRepository.findByUserIdAndFriendId(userId, friendId).ifPresent(request -> {
            request.setStatus("rejected");
            friendRequestRepository.save(request);
        });
    }

    // 获取好友请求状态
    public String getFriendRequestStatus(Long userId, Long friendId) {
        return friendRequestRepository.findStatusByUserIdAndFriendId(userId, friendId);
    }

    // 获取好友请求列表
    public List<FriendRequest> getFriendRequests(Long userId) {
        return friendRequestRepository.findByUserId(userId);
    }

    private String getCurrentTime() {
        // You can use Java's LocalDateTime or a custom time formatter
        return LocalDateTime.now().toString();
    }
}
