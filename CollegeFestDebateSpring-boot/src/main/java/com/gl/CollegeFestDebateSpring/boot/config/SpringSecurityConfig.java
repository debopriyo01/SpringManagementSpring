package com.gl.CollegeFestDebateSpring.boot.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gl.CollegeFestDebateSpring.boot.service.UserDetailsServiceImpl;
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		http.authorizeRequests().antMatchers("/h2-console/**").permitAll()
		.and().authorizeRequests()
		.antMatchers("/","/students/save","/students/showFormForAdd","/students/403").hasAnyAuthority("USER","ADMIN")
        .antMatchers("/students/update","/students/delete").hasAuthority("ADMIN")
        .anyRequest().authenticated()
        .and()
		.formLogin().loginProcessingUrl("/login").successForwardUrl("/students/list").and().logout().logoutSuccessUrl("/login").permitAll()
		  .and()
          .exceptionHandling().accessDeniedPage("/students/403");
      	http.csrf().disable();
        http.headers().frameOptions().disable();
		
	}

	
}
