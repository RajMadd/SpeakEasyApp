package com.springboot.web.service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.web.dao.ChatRoomRepository;
import com.springboot.web.dao.MessageRepository;
import com.springboot.web.dao.UserRepository;
import com.springboot.web.entities.ChatRoomEntity;
import com.springboot.web.entities.MessageEntity;
import com.springboot.web.entities.UserEntity;

@Service
public class MessageService {

	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
    private UserRepository userRepository;

	
	@Autowired
	private ChatRoomRepository chatRoomRepository;
	
	public void sendMessageToAll(Long chatRoomId, Long senderId, String content) {
	    Optional<ChatRoomEntity> chatRoomOpt = chatRoomRepository.findById(chatRoomId);
	    Optional<UserEntity> senderOpt = userRepository.findById(senderId);

	    if (!chatRoomOpt.isPresent() || !senderOpt.isPresent()) {
	        throw new RuntimeException("Chat Room or Sender not found");
	    }

	    ChatRoomEntity chatRoom = chatRoomOpt.get();
	    UserEntity sender = senderOpt.get();

	    MessageEntity message = new MessageEntity();
	    message.setChatRoom(chatRoom);
	    message.setSender(sender);
	    message.setContent(content);
    messageRepository.save(message); // 💾 Save the message
	}
	


	
	public MessageEntity  sendMessage(MessageEntity message) {
		return messageRepository.save(message);
	}
	
	public List<MessageEntity> getMessagesByChatRoom(Long chatRoomId){
            return chatRoomRepository.findById(chatRoomId)
                    .map(chatroom -> messageRepository.findByChatRoom_IdOrderBySentAtAsc(chatRoomId))
                    .orElse(Collections.emptyList());
	}
	
	public List<MessageEntity> getPrivateMessages(Long senderId, Long receiverId) {
	    return messageRepository.findBySender_IdAndReceiver_IdOrderBySentAtAsc(senderId, receiverId);
	}

}
