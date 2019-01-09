package com.car.order.ordercar.service;

import com.car.order.ordercar.model.User;
import com.car.order.ordercar.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

public class UserServiceTest {

    static UserService userService;
    static UserRepository userRepository;
    static User testUser;

    // metoda odpalana przed wszystkimi testami 1x - inicjalizacja
    @BeforeAll
    public static void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository);
    }


    @BeforeEach
    public void setUpEach() {
        testUser = new User();
        testUser.setUsername("test_user");
        testUser.setPassword("test_password");
    }

    @Test
    public void successfulTest_saveUser() throws Exception {
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(testUser);

        User resultUser = userService.login("test_user", "test_password");

        Assertions.assertEquals(resultUser.getPassword(), testUser.getPassword());
        Assertions.assertEquals(resultUser.getUsername(), testUser.getUsername());
    }
}
