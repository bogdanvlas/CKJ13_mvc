package com.itstep.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.itstep.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	public User findByUsername(String username);
}
