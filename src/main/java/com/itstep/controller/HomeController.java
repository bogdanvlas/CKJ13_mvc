package com.itstep.controller;

import java.net.http.HttpRequest;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itstep.model.User;
import com.itstep.repository.UserRepository;
import com.itstep.service.UserService;
import com.itstep.service.UsernameExistsException;

@Controller
public class HomeController {

	private UserRepository userRepository;

	private UserService userService;

	@Autowired
	public HomeController(UserRepository userRepository, UserService userService) {
		this.userRepository = userRepository;
		this.userService = userService;
	}

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
			HttpServletRequest request) {
		try {
			userService.register(username, password);
			request.login(username, password);
			return "redirect:/";
		} catch (UsernameExistsException e) {
			return "redirect:/signup?error";
		} catch (ServletException e) {
			e.printStackTrace();
			return "redirect:/login";
		}
	}
}
