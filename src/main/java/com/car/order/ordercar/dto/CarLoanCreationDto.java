package com.car.order.ordercar.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CarLoanCreationDto {

	private int carId;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date dateFrom;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date dateTo;
}
