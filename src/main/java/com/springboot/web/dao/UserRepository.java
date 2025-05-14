package com.springboot.web.dao;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.web.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	Optional<UserEntity> findByEmail(String email);
	Optional<UserEntity> findByUsername(String username);
}
