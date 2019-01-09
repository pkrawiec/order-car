package com.car.order.ordercar.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Where;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter // generuje gettery
@Setter // generuje settery
@EqualsAndHashCode(callSuper = true)
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) }) // pole username musi byc unikalne w bazie
@Where(clause = "active = true")
public class ActivatedUser extends BaseUser {
	
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "owner")
	private List<Car> cars;
}
