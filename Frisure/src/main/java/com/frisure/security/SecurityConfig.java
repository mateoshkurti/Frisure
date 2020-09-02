package com.frisure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.frisure.services.UserDetailsServiceImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired 
	private LoginAccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.headers().frameOptions().disable();

		http.formLogin().defaultSuccessUrl("/home", true);
		
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/register").permitAll()
		.antMatchers("secure/**","/secure/serviceProvider/**").hasRole("SP")
		.antMatchers("secure/**","/secure/user/**").hasRole("user")
				.antMatchers("/", "/js/**", "/css/**", "/images/**", "/**","/fragments/**").permitAll().antMatchers("/h2-console/**")
				.permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll().and()
				.logout().invalidateHttpSession(true).clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
				.permitAll().and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);
		
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}
