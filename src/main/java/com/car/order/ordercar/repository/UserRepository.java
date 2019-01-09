package com.car.order.ordercar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.car.order.ordercar.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    public User findByUsername(String username);
    public boolean existsByUsername(String username);
}