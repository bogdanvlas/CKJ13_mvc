package com.itstep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.itstep.model.User;
import com.itstep.repository.UserRepository;

@Service
public class UserService {
	private UserRepository userRepository;

	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User register(String username, String password) {
		// 1. Проверить, что юзернейм свободен
		User user = userRepository.findByUsername(username);
		if (user == null) {
			// Если свободен, продолжу регистрацию
			user = new User();
			user.setUsername(username);
			// 2. Закодировать пароль\
			user.setPassword(passwordEncoder.encode(password));
			// Присвоить роль ROLE_USER
			user.setRole("ROLE_USER");
			// сохранить пользователя в базе и вернуть из метода
			return userRepository.save(user);
		} else {
			// Если занят, кину эксепшн, который обротаю в контроллере
			throw new UsernameExistsException(username);
		}
	}
}
