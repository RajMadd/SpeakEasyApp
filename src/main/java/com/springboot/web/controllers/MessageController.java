package com.springboot.web.controllers;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.web.dao.ChatRoomRepository;
import com.springboot.web.dao.UserChatRoomRepository;
import com.springboot.web.dao.UserRepository;
import com.springboot.web.entities.ChatRoomEntity;
import com.springboot.web.entities.MessageEntity;
import com.springboot.web.entities.UserChatRoomEntity;
import com.springboot.web.entities.UserEntity;
import com.springboot.web.service.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {

	
	
	@Autowired
	private MessageService messageService;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private ChatRoomRepository chatRoomRepository;

	@Autowired
	private UserChatRoomRepository userChatRoomRepository;

	
	@PostMapping
  public ResponseEntity<?> sendMessage(@RequestBody MessageEntity message) {
	    if (message.getSender() == null || message.getSender().getId() == null) {
	        return ResponseEntity.badRequest().body("Sender information missing");
	    }

	    // Fetch sender
	    Optional<UserEntity> senderOpt = userRepository.findById(message.getSender().getId());
   if (!senderOpt.isPresent()) {
	        return ResponseEntity.badRequest().body("Sender not found");
	    }
	    
	    UserEntity sender = senderOpt.get();
	    message.setSender(sender);

	    ChatRoomEntity chatRoom = null;

	    if (message.getChatRoom() != null && message.getChatRoom().getId() != null) {
	        // If chatRoom ID is provided, fetch it
	        Optional<ChatRoomEntity> chatRoomOpt = chatRoomRepository.findById(message.getChatRoom().getId());
       if (!chatRoomOpt.isPresent()) {
	            return ResponseEntity.badRequest().body("Chat Room not found");
	        }
	        chatRoom = chatRoomOpt.get();
	        message.setChatRoom(chatRoom);
	        
	        // If it is a group chat, make sure sender is a member
	        if (chatRoom.isGroupChat()) {
	            boolean isMember = chatRoom.getUserChatRooms().stream()
	                    .anyMatch(ucr -> ucr.getUser().getId().equals(sender.getId()));
	            if (!isMember) {
	                return ResponseEntity.badRequest().body("Sender is not a member of this group chat room");
	            }
	        }
	    } else {
	        // If no chatRoom ID is provided, we need to create/find chat room
            if (message.getReceiver() == null || message.getReceiver().getId() == null) {
	            return ResponseEntity.badRequest().body("Receiver information missing");
	        }

	    	
	    	    
	        // Private chat: find if chat room already exists
	        Optional<UserEntity> receiverOpt = userRepository.findById(message.getReceiver().getId());
	        if (!receiverOpt.isPresent()) {
	            return ResponseEntity.badRequest().body("Receiver not found");
	        }
	        UserEntity receiver = receiverOpt.get();
	        message.setReceiver(receiver);

	        List<UserChatRoomEntity> senderRooms = userChatRoomRepository.findByUser(sender);
	        List<UserChatRoomEntity> receiverRooms = userChatRoomRepository.findByUser(receiver);

	        for (UserChatRoomEntity senderRoom : senderRooms) {
	            for (UserChatRoomEntity receiverRoom : receiverRooms) {
	                if (senderRoom.getChatRoom().getId().equals(receiverRoom.getChatRoom().getId())
	                        && !senderRoom.getChatRoom().isGroupChat()) {
	                    chatRoom = senderRoom.getChatRoom();
	                    break;
	                }
	            }
	            if (chatRoom != null) break;
	        }

	        // If not found, create a new private chatroom
	        if (chatRoom == null) {
	            chatRoom = new ChatRoomEntity();
	            chatRoom.setChatRoomName(sender.getUsername() + "_" + receiver.getUsername());
	            chatRoom.setGroupChat(false);
	            chatRoom = chatRoomRepository.save(chatRoom);

	            // Add both users to new chatroom
	            UserChatRoomEntity userChat1 = new UserChatRoomEntity(sender, chatRoom);
	            UserChatRoomEntity userChat2 = new UserChatRoomEntity(receiver, chatRoom);
	            chatRoom.getUserChatRooms().add(userChat1);
	            chatRoom.getUserChatRooms().add(userChat2);
	            chatRoomRepository.save(chatRoom);
	        }
	    }

	    message.setChatRoom(chatRoom);

	    // For private chat, receiver must be set
	    if (!chatRoom.isGroupChat() && message.getReceiver() == null) {
	        return ResponseEntity.badRequest().body("Receiver must be provided for private chats");
	    }

	    // 🔥🔥 Now based on GroupChat or PrivateChat, call different methods
	    if (chatRoom.isGroupChat()) {
	        // For group chat, send message to all users
	        messageService.sendMessageToAll(chatRoom.getId(), sender.getId(), message.getContent());
	        return ResponseEntity.ok("Group message sent successfully");
	    } else {
	        // For private chat, send message to specific receiver
	        MessageEntity savedMessage = messageService.sendMessage(message);
        return ResponseEntity.ok(savedMessage);
	    }
	}
	


	
	
	@GetMapping("/{chatRoomId}")
    public ResponseEntity<List<MessageEntity>> getMessagesByChatRoom(@PathVariable Long chatRoomId) {
        List<MessageEntity> messages = messageService.getMessagesByChatRoom(chatRoomId);
        return messages.isEmpty() ? ResponseEntity.notFound().build() :ResponseEntity.ok(messages);
    }

	
	
	@GetMapping("/private/{senderId}/{receiverId}")
    public ResponseEntity<List<MessageEntity>> getPrivateMessages(
            @PathVariable Long senderId,
            @PathVariable Long receiverId) {
        List<MessageEntity> messages = messageService.getPrivateMessages(senderId, receiverId);
        return ResponseEntity.ok(messages);
    }
	
}