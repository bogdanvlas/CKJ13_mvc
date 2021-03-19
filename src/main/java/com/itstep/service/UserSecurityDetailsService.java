package com.itstep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.itstep.model.User;
import com.itstep.model.UserSecurity;
import com.itstep.repository.UserRepository;

@Service
public class UserSecurityDetailsService implements UserDetailsService {

	private UserRepository userRepository;

	@Autowired
	public UserSecurityDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// найти пользователя в БД
		User user = userRepository.findByUsername(username);
		if (user == null) {
			// если пользователь не найден, бросить UsernameNotFoundException
			throw new UsernameNotFoundException(username);
		}
		// преобразовать его в UserDetails и вернуть из метода
		// UserDetails <-- user;
		return new UserSecurity(user);
	}

}
