package com.car.order.ordercar.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
// adnotacja entity odpowiedzialna za utworzenie modelu bazodanowego dla tabeli role
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // kolumna w bazie -> role_name
    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private RoleName roleName;
}
