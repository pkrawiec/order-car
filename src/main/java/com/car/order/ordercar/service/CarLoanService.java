package com.car.order.ordercar.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.order.ordercar.dto.CarLoanCreationDto;
import com.car.order.ordercar.dto.UserCarLoanDto;
import com.car.order.ordercar.exception.CarLoanException;
import com.car.order.ordercar.model.ActivatedUser;
import com.car.order.ordercar.model.Car;
import com.car.order.ordercar.model.CarLoan;
import com.car.order.ordercar.repository.CarLoanRepository;

@Service
public class CarLoanService {

	private CarService carService;
	private UserService userService;
	private CarLoanRepository carLoanRepository; 
	
	@Autowired
	public CarLoanService(CarService carService, UserService userService, CarLoanRepository carLoanRepository) {
		this.carService = carService;
		this.userService = userService;
		this.carLoanRepository = carLoanRepository;
	}

	public CarLoan createCarLoan(CarLoanCreationDto carLoanDto) throws CarLoanException {
		validateCarLoanDates(carLoanDto);
		
		Car car = carService.getCarById(carLoanDto.getCarId());
		ActivatedUser currentUser = userService.getUserFromSession();
		
		CarLoan carLoan = new CarLoan();
		carLoan.setCar(car);
		carLoan.setClient(currentUser);
		carLoan.setDateFrom(carLoanDto.getDateFrom());
		carLoan.setDateTo(carLoanDto.getDateTo());
		carLoan.setPricePerDay(car.getPricePerDay());
		
		return carLoanRepository.save(carLoan);
	}
	
	private void validateCarLoanDates(CarLoanCreationDto carLoanDto) throws CarLoanException {
		Date dateFrom = carLoanDto.getDateFrom();
		Date dateTo = carLoanDto.getDateTo();
		if (dateFrom == null || dateTo == null || dateFrom.after(dateTo)) {
			throw new CarLoanException("car loan dates are not valid");
		}
	}
	
	public List<UserCarLoanDto> getCurrentUserCarLoans() {
		List<CarLoan> carLoansByCurrentUser = carLoanRepository.getCarLoansByCurrentUser();

		return getCarLoanDtos(carLoansByCurrentUser);
	}
	
	public List<UserCarLoanDto> getCurrentOwnerCarLoans() {
		List<CarLoan> carLoansByCurrentOwner = carLoanRepository.getCarLoansByCurrentOwner();
		
		return getCarLoanDtos(carLoansByCurrentOwner);
	}
	
	private List<UserCarLoanDto> getCarLoanDtos(List<CarLoan> carLoans) {
		if (carLoans != null && !carLoans.isEmpty()) {
			return carLoans.stream()
				.map(this::getCarLoanDto)
				.collect(Collectors.toList());
		}
		return Collections.emptyList();
	}
	
	private UserCarLoanDto getCarLoanDto(CarLoan carLoan) {
		return UserCarLoanDto.builder()
				.car(carLoan.getCar())
				.carLoanId(carLoan.getId())
				.dateFrom(carLoan.getDateFrom())
				.dateTo(carLoan.getDateTo())
				.pricePerDay(carLoan.getPricePerDay())
				.priceSummary(getCarLoanSummaryPrice(carLoan))
				.build();
	}
	
	private BigDecimal getCarLoanSummaryPrice(CarLoan carLoan) {
		int carLoanDays = getCarLoanDays(carLoan);
		BigDecimal pricePerDayForCar = carLoan.getPricePerDay();
		
		return pricePerDayForCar.multiply(new BigDecimal(carLoanDays));
	}
	
	private int getCarLoanDays(CarLoan carLoan) {
		Date dateFrom = carLoan.getDateFrom();
		Date dateTo = carLoan.getDateTo();
		long diff = dateTo.getTime() - dateFrom.getTime();
	    
		return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
}
