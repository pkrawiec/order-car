package com.car.order.ordercar.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.car.order.ordercar.model.Role;
import com.car.order.ordercar.model.RoleName;
import com.car.order.ordercar.model.User;
import com.car.order.ordercar.repository.UserRepository;

public class UserAuthServiceImplTest {

	static User testUser;

	static final String TEST_PASSWORD = "test_password";
	static final String TEST_USERNAME = "test_username";
    
	static UserAuthServiceImpl userAuthService;
	static UserRepository userRepository;
	
	@BeforeAll
	public static void setUp() throws Exception {
		testUser = new User();
		testUser.setPassword(TEST_PASSWORD);
		testUser.setUsername(TEST_USERNAME);
    	
    	Role userRole = new Role();
    	userRole.setName(RoleName.ROLE_USER.name());
        testUser.setRole(userRole);

        userRepository = Mockito.mock(UserRepository.class);
        userAuthService = new UserAuthServiceImpl(userRepository);
	}

	@Test
	public void testFindByUsername() throws Exception {
		when(userRepository.findByUsername(Mockito.anyString())).thenReturn(testUser);
		String username = TEST_USERNAME;
		
		User resultUser = userAuthService.findByUsername(username);
		
		assertEquals("test_username", resultUser.getUsername());
		assertEquals(RoleName.ROLE_USER.name(), resultUser.getRole().getName());
	}
	
	@Test
	public void testLoadUserByUsername() throws Exception {
		when(userRepository.findByUsername(Mockito.anyString())).thenReturn(testUser);
		
		UserDetails userDetails = userAuthService.loadUserByUsername(TEST_USERNAME);
		
		assertEquals("test_username", userDetails.getUsername());
		assertEquals(RoleName.ROLE_USER.name(), userDetails.getAuthorities().iterator().next().getAuthority());
	}
	
	@Test
	public void unsuccessfulTestLoadUserByUsername() throws Exception {
		when(userRepository.findByUsername(Mockito.anyString())).thenReturn(null);
		
		assertThrows(UsernameNotFoundException.class, () -> {
			userAuthService.loadUserByUsername(TEST_USERNAME);
		}, "Niepoprawna nazwa uzytkownika lub haslo");
	}
}