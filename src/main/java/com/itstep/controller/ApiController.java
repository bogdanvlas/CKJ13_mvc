package com.itstep.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itstep.model.User;
import com.itstep.repository.UserRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ApiController {

	private UserRepository userRepository;

	@GetMapping("/users")
	public Iterable<User> getUsers() {
		return userRepository.findAll();
	}
}
