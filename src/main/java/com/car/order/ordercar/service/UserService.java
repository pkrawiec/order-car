package com.car.order.ordercar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.car.order.ordercar.dto.UserRegistrationDto;
import com.car.order.ordercar.exception.UserException;
import com.car.order.ordercar.model.Role;
import com.car.order.ordercar.model.RoleName;
import com.car.order.ordercar.model.User;
import com.car.order.ordercar.repository.RoleRepository;
import com.car.order.ordercar.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepository userRepository, RoleRepository roleRepository,
			BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public UserService() {}

	public User registerUser(UserRegistrationDto userRegistrationDto) throws UserException {
		validateUserRegistrationDto(userRegistrationDto);
		
		String encodedPassword = encodePassword(userRegistrationDto.getPassword());

		User user = new User();
		user.setPassword(encodedPassword);
		user.setUsername(userRegistrationDto.getUsername());
		user.setRole(getUserRole());

		return userRepository.save(user);
	}
	
	private void validateUserRegistrationDto(UserRegistrationDto userRegistrationDto) throws UserException {
		if (userRegistrationDto == null || userRegistrationDto.getPassword() == null 
				|| userRegistrationDto.getUsername() == null) {
			
			throw new UserException("Validation exception thrown during user's registration");
		}
	}
	
	public boolean userExist(UserRegistrationDto userRegistrationDto) {
		return userRegistrationDto != null && 
				userRegistrationDto.getUsername() != null
				&& userRepository.existsByUsername(userRegistrationDto.getUsername());
	}

	public Role getUserRole() {
		return roleRepository.findByName(RoleName.ROLE_USER.name());
	}

	public String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}
	
	public User getUserFromSession() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = null;
		
		if (auth.getPrincipal().getClass().equals(String.class)) {
			username = (String) auth.getPrincipal();
		}
		if (auth.getPrincipal().getClass().equals(org.springframework.security.core.userdetails.User.class)) {
			username = ((UserDetails) auth.getPrincipal()).getUsername();
		}
        
        return userRepository.findByUsername(username);
	}
}
