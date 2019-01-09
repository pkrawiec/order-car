package com.car.order.ordercar.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.car.order.ordercar.dto.UserRegistrationDto;
import com.car.order.ordercar.exception.UserException;
import com.car.order.ordercar.model.Role;
import com.car.order.ordercar.model.RoleName;
import com.car.order.ordercar.model.ActivatedUser;
import com.car.order.ordercar.repository.RoleRepository;
import com.car.order.ordercar.repository.ActivatedUserRepository;

public class UserServiceTest {

    static UserService userService;
    static ActivatedUserRepository userRepository;
    static BCryptPasswordEncoder bCryptPasswordEncoder;
    static RoleRepository roleRepository;
    
    static ActivatedUser testUser;
    static UserRegistrationDto registrationDto;
    
    final String TEST_PASSWORD = "test_password";
    final String TEST_USERNAME = "test_username";
    
    // metoda odpalana przed wszystkimi testami 1x - inicjalizacja
    @BeforeAll
    public static void setUp() {
        userRepository = Mockito.mock(ActivatedUserRepository.class);
        bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        roleRepository = Mockito.mock(RoleRepository.class);
        
        userService = new UserService(userRepository, roleRepository, bCryptPasswordEncoder, null);
    }

    // metoda odpalana przed kazdym testem testem
    @BeforeEach
    public void setUpEach() {
    	testUser = new ActivatedUser();
    	testUser.setPassword(BCrypt.hashpw(TEST_PASSWORD, BCrypt.gensalt()));
    	testUser.setUsername(TEST_USERNAME);
    	
    	Role userRole = new Role();
    	userRole.setName(RoleName.ROLE_USER.name());
        testUser.setRole(userRole);
    	
        registrationDto = new UserRegistrationDto();
        registrationDto.setPassword(TEST_PASSWORD);
        registrationDto.setUsername(TEST_USERNAME);
    }

    @Test
    public void successfulRegisterUser() throws UserException {
        Mockito.when(userRepository.save(Mockito.any(ActivatedUser.class))).thenReturn(testUser);

        ActivatedUser resultUser = userService.registerUser(registrationDto);

        Assertions.assertTrue(BCrypt.checkpw(registrationDto.getPassword(), resultUser.getPassword()));
        Assertions.assertEquals(resultUser.getUsername(), registrationDto.getUsername());
        Assertions.assertEquals(RoleName.ROLE_USER.name(), resultUser.getRole().getName());
    }
    
    @Test
    public void successfulEncodePassword() {
        Mockito.when(bCryptPasswordEncoder.encode(Mockito.anyString())).thenCallRealMethod();
    	String encodedPassword = userService.encodePassword(TEST_PASSWORD);
    	
    	Assertions.assertTrue(BCrypt.checkpw(TEST_PASSWORD, encodedPassword));
    }
    
    @Test
    public void successfulGetUserRole() {
    	Role userRole = new Role();
    	userRole.setName(RoleName.ROLE_USER.name());
    	
        Mockito.when(roleRepository.findByName(RoleName.ROLE_USER.name())).thenReturn(userRole);
    	
        Role resultRole = userService.getUserRole();
        
    	Assertions.assertEquals(RoleName.ROLE_USER.name(), resultRole.getName());
    }
    
    @Test
    public void unsuccessfulRegisterUser_emptyPassword() throws UserException {
    	UserRegistrationDto registrationDto = new UserRegistrationDto();
    	registrationDto.setUsername(TEST_USERNAME);
    	registrationDto.setPassword(null);
    	
    	assertThrows(UserException.class, () -> {
    		userService.registerUser(registrationDto);
    	}, "Validation exception thrown during user's registration");
    }
}
