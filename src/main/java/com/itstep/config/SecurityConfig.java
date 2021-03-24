package com.itstep.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.itstep.service.UserSecurityDetailsService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private UserSecurityDetailsService detailsService;
	
	@Autowired
	public SecurityConfig(UserSecurityDetailsService detailsService) {
		this.detailsService = detailsService;
	}


	// настраиваем АВТОРИЗАЦИЮ
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/notes/**").authenticated()
				.antMatchers("/admin_page/**").hasAuthority("ROLE_ADMIN")
				.and()
				.formLogin().loginPage("/login")
				.and()
				.logout()
				.and()
				.rememberMe().userDetailsService(detailsService)
				.and()
				.exceptionHandling().accessDeniedPage("/login?denied")
				.and()
				.csrf().disable();
	}

	// UserDetailsService - по username получает UserDetails
	// AuthenticationProvider - предоставляет информацию для аутентификации
	// AuthenticationManager - управляет процессом аутентификации
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(detailsService);
		provider.setPasswordEncoder(passwordEncoder());

		auth.authenticationProvider(provider);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
