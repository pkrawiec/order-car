package com.car.order.ordercar.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserEditDto {
	
	private int id;
	
	@NotBlank 
	private String username;
	
	private String firstName;
	private String lastName;
}
