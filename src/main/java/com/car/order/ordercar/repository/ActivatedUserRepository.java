package com.car.order.ordercar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.car.order.ordercar.model.ActivatedUser;

public interface ActivatedUserRepository extends JpaRepository<ActivatedUser, Integer> {
    
    public ActivatedUser findByUsername(String username);
    public boolean existsByUsername(String username);
}