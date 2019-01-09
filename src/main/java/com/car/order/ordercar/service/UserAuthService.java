package com.car.order.ordercar.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.car.order.ordercar.model.ActivatedUser;

public interface UserAuthService extends UserDetailsService {
	public ActivatedUser findByUsername(String email);
}
