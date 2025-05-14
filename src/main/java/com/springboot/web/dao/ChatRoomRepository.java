package com.springboot.web.dao;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.web.entities.ChatRoomEntity;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long>{
	Optional<ChatRoomEntity> findByChatRoomName(String chatRoomName);
	void deleteById(Long id);

}
