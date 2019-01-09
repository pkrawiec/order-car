package com.car.order.ordercar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.order.ordercar.model.User;
import com.car.order.ordercar.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String name, String password) {
        User user = new User();
        user.setPassword(name);
        user.setUsername(password);

        return userRepository.save(user);
    }

    public User login(String username, String password) throws Exception {
        User user = userRepository.findByUsernameAndPassword(username, password);

        if (user != null) {
            return user;
        }
        else {
            throw new Exception("bad user data");
        }
    }
}
