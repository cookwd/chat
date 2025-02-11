package com.example.backend.controller;

import com.example.backend.model.FriendRequest;
import com.example.backend.model.User;
import com.example.backend.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backend.config.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendController {

    @Autowired
    private FriendService friendService;

    // 添加好友
    @PostMapping
    public ResponseEntity<?> addFriend(@RequestBody FriendRequest friendRequest) {
        try {
            // 添加好友到数据库
            friendService.addFriend(friendRequest.getUserId(), friendRequest.getFriendId());
            return ResponseEntity.ok(new ApiResponse(true, "Friend added successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, "Failed to add friend"));
        }
    }

    // 删除好友
    @DeleteMapping
    public ResponseEntity<?> removeFriend(@RequestParam Long userId, @RequestParam Long friendId) {
        try {
            friendService.removeFriend(userId, friendId);
            return ResponseEntity.ok(new ApiResponse(true, "Friend removed successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, "Failed to remove friend"));
        }
    }

    // 获取用户好友列表
    @GetMapping
    public ResponseEntity<List<User>> getFriends(@RequestParam Long userId) {
        try {
            List<User> friends = friendService.getFriends(userId);
            return ResponseEntity.ok(friends);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 发送好友请求
    @PostMapping("/request")
    public ResponseEntity<?> sendFriendRequest(@RequestBody FriendRequest friendRequest) {
        try {
            friendService.sendFriendRequest(friendRequest.getUserId(), friendRequest.getFriendId());
            return ResponseEntity.ok(new ApiResponse(true, "Friend request sent"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, "Failed to send friend request"));
        }
    }

    // 接受好友请求
    @PostMapping("/accept")
    public ResponseEntity<?> acceptFriendRequest(@RequestBody FriendRequest friendRequest) {
        try {
            friendService.acceptFriendRequest(friendRequest.getUserId(), friendRequest.getFriendId());
            return ResponseEntity.ok(new ApiResponse(true, "Friend request accepted"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, "Failed to accept friend request"));
        }
    }

    // 拒绝好友请求
    @PostMapping("/reject")
    public ResponseEntity<?> rejectFriendRequest(@RequestBody FriendRequest friendRequest) {
        try {
            friendService.rejectFriendRequest(friendRequest.getUserId(), friendRequest.getFriendId());
            return ResponseEntity.ok(new ApiResponse(true, "Friend request rejected"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, "Failed to reject friend request"));
        }
    }

    // 获取好友请求状态
    @GetMapping("/status")
    public ResponseEntity<String> getFriendRequestStatus(@RequestParam Long userId, @RequestParam Long friendId) {
        try {
            String status = friendService.getFriendRequestStatus(userId, friendId);
            return ResponseEntity.ok(status);  // 例如：待确认、已接受、已拒绝
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found");
        }
    }

    // 获取当前用户的所有好友请求
    @GetMapping("/requests")
    public ResponseEntity<List<FriendRequest>> getFriendRequests(@RequestParam Long userId) {
        try {
            List<FriendRequest> requests = friendService.getFriendRequests(userId);
            return ResponseEntity.ok(requests);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}

