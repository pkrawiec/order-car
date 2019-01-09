package com.car.order.ordercar.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.car.order.ordercar.model.Car;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCarLoanDto {

	private int carLoanId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date dateFrom;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date dateTo;
	
	private Car car;

	private BigDecimal pricePerDay;
	private BigDecimal priceSummary;
}
