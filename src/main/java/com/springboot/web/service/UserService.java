package com.springboot.web.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.springboot.web.dao.UserRepository;
import com.springboot.web.entities.UserEntity;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	public UserEntity saveUser(UserEntity user) {
		return userRepository.save(user);
	}
	public Optional<UserEntity> findByEmail(String email){
		return userRepository.findByEmail(email);
	}
	
	public Optional<UserEntity> findById(Long id){
		return userRepository.findById(id);
	}
	
	public UserEntity createUser(@RequestBody UserEntity user){
		return userRepository.save(user);
	}
	
	public Optional<UserEntity> getUserById(@PathVariable Long id){
		return userRepository.findById(id);
	}
	
	
	public List<UserEntity> getAllUsers(){
		return (List<UserEntity>) userRepository.findAll();
	}
	
//	public UserEntity updateUser(@PathVariable Long id,@RequestBody UserEntity userDetails){
//		return userRepository.findById(id).map(user ->{
//			user.setUsername(userDetails.getUsername());
//			user.setEmail(userDetails.getEmail());
//			user.setPassword(userDetails.getPassword());
//			return userRepository.save(user);
//		}).orElse(null);
//	}
	
	public UserEntity updateUser(@PathVariable Long id, @RequestBody UserEntity userDetails) {
	    return userRepository.findById(id).map(user -> {
	        if (userDetails.getUsername() != null && !userDetails.getUsername().isEmpty()) {
	            user.setUsername(userDetails.getUsername());
	        }
	        if (userDetails.getEmail() != null && !userDetails.getEmail().isEmpty()) {
	            user.setEmail(userDetails.getEmail());
	        }
	        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
	            user.setPassword(userDetails.getPassword());
	        }
	        return userRepository.save(user);
	    }).orElse(null);
	}

	
	public boolean deleteUser(@PathVariable Long id){
		if(userRepository.existsById(id)) {
		userRepository.deleteById(id);
		return true;
		}
		return false;
	}
						
}
