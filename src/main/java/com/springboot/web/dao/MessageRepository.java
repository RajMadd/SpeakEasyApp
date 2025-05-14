package com.springboot.web.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.web.entities.ChatRoomEntity;
import com.springboot.web.entities.MessageEntity;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long>{

	List<MessageEntity> findByChatRoom(ChatRoomEntity chatRoom);
	@Query("SELECT m FROM MessageEntity m WHERE (m.sender.id = :senderId AND m.receiver.id = :receiverId) OR (m.sender.id = :receiverId AND m.receiver.id = :senderId)")
	List<MessageEntity> findPrivateMessages(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);
	 List<MessageEntity> findByChatRoom_IdOrderBySentAtAsc(Long chatRoomId);
	 List<MessageEntity> findBySender_IdAndReceiver_IdOrderBySentAtAsc(Long senderId, Long receiverId);

}
