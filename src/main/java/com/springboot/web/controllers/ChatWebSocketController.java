package com.springboot.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.springboot.web.entities.MessageEntity;

@Controller
public class ChatWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload MessageEntity message) {
        // Save message logic here (optional if already saved in REST controller)

        if (message.getChatRoom().isGroupChat()) {
            messagingTemplate.convertAndSend("/topic/group/" + message.getChatRoom().getId(), message);
        } else {
            Long senderId = message.getSender().getId();
            Long receiverId = message.getReceiver().getId();

            messagingTemplate.convertAndSendToUser(receiverId.toString(), "/queue/messages", message);
            messagingTemplate.convertAndSendToUser(senderId.toString(), "/queue/messages", message);
        }
    }
}
