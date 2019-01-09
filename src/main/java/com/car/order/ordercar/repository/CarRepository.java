package com.car.order.ordercar.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.car.order.ordercar.model.Car;

public interface CarRepository extends JpaRepository<Car, Integer> {

	@Query("SELECT car FROM Car car WHERE car.owner.username = ?#{ authentication?.name } ")
	Set<Car> findAllByCurrentUser();
	
	@Query("SELECT car FROM Car car WHERE car.owner.username != ?#{ authentication?.name } ")
	List<Car> findAllCarsForRent();
}