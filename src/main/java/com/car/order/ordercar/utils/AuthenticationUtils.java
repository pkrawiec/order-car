package com.car.order.ordercar.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.car.order.ordercar.model.RoleName;
import com.car.order.ordercar.model.ActivatedUser;

public class AuthenticationUtils {
	private AuthenticationUtils() {}
	
	public static boolean isAuthenticated() {
		SecurityContext context = SecurityContextHolder.getContext();
		
		return context != null && context.getAuthentication() != null
				&& context.getAuthentication().isAuthenticated();
	}
	
	public static boolean hasRole(ActivatedUser user, RoleName roleName) {
		return user != null && user.getRole().getName().equals(roleName.name());
	}
	
	public static boolean hasRole(RoleName roleName) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        
        return userDetails != null && !userDetails.getAuthorities().isEmpty() 
        		&& userDetails.getAuthorities().iterator().next().getAuthority().equals(roleName.name());
	}
	
	public static void setAuthentication(String role, String username) {
		UsernamePasswordAuthenticationToken authentication = 
				new UsernamePasswordAuthenticationToken(username, null, getUserAuthoritiesList(role));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	private static List<GrantedAuthority> getUserAuthoritiesList(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));

        return authorities;
    }
}
