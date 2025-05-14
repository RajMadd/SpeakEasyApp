package com.springboot.web.entities;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class MessageEntity {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "sender_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private UserEntity sender;
	
	@ManyToOne
    @JoinColumn(name = "receiver_id", nullable = true) 
	@OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity receiver;

	
	@ManyToOne
	@JoinColumn(name = "chatroom_id", nullable = false)
	@JsonBackReference  // Prevents infinite recursion
	private ChatRoomEntity chatRoom;
	
	@Column(nullable=false , length=1000)
	private String content;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date sentAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserEntity getSender() {
		return sender;
	}

	public void setSender(UserEntity sender) {
		this.sender = sender;
	}

	public UserEntity getReceiver() {
		return receiver;
	}

	public void setReceiver(UserEntity receiver) {
		this.receiver = receiver;
	}

	public ChatRoomEntity getChatRoom() {
		return chatRoom;
	}

	public void setChatRoom(ChatRoomEntity chatRoom) {
		this.chatRoom = chatRoom;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSentAt() {
		return sentAt;
	}

	public void setSentAt(Date sentAt) {
		this.sentAt = sentAt;
	}

	public MessageEntity(Long id, UserEntity sender, UserEntity receiver, ChatRoomEntity chatRoom, String content,
			Date sentAt) {
		super();
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.chatRoom = chatRoom;
		this.content = content;
		this.sentAt = sentAt;
	}

	public MessageEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "MessageEntity [id=" + id + ", sender=" + sender + ", receiver=" + receiver + ", chatRoom=" + chatRoom
				+ ", content=" + content + ", sentAt=" + sentAt + "]";
	}

	

	
	
}
