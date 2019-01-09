package com.car.order.ordercar.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.car.order.ordercar.dto.UserRegistrationDto;
import com.car.order.ordercar.exception.UserException;
import com.car.order.ordercar.service.UserService;

@Controller
public class MainController {

	@Autowired
	private UserService userService;
	
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
    	model.addAttribute("userRegistrationDto", new UserRegistrationDto());
    	
        return "register";
    }
    
    @GetMapping("/admin/panel")
    public String getAdminPanel(Model model) {
        return "admin-panel";
    }
    
    @PostMapping("/register")
    public String postRegisterForm(@ModelAttribute("userRegistrationDto") @Valid UserRegistrationDto userRegistrationDto,
    		BindingResult result) throws UserException {
    	
    	if (userService.userExist(userRegistrationDto)) {
    		result.rejectValue("username", null, "Istnieje juz konto o podanej nazwie");
    	}
    	
    	if (result.hasErrors()) {
    		return "register";
    	}
    	
    	userService.registerUser(userRegistrationDto);
    	
    	return "redirect:/register?sukces";
    }
}