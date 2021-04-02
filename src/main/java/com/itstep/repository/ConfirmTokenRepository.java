package com.itstep.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.itstep.model.ConfirmToken;
import com.itstep.model.User;

@Repository
public interface ConfirmTokenRepository extends CrudRepository<ConfirmToken, Integer> {
	ConfirmToken findByUser(User user);

	ConfirmToken findByValue(String value);
}
