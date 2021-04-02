package com.itstep.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ConfirmToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String value;
	private LocalDateTime createdAt;

	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	private User user;

	public ConfirmToken(User user) {
		this.value = UUID.randomUUID().toString();
		this.createdAt = LocalDateTime.now();
		this.user = user;
	}

}
