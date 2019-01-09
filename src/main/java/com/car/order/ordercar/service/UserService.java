package com.car.order.ordercar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.car.order.ordercar.dto.UserEditDto;
import com.car.order.ordercar.dto.UserRegistrationDto;
import com.car.order.ordercar.exception.UserException;
import com.car.order.ordercar.model.ActivatedUser;
import com.car.order.ordercar.model.Car;
import com.car.order.ordercar.model.Role;
import com.car.order.ordercar.model.RoleName;
import com.car.order.ordercar.repository.ActivatedUserRepository;
import com.car.order.ordercar.repository.RoleRepository;
import com.car.order.ordercar.repository.UserRepository;

@Service
public class UserService {

	private ActivatedUserRepository activatedUserRepository;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserService(ActivatedUserRepository activatedUserRepository, RoleRepository roleRepository,
			BCryptPasswordEncoder passwordEncoder, UserRepository userRepository) {
		this.activatedUserRepository = activatedUserRepository;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public UserService() {}

	public ActivatedUser registerUser(UserRegistrationDto userRegistrationDto) throws UserException {
		validateUserRegistrationDto(userRegistrationDto);
		
		String encodedPassword = encodePassword(userRegistrationDto.getPassword());

		ActivatedUser user = new ActivatedUser();
		user.setPassword(encodedPassword);
		user.setUsername(userRegistrationDto.getUsername());
		user.setRole(getUserRole());
		user.setActive(true);

		return activatedUserRepository.save(user);
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
	
	public Page<ActivatedUser> getUsers(Pageable pageable) {
		return activatedUserRepository.findAll(pageable);
	}
	
	public void deleteUser(int id) {
		ActivatedUser user = activatedUserRepository.getOne(id);
		List<Car> userCars = user.getCars();
		if (userCars != null && !userCars.isEmpty()) {
			userCars.forEach(car -> car.setActive(false));
		}
		user.setActive(false);
		
		activatedUserRepository.save(user);
	}
	
	public UserEditDto getUser(int id) throws UserException {
		ActivatedUser user = activatedUserRepository.getOne(id);
		if (user != null) {
			UserEditDto userEditDto = new UserEditDto();
			userEditDto.setId(user.getId());
			userEditDto.setFirstName(user.getFirstName());
			userEditDto.setLastName(user.getLastName());
			userEditDto.setUsername(user.getUsername());
			
			return userEditDto;
		}
		throw new UserException("user not found exception");
	}
	
	public ActivatedUser getUserFromSession() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = null;
		
		if (auth.getPrincipal().getClass().equals(String.class)) {
			username = (String) auth.getPrincipal();
		}
		if (auth.getPrincipal().getClass().equals(org.springframework.security.core.userdetails.User.class)) {
			username = ((UserDetails) auth.getPrincipal()).getUsername();
		}
        
        return activatedUserRepository.findByUsername(username);
	}

	public ActivatedUser editUser(UserEditDto userDto) throws UserException {
		ActivatedUser user = activatedUserRepository.getOne(userDto.getId());
		if (user != null) {
			user.setFirstName(userDto.getFirstName());
			user.setLastName(userDto.getLastName());
			user.setUsername(userDto.getUsername());
			
			return activatedUserRepository.save(user);
		}
		throw new UserException("user not found exception");
	}
}
