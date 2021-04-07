package com.itstep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Ckj13MvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ckj13MvcApplication.class, args);
	}

}
