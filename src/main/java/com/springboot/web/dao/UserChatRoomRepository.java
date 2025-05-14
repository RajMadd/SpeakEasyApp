package com.springboot.web.dao;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.web.entities.ChatRoomEntity;
import com.springboot.web.entities.UserChatRoomEntity;
import com.springboot.web.entities.UserEntity;

@Repository
public interface UserChatRoomRepository extends JpaRepository<UserChatRoomEntity, Long>{
	List<UserChatRoomEntity> findByUser(UserEntity user);
	List<UserChatRoomEntity> findByChatRoom(ChatRoomEntity chatRoom);
	Optional<UserChatRoomEntity> findByUserAndChatRoom(UserEntity user, ChatRoomEntity chatRoom);
}
