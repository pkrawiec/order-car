package com.car.order.ordercar.service;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.car.order.ordercar.model.ActivatedUser;
import com.car.order.ordercar.repository.ActivatedUserRepository;

@Service
public class UserAuthServiceImpl implements UserAuthService {
	
	private ActivatedUserRepository userRepository;
	
	@Autowired
	public UserAuthServiceImpl(ActivatedUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserDetails loadUserByUsername(String username) {
		ActivatedUser user = findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Niepoprawna nazwa uzytkownika lub haslo");
		}
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRole().getName()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(String role) {
		return Arrays.asList(new SimpleGrantedAuthority(role));
	}
	
	@Override
	public ActivatedUser findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
}