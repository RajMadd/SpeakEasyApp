package com.springboot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.springboot.web.dao.UserRepository;

@SpringBootApplication
public class SpeakEasyAppApplication {

	public static void main(String[] args) {
		ApplicationContext context=SpringApplication.run(SpeakEasyAppApplication.class, args);
		UserRepository userRepository=context.getBean(UserRepository.class);
		
	}

}
