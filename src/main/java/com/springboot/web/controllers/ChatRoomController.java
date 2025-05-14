package com.springboot.web.controllers;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import 
org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.web.entities.ChatRoomEntity;
import com.springboot.web.service.ChatRoomService;

@RestController
@RequestMapping("/chatrooms")
public class ChatRoomController {

	@Autowired
	private ChatRoomService chatRoomService;
	
	@PostMapping
    public ResponseEntity<ChatRoomEntity> createChatRoom(@RequestBody ChatRoomEntity chatRoom) {
        ChatRoomEntity createdChatRoom = chatRoomService.createChatRoom(chatRoom);
        return ResponseEntity.ok(createdChatRoom);
    }

    // Get a chat room by ID
    @GetMapping("/{id}")
    public ResponseEntity<ChatRoomEntity> getChatRoomById(@PathVariable Long id) {
        Optional<ChatRoomEntity> chatRoom = chatRoomService.getChatRoomById(id);
        return chatRoom.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all chat rooms
    @GetMapping
    public ResponseEntity<List<ChatRoomEntity>> getAllChatRooms() {
        List<ChatRoomEntity> chatRooms = chatRoomService.getAllChatRooms();
        return ResponseEntity.ok(chatRooms);
    }
    
    @PutMapping("/{chatRoomId}")
    public ResponseEntity<ChatRoomEntity> updateChatRoom(
            @PathVariable Long chatRoomId, @RequestBody ChatRoomEntity updatedChatRoom) {
        return ResponseEntity.ok(chatRoomService.updateChatRoom(chatRoomId, updatedChatRoom));
    }

    
    @DeleteMapping("/{chatRoomId}")
    public ResponseEntity<String> deleteChatRoom(@PathVariable Long chatRoomId) {
        chatRoomService.deleteChatRoom(chatRoomId);
        return ResponseEntity.ok("Chat room deleted successfully.");
    }
	
}
