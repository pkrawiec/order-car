package com.car.order.ordercar.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.car.order.ordercar.service.UserAuthService;

@Configuration
public class WebConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserAuthService userAuthService;
	
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers(
					"/",
					"/rejestracja",
					"/aktualnosci/**",
					"**/static").permitAll()
				.antMatchers("/webjars/**").permitAll()
				.anyRequest().authenticated()
			.and()
				.formLogin()
					.loginPage("/zaloguj").permitAll()
			.and()
				.logout()
					.invalidateHttpSession(true)
					.clearAuthentication(true)
					.logoutRequestMatcher(new AntPathRequestMatcher("/wyloguj"))
	                .logoutSuccessUrl("/?wylogowano")
				.permitAll();
	}
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userAuthService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authBuilder) throws Exception {
    	authBuilder.authenticationProvider(authenticationProvider());
    }
}