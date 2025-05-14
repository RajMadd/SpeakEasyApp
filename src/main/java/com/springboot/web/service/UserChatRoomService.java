package com.springboot.web.service;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.web.dao.ChatRoomRepository;
import com.springboot.web.dao.UserChatRoomRepository;
import com.springboot.web.dao.UserRepository;
import com.springboot.web.entities.ChatRoomEntity;
import com.springboot.web.entities.UserChatRoomEntity;
import com.springboot.web.entities.UserEntity;

@Service
public class UserChatRoomService {
	
	@Autowired
	private UserChatRoomRepository userChatRoomRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ChatRoomRepository chatRoomRepository;
	
	
	
	
	
	public UserChatRoomEntity addUserToChatRoom(Long userId, Long chatRoomId) {
		UserEntity user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));
		ChatRoomEntity chatRoom = chatRoomRepository.findById(chatRoomId)
				.orElseThrow(() -> new RuntimeException("Chat Room not found"));
		
		// Check if the user is already in the chat room
        if (userChatRoomRepository.findByUserAndChatRoom(user, chatRoom).isPresent()) {
            throw new RuntimeException("User is already in this chat room");
        }

     // 🚨 NEW CHECK: If it is a private chat (groupChat == false)
        if (!chatRoom.isGroupChat()) {
            // Fetch all existing users in this chat room
            List<UserChatRoomEntity> existingUsers = userChatRoomRepository.findByChatRoom(chatRoom);
            if (existingUsers.size() >= 2) {
                throw new RuntimeException("Private chat room already has 2 members. Cannot add more.");
            }
        }
        
        // Create and save the new user-chatroom entry
        UserChatRoomEntity userChatRoom = new UserChatRoomEntity(user, chatRoom);  // FIXED CONSTRUCTOR USAGE
        return userChatRoomRepository.save(userChatRoom);
    }
	
	
	
	
	public void removeUserFromChatRoom(Long userId, Long chatRoomId) {
		UserEntity user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));
		ChatRoomEntity chatRoom = chatRoomRepository.findById(chatRoomId)
				.orElseThrow(() -> new RuntimeException("Chat Room not found"));

		UserChatRoomEntity userChatRoom = userChatRoomRepository.findByUserAndChatRoom(user, chatRoom)
                .orElseThrow(() -> new RuntimeException("User is not in this chat room"));

        userChatRoomRepository.delete(userChatRoom);
    }
		
	
	
	
	
		public List<ChatRoomEntity> getChatRoomsForUser(Long userId) {
			UserEntity user = userRepository.findById(userId)
					.orElseThrow(() -> new RuntimeException("User not found"));

			List<UserChatRoomEntity> userChatRooms = userChatRoomRepository.findByUser(user);
	        return userChatRooms.stream()
	                .map(UserChatRoomEntity::getChatRoom)
	                .collect(Collectors.toList());
		}
		
		
		
		
		
		public List<UserEntity> getUsersInChatRoom(Long chatRoomId) {
			ChatRoomEntity chatRoom = chatRoomRepository.findById(chatRoomId)
					.orElseThrow(() -> new RuntimeException("Chat Room not found"));
			List<UserChatRoomEntity> userChatRooms = userChatRoomRepository.findByChatRoom(chatRoom);
	        return userChatRooms.stream()
	                .map(UserChatRoomEntity::getUser)
	                .collect(Collectors.toList());
	    }
	
}
