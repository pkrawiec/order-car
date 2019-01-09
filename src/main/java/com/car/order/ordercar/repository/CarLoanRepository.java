package com.car.order.ordercar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.car.order.ordercar.model.CarLoan;

@Repository
public interface CarLoanRepository extends JpaRepository<CarLoan, Integer>{
	
	@Query("SELECT carLoan FROM CarLoan carLoan WHERE carLoan.client.username = ?#{ authentication?.name } ")
	public List<CarLoan> getCarLoansByCurrentUser();
	
	@Query("SELECT carLoan FROM CarLoan carLoan WHERE carLoan.car.owner.username = ?#{ authentication?.name } ")
	public List<CarLoan> getCarLoansByCurrentOwner();
}
