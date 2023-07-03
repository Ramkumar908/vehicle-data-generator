package com.vehicle.main.service.impl;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.vehicle.main.entity.User;
import com.vehicle.main.entity.UserRole;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private Set<UserRole> userRoles;

	public CustomUserDetails(User user) {
		super();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.userRoles = user.getUserRoles();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		log.trace("The getAuthorities () method start");
		return userRoles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().getRoleName()))
				.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
