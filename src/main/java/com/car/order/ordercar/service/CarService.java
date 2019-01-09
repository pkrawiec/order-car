package com.car.order.ordercar.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.order.ordercar.dto.CarCreationDto;
import com.car.order.ordercar.model.Car;
import com.car.order.ordercar.repository.CarRepository;

@Service
public class CarService {

	private CarRepository carRepository;
	private UserService userService;

	@Autowired
	public CarService(CarRepository carRepository, UserService userService) {
		this.carRepository = carRepository;
		this.userService = userService;
	}
	
	public Set<Car> getCarsByCurrentUser() {
		return carRepository.findAllByCurrentUser();
	}

	public List<Car> getAllCars() {
		return carRepository.findAll();
	}
	
	public Car getCarById(int id) {
		return carRepository.getOne(id);
	}
	
	public Car saveCar(CarCreationDto carCreationDto) {
		Car car = new Car();
		car.setManufacturer(carCreationDto.getManufacturer());
		car.setModel(carCreationDto.getModel());
		car.setName(carCreationDto.getName());
		car.setProductionYear(carCreationDto.getProductionYear());
		car.setOwner(userService.getUserFromSession());
		car.setPricePerDay(carCreationDto.getPricePerDay());
		car.setActive(true);
		
		return carRepository.save(car);
	}
	
	public Car editCar(CarCreationDto carCreationDto) {
		Car car = getCarById(carCreationDto.getId());
		car.setManufacturer(carCreationDto.getManufacturer());
		car.setModel(carCreationDto.getModel());
		car.setName(carCreationDto.getName());
		car.setProductionYear(carCreationDto.getProductionYear());
		car.setPricePerDay(carCreationDto.getPricePerDay());
		
		return carRepository.save(car);
	}
	
	public List<Car> getCarsForRent() {
		return carRepository.findAllCarsForRent();
	}
}
