package com.itstep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	private JavaMailSender javaMailSender;

	@Autowired
	public MailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Async
	public void sendMail(SimpleMailMessage mail) {
		System.out.println("Send email from " + Thread.currentThread().getName());
		javaMailSender.send(mail);
	}

}
