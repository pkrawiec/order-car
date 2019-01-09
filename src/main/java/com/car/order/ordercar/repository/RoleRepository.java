package com.car.order.ordercar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.car.order.ordercar.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

	Role findByName(String name); // generuje pod spodem zapytanie do bazy do wyszukania roli uzytkownika po nazwie
}
