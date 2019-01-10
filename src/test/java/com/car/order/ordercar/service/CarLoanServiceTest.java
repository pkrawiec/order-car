package com.car.order.ordercar.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.car.order.ordercar.dto.CarLoanCreationDto;
import com.car.order.ordercar.exception.CarLoanException;
import com.car.order.ordercar.model.ActivatedUser;
import com.car.order.ordercar.model.Car;
import com.car.order.ordercar.model.CarLoan;
import com.car.order.ordercar.repository.CarLoanRepository;

public class CarLoanServiceTest {
	
	static CarLoanService carLoanService;
	static CarService carService;
	static UserService userService;
	static CarLoanRepository carLoanRepository;
	CarLoanCreationDto carLoanCreationDto;

	@BeforeAll
	public static void setUpAll() {
		carService = Mockito.mock(CarService.class);
		userService = Mockito.mock(UserService.class);
		carLoanRepository = Mockito.spy(CarLoanRepository.class);
		
		carLoanService = new CarLoanService(carService, userService, carLoanRepository);
	}
	
	@BeforeEach
	public void setUpEach() {
		carLoanCreationDto = new CarLoanCreationDto();
	}

	@Test
	public void unsuccessfulTestCreateCarLoan() throws Exception {
		Date dateFrom = new Date();
		Date dateTo = addDays(dateFrom, -1);
		
		carLoanCreationDto.setDateFrom(dateFrom);
		carLoanCreationDto.setDateTo(dateTo);
		
		assertThrows(CarLoanException.class, () -> {
			carLoanService.createCarLoan(carLoanCreationDto);
		}, "car loan dates are not valid");
	}
	
	@Test
	public void successfulTestCreateCarLoan() throws CarLoanException {
		Date dateFrom = new Date();
		Date dateTo = addDays(dateFrom, 1);
		
		carLoanCreationDto.setDateFrom(dateFrom);
		carLoanCreationDto.setDateTo(dateTo);
		
		Car mockCar = new Car();
		mockCar.setPricePerDay(new BigDecimal("10"));
		when(carService.getCarById(Mockito.anyInt())).thenReturn(mockCar);
		when(userService.getUserFromSession()).thenReturn(new ActivatedUser());
		
		CarLoan newCarLoan = new CarLoan();
		newCarLoan.setPricePerDay(new BigDecimal("10"));
		when(carLoanRepository.save(Mockito.any(CarLoan.class))).thenReturn(newCarLoan);
		
		CarLoan createdCarLoan = carLoanService.createCarLoan(carLoanCreationDto);
		assertEquals(new BigDecimal("10"), createdCarLoan.getPricePerDay());
	}

	@Test
	public void successfulGetCarLoanDays() {
		Date dateFrom = new Date();
		Date dateTo = addDays(dateFrom, 2);

		CarLoan carLoan = new CarLoan();
		carLoan.setDateFrom(dateFrom);
		carLoan.setDateTo(dateTo);
		
		int carLoanDays = carLoanService.getCarLoanDays(carLoan);
		int expectedValue = 2;
		
		assertEquals(expectedValue, carLoanDays);
	}

	@Test
	public void successfulGetCarLoanSummaryPrice() {
		Date dateFrom = new Date();
		Date dateTo = addDays(dateFrom, 2);

		CarLoan carLoan = new CarLoan();
		carLoan.setDateFrom(dateFrom);
		carLoan.setDateTo(dateTo);
		carLoan.setPricePerDay(new BigDecimal("10"));
		
		BigDecimal summaryPrice = carLoanService.getCarLoanSummaryPrice(carLoan);
		BigDecimal expectedValue = new BigDecimal("20");
		
		assertEquals(expectedValue, summaryPrice);
	}
	
	static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
}