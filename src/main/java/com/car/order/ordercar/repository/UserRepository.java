package com.car.order.ordercar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.car.order.ordercar.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
    public boolean existsByUsername(String username);
}
