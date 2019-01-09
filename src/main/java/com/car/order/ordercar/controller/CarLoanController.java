package com.car.order.ordercar.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.car.order.ordercar.dto.CarLoanCreationDto;
import com.car.order.ordercar.dto.UserCarLoanDto;
import com.car.order.ordercar.exception.CarLoanException;
import com.car.order.ordercar.model.Car;
import com.car.order.ordercar.service.CarLoanService;
import com.car.order.ordercar.service.CarService;

@Controller
@RequestMapping("/car-loan")
public class CarLoanController {

	@Autowired
	private CarLoanService carLoanService;
	@Autowired
	private CarService carService;

	private static final String CAR_RENT_TEMPLATE = "cars/rent-car";
	private static final String CLIENT_CAR_LOANS_TEMPLATE = "car-loans/user-car-loans";
	private static final String OWNER_CAR_LOANS_TEMPLATE = "car-loans/owner-car-loans";

	@GetMapping("/{carId}/rent")
	public String getRentCarPage(@PathVariable("carId") int carId, Model model) {
		Car car = carService.getCarById(carId);
		model.addAttribute("carDto", car);
		model.addAttribute("carLoanDto", new CarLoanCreationDto());

		return CAR_RENT_TEMPLATE;
	}

	@PostMapping("/{carId}/rent")
	public String saveNewCarLoan(@ModelAttribute("carLoanDto") @Valid CarLoanCreationDto carLoanCreationDto,
			@PathVariable("carId") int carId, Model model, BindingResult result) {

		if (result.hasErrors()) {
			Car car = carService.getCarById(carId);
			model.addAttribute("carDto", car);
			return CAR_RENT_TEMPLATE;
		}
		try {
			carLoanService.createCarLoan(carLoanCreationDto);
		} catch (CarLoanException e) {
			return String.format("redirect:/car-loan/%d/rent?datesError", carId);
		}

		return "redirect:/?successLoan";
	}
	
	@GetMapping("/client")
	public String getClientCarLoansPage(Model model) {
		List<UserCarLoanDto> currentUserCarLoans = carLoanService.getCurrentUserCarLoans();
		model.addAttribute("userCarLoans", currentUserCarLoans);

		return CLIENT_CAR_LOANS_TEMPLATE;
	}
	
	@GetMapping("/owner")
	public String getOwnerCarLoansPage(Model model) {
		List<UserCarLoanDto> currentUserCarLoans = carLoanService.getCurrentOwnerCarLoans();
		model.addAttribute("ownerCarLoans", currentUserCarLoans);

		return OWNER_CAR_LOANS_TEMPLATE;
	}
}
