package com.car.order.ordercar.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity // adnotacja odpowiedzialna za utworzenie modelu bazodanowego dla tabeli role
@Data	// adnotacja odpowiedzialna za wygenerowanie getterow i setterow (lombok)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
}
