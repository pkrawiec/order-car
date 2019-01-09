package com.car.order.ordercar.repository;

import com.car.order.ordercar.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByUsernameAndPassword(String username, String password);
    
    public User findByUsername(String username);
}