package com.car.order.ordercar.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserRegistrationDto extends UserEditDto {
	
	@NotBlank 
	private String password;
}
