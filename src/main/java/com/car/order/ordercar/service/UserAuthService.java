package com.car.order.ordercar.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.car.order.ordercar.model.User;

public interface UserAuthService extends UserDetailsService {
	public User findByUsername(String email);
}
