package com.springboot.web.entities;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "chat_room_id"}))
public class UserChatRoomEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private UserEntity user;
	
	@ManyToOne
	@JoinColumn(name="chatroom_id", nullable=false)
	@JsonBackReference
	private ChatRoomEntity chatRoom;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public ChatRoomEntity getChatRoom() {
		return chatRoom;
	}
	public void setChatRoom(ChatRoomEntity chatRoom) {
		this.chatRoom = chatRoom;
	}
	public UserChatRoomEntity(Long id, UserEntity user, ChatRoomEntity chatRoom) {
		super();
		this.id = id;
		this.user = user;
		this.chatRoom = chatRoom;
	}
	public UserChatRoomEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserChatRoomEntity(UserEntity user, ChatRoomEntity chatRoom) {
	    this.user = user;
	    this.chatRoom = chatRoom;
	}

	
}
