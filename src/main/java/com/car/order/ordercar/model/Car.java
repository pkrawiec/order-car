package com.car.order.ordercar.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;

	@NotNull
	private Integer productionYear;

	@NotBlank
	private String model;
	
	@NotBlank
	private String manufacturer;
	
	@NotNull
	@ManyToOne(optional = false)
	private User owner;
}
