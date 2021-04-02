package com.itstep.controller;

import java.net.http.HttpRequest;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itstep.model.ConfirmToken;
import com.itstep.model.User;
import com.itstep.repository.ConfirmTokenRepository;
import com.itstep.repository.UserRepository;
import com.itstep.service.MailService;
import com.itstep.service.UserService;
import com.itstep.service.UsernameExistsException;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class HomeController {

	private UserRepository userRepository;

	private UserService userService;

	private ConfirmTokenRepository tokenRepository;

	private MailService mailService;

	@GetMapping
	public String index(Principal prl, Model model) {
		if (prl != null) {
			User user = userRepository.findByUsername(prl.getName());
			String msg = "Hello, " + user.getUsername();
			model.addAttribute("message", msg);
		}
		return "index";
	}

	@GetMapping("/admin_page")
	public String adminPage() {
		return "admin_page";
	}

	@GetMapping("/login")
	public String signIn() {
		return "login";
	}

	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}

	@PostMapping("/signup")
	public String registerNewUser(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("email") String email) {
		try {
			User user = userService.register(username, password, email);
			// создать токен для подтверждения почты
			ConfirmToken token = new ConfirmToken(user);
			// сохранить токен в базу
			tokenRepository.save(token);
			// отправить пользоваетелю на почту ссылку для подтверждения
			String confirmLink = "http://localhost:8085/confirm?token=" + token.getValue();
			String text = "Please, confirm your email via this link: " + confirmLink;

			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setSubject("Confirm your email");
			mail.setFrom("nobook@mail.com");
			mail.setTo(user.getEmail());
			mail.setText(text);
			mailService.sendMail(mail);
			return "redirect:/";
		} catch (UsernameExistsException e) {
			return "redirect:/signup?error";
		}
	}

	@GetMapping("/confirm")
	public String confirmEmailWithToken(@RequestParam("token") String tokenValue) {
		// извлечь значение токена из параметра запроса
		// проверить, что базе есть такой токен
		ConfirmToken token = tokenRepository.findByValue(tokenValue);
		if (token != null) {
			// если да, активировать аккаунт пользователя, с которым он связан
			User user = userRepository.findByUsername(token.getUser().getUsername());
			user.setEnabled(true);
			userRepository.save(user);
			tokenRepository.delete(token);
		}
		// удалить токен
		return "redirect:/login";
	}
}
