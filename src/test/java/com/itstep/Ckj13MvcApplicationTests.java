package com.itstep;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.itstep.service.MailService;

@SpringBootTest
class Ckj13MvcApplicationTests {
	private MailService mailService;

	@Autowired
	public Ckj13MvcApplicationTests(MailService mailService) {
		this.mailService = mailService;
	}

	@Test
	void sendMeailTest() {
		SimpleMailMessage mail = new SimpleMailMessage();
		String text = "This mail is sent from java test";

		mail.setFrom("sender@mail.com");
		mail.setTo("receiver@mail.com");
		mail.setText(text);
		mail.setSubject("Test email");

		System.out.println("Try to send mail");
		mailService.sendMail(mail);
		System.out.println("Test done!");
	}

	@Test
	@Disabled
	void testPasswordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String str = "admin";
		System.out.println("Encoded string: " + str);
		System.out.println(encoder.encode(str));
	}
}
