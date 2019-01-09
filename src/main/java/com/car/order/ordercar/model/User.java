package com.car.order.ordercar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter // generuje gettery
@Setter // generuje settery
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) }) // pole username musi byc unikalne w bazie
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank // adnotacja sluzaca walidacji pola, czy nie jest puste ani nie jest nullem
	@Column(unique = true)
	private String username;

	@NotBlank
	private String password;

	@OneToOne
	private Role role;
}
