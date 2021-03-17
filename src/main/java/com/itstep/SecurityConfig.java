package com.itstep;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	//настраиваем АВТОРИЗАЦИЮ
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/notes/**").authenticated()
		.antMatchers("/admin_page/**").hasRole("ADMIN")
		.and()
		.formLogin()
		.and()
		.logout()
		.and()
		.csrf().disable();
	}
	
	
	//настраиваем АУТЕНТИФИКАЦИЮ - проверка корректности пользователя
	// AuthenticationManager - управляет аутентификацией
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// описывает одного пользователя, понятного Spring Security
		UserDetails user1 = User.builder()
				.username("user")
				.password(passwordEncoder().encode("user"))
				.roles("USER")
				.build();

		UserDetails user2 = User.builder()
				.username("admin")
				.password(passwordEncoder().encode("admin"))
				.roles("ADMIN")
				.build();

		auth.inMemoryAuthentication().withUser(user1).withUser(user2);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
