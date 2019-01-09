package com.car.order.ordercar.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@MappedSuperclass
@Data
public class BaseUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank // adnotacja sluzaca walidacji pola, czy nie jest puste ani nie jest nullem
	@Column(unique = true)
	private String username;

	@NotBlank
	@JsonIgnore
	private String password;
	
	private String firstName;
	private String lastName;

	private boolean active;
	
	@OneToOne
	private Role role;
}
