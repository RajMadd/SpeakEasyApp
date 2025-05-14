package com.springboot.web.entities;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class ChatRoomEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String chatRoomName;
	
	@Column(nullable=false)
	private boolean isGroupChat;
	
	@OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference  // Prevents infinite recursion
	private Set<MessageEntity> messages;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
    private List<UserChatRoomEntity> userChatRooms = new ArrayList<>();

	

	public List<UserChatRoomEntity> getUserChatRooms() {
		return userChatRooms;
	}

	public void setUserChatRooms(List<UserChatRoomEntity> userChatRooms) {
		this.userChatRooms = userChatRooms;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChatRoomName() {
		return chatRoomName;
	}

	public void setChatRoomName(String chatRoomName) {
		this.chatRoomName = chatRoomName;
	}

	public boolean isGroupChat() {
		return isGroupChat;
	}

	public void setGroupChat(boolean isGroupChat) {
		this.isGroupChat = isGroupChat;
	}

	public Set<MessageEntity> getMessages() {
		return messages;
	}

	public void setMessages(Set<MessageEntity> messages) {
		this.messages = messages;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public ChatRoomEntity(Long id, String chatRoomName, boolean isGroupChat, Set<MessageEntity> messages, List<UserChatRoomEntity> userChatRooms,
			Date createdAt) {
		super();
		this.id = id;
		this.chatRoomName = chatRoomName;
		this.isGroupChat = isGroupChat;
		this.messages = messages;
		this.userChatRooms = userChatRooms;
		this.createdAt = createdAt;
	}

	public ChatRoomEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
