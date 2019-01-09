package com.car.order.ordercar.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CarCreationDto {
	
	private Integer id;
	
	private String name;

	@NotNull
	private Integer productionYear;

	@NotBlank
	private String model;
	
	@NotBlank
	private String manufacturer;
}
