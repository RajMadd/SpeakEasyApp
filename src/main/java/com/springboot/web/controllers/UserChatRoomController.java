package com.springboot.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.web.dao.ChatRoomRepository;
import com.springboot.web.dao.UserChatRoomRepository;
import com.springboot.web.entities.ChatRoomEntity;
import com.springboot.web.entities.UserChatRoomEntity;
import com.springboot.web.entities.UserEntity;
import com.springboot.web.service.UserChatRoomService;



@RestController
@RequestMapping("/userchatroom")
public class UserChatRoomController {
	
	
	@Autowired
    private UserChatRoomService userChatRoomService;
	
	@Autowired
	private UserChatRoomRepository userChatRoomRepository;
	
	@Autowired
	private ChatRoomRepository chatRoomRepository;

	
    // Add a user to a chat room
    @PostMapping("/{userId}/{chatRoomId}")
    public ResponseEntity<UserChatRoomEntity> addUserToChatRoom(
            @PathVariable Long userId, 
            @PathVariable Long chatRoomId) {
        UserChatRoomEntity userChatRoom = userChatRoomService.addUserToChatRoom(userId, chatRoomId);
        return ResponseEntity.ok(userChatRoom);
    }
    
    

    // Remove a user from a chat room
    @DeleteMapping("/{userId}/{chatRoomId}")
    public ResponseEntity<Void> removeUserFromChatRoom(
            @PathVariable Long userId, 
            @PathVariable Long chatRoomId) {
        userChatRoomService.removeUserFromChatRoom(userId, chatRoomId);
        return ResponseEntity.noContent().build();
    }
    
    

    // Get all chat rooms a user is in
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ChatRoomEntity>> getChatRoomsForUser(@PathVariable Long userId) {
        List<ChatRoomEntity> chatRooms = userChatRoomService.getChatRoomsForUser(userId);
        return chatRooms.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(chatRooms);
    }

    
    
    // Get all users in a chat room
    @GetMapping("/chatroom/{chatRoomId}")
    public ResponseEntity<List<UserEntity>> getUsersInChatRoom(@PathVariable Long chatRoomId) {
        List<UserEntity> users = userChatRoomService.getUsersInChatRoom(chatRoomId);
        return users.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(users);
    }
    
    
    @GetMapping("/receiver/{userId}/{chatRoomId}")
    public ResponseEntity<Long> getOtherUserInPrivateChat(
            @PathVariable Long userId, 
            @PathVariable Long chatRoomId) {

        ChatRoomEntity chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("Chat Room not found"));

        if (chatRoom.isGroupChat()) {
            return ResponseEntity.badRequest().build(); // Not applicable for group chats
        }

        
		List<UserChatRoomEntity> members = userChatRoomRepository.findByChatRoom(chatRoom);
        for (UserChatRoomEntity member : members) {
            if (!member.getUser().getId().equals(userId)) {
                return ResponseEntity.ok(member.getUser().getId());
            }
        }

        return ResponseEntity.notFound().build(); // Only one user in the room so far
    }

}
