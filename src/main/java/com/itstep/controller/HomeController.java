package com.itstep.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping
	public String index() {
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
}
