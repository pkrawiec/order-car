package com.car.order.ordercar.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.car.order.ordercar.dto.UserEditDto;
import com.car.order.ordercar.exception.UserException;
import com.car.order.ordercar.model.ActivatedUser;
import com.car.order.ordercar.service.UserService;

@Controller
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String getUsers(Model model, @RequestParam("page") Optional<Integer> page, 
      @RequestParam("size") Optional<Integer> size) {
		
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(5);
		
        Page<ActivatedUser> usersPage = userService.getUsers(PageRequest.of(currentPage, pageSize));
        
        model.addAttribute("usersPage", usersPage);
        
        int totalPages = usersPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages - 1)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
		
		return "admin/users";
	}
	
	@GetMapping("/{id}/admin/remove")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String removeUser(@PathVariable("id") int userId) {
		userService.deleteUser(userId);
		
		return "redirect:/users/admin?userdeleted";
	}
	
	@PostMapping("/{id}/admin/edit")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String editUser(@ModelAttribute("userDto") @Valid UserEditDto userDto) throws UserException {
		userService.editUser(userDto);
		
		return "redirect:/users/" + userDto.getId() +  "/admin?sukces";
	}
	
	@GetMapping("/{id}/admin")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String editUser(@PathVariable("id") int userId, Model model) throws UserException {
		UserEditDto userDto = userService.getUser(userId);
		model.addAttribute("userDto", userDto);
		
		return "admin/user-edit";
	}
}
