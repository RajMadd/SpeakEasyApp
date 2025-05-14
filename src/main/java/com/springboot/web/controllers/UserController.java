package com.springboot.web.controllers;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.web.dao.UserRepository;
import com.springboot.web.entities.UserEntity;
import com.springboot.web.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/register")
	public ResponseEntity<UserEntity> registerUser(@RequestBody UserEntity user){
	    System.out.println("Received user: " + user.getUsername() + ", " + user.getEmail());
		UserEntity savedUser = userService.createUser(user);
	    return ResponseEntity.ok(savedUser);
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Map<String, String> loginData, HttpServletRequest request) {
	    String input = loginData.get("input"); // can be email or username
	    String password = loginData.get("password");

	    Optional<UserEntity> userOpt;

	    // Determine if input is email (basic check)
	    if (input.contains("@")) {
	        userOpt = userRepository.findByEmail(input);
	    } else {
	        userOpt = userRepository.findByUsername(input);
	    }

	    if (userOpt.isPresent()) {
	        UserEntity user = userOpt.get();
	        if (user.getPassword().equals(password)) { // Use password hashing in production
	            request.getSession().setAttribute("user", user);
	            return ResponseEntity.ok("Login successful");
	        }
	    }

	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	}


	@GetMapping("/{id}")
	public ResponseEntity<UserEntity> getUserById(@PathVariable Long id){
		Optional<UserEntity> user= userService.getUserById(id);
		return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@GetMapping
	public ResponseEntity<List<UserEntity>> getAllUsers(){
		return ResponseEntity.ok(userService.getAllUsers());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserEntity> updateUser(@PathVariable Long id,@RequestBody UserEntity user){
		UserEntity updatedUser=userService.updateUser(id, user);
		return updatedUser !=null ? ResponseEntity.ok(updatedUser): ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id){
		boolean isDeleted = userService.deleteUser(id);
		return isDeleted? ResponseEntity.ok("User deleted Successfully!"): ResponseEntity.notFound().build();
	}
	
	@GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate(); // Destroys the session
        return ResponseEntity.ok("Logged out");
    }
	
}
