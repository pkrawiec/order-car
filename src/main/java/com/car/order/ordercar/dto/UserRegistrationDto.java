package com.car.order.ordercar.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserRegistrationDto {
	
	@NotBlank 
	private String username;
	@NotBlank 
	private String password;
}
