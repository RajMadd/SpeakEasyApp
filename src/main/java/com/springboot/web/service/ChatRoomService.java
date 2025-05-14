package com.springboot.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.web.dao.ChatRoomRepository;
import com.springboot.web.entities.ChatRoomEntity;

import jakarta.transaction.Transactional;

@Service
public class ChatRoomService {
	
	@Autowired
	private ChatRoomRepository chatRoomRepository;
	
	public ChatRoomEntity createChatRoom(ChatRoomEntity chatRoom) {
		return chatRoomRepository.save(chatRoom);
	}
	
	public Optional<ChatRoomEntity> findByChatRoomName(String chatRoomName){
		return chatRoomRepository.findByChatRoomName(chatRoomName);
	}
	
	    // Get a chat room by ID
	 public Optional<ChatRoomEntity> getChatRoomById(Long id) {
	        return chatRoomRepository.findById(id);
	    }
	 
	 public ChatRoomEntity updateChatRoom(Long chatRoomId, ChatRoomEntity updatedChatRoom) {
	        return chatRoomRepository.findById(chatRoomId).map(chatRoom -> {
	            chatRoom.setChatRoomName(updatedChatRoom.getChatRoomName());
	            chatRoom.setGroupChat(updatedChatRoom.isGroupChat());
	            
	            if (updatedChatRoom.getMessages() != null) {
	                chatRoom.getMessages().clear(); // Remove old references
	                chatRoom.getMessages().addAll(updatedChatRoom.getMessages()); // Add new messages
	            }

	            return chatRoomRepository.save(chatRoom);
	        }).orElseThrow(() -> new RuntimeException("Chat Room not found"));
	    }
	 
	    // Get all chat rooms
	 public List<ChatRoomEntity> getAllChatRooms() {
	        return chatRoomRepository.findAll();
	    }
	 
	 @Transactional
	 public void deleteChatRoom(Long chatRoomId) {
	        chatRoomRepository.deleteById(chatRoomId);
	    }
}
