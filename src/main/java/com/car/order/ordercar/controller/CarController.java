package com.car.order.ordercar.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.car.order.ordercar.dto.CarCreationDto;
import com.car.order.ordercar.model.Car;
import com.car.order.ordercar.model.RoleName;
import com.car.order.ordercar.service.CarService;
import com.car.order.ordercar.utils.AuthenticationUtils;

@Controller
@RequestMapping("/cars")
public class CarController {

	@Autowired
	private CarService carService;
	
	private static final String CAR_EDITOR_TEMPLATE = "car-editor";
	
	@GetMapping()
	public String getUserCars(Model model) {
		Set<Car> userCars = carService.getCarsByCurrentUser();
		model.addAttribute("allCars", userCars);
		
		return "cars";
	}
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String getAdminCars(Model model) {
		List<Car> userCars = carService.getAllCars();
		model.addAttribute("allCars", userCars);
		
		return "cars";
	}
	
	@GetMapping("/add-new")
	public String addNewCar(Model model) {
		model.addAttribute("carDto", new CarCreationDto());
		
		return CAR_EDITOR_TEMPLATE;
	}
	
	@GetMapping(params = {"carId"})
	public String editCar(@RequestParam("carId") int carId, Model model) {
		model.addAttribute("carDto", carService.getCarById(carId));
		
		return CAR_EDITOR_TEMPLATE;
	}
	
	@PostMapping("/save")
    public String postNewCar(@ModelAttribute("carDto") @Valid CarCreationDto carCreationDto, BindingResult result) {
    	
    	if (result.hasErrors()) {
    		return CAR_EDITOR_TEMPLATE;
    	}
    	if (carCreationDto.getId() != null) {
        	carService.editCar(carCreationDto);
    		
    	} else {
        	carService.saveCar(carCreationDto);
    	}
    	
    	if (AuthenticationUtils.hasRole(RoleName.ROLE_ADMIN)) {
    		return "redirect:/cars/admin?sukces";
    	}
    	
    	return "redirect:/cars?sukces";
    }
	
	@PostMapping("/edit")
    public String editCar(@ModelAttribute("carDto") @Valid CarCreationDto carCreationDto, BindingResult result) {
    	
    	if (result.hasErrors()) {
    		return CAR_EDITOR_TEMPLATE;
    	}
    	carService.editCar(carCreationDto);
    	
    	if (AuthenticationUtils.hasRole(RoleName.ROLE_ADMIN)) {
    		return "redirect:/cars/admin?sukcesEdit";
    	}
    	
    	return "redirect:/cars?sukcesEdit";
    }
}
