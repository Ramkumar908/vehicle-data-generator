package com.vehicle.main.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vehicle.main.config.PropertyConfig;
import com.vehicle.main.dto.UserDto;
import com.vehicle.main.entity.Role;
import com.vehicle.main.entity.User;
import com.vehicle.main.entity.UserRole;
import com.vehicle.main.repository.RoleRepository;
import com.vehicle.main.repository.UserRepository;
import com.vehicle.main.response.UserResponse;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PropertyConfig props;

	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	//@Transactional(readOnly = false)
	public UserResponse saveUser(UserDto userDto) {
		UserResponse response = null;
		Set<UserRole> roles = null;
		UserRole userRole = null;
		User existUser = null;
		User user = null;
		Role role = null;

		existUser = userRepository.findByUsername(userDto.getUsername());
		response = new UserResponse();

		try {

			if (existUser == null) {
				user = new User();
				user.setName(userDto.getName());
				user.setUsername(userDto.getUsername());
				user.setPassword(passwordEncoder.encode(userDto.getPassword()));
				user.setMobileNumber(userDto.getMobileNo());
				user.setEmail(userDto.getEmail());

				role = new Role();
				role.setRoleId(222L);
				role.setRoleType("USER");

				roleRepo.save(role);

				roles = new HashSet<>();
				userRole = new UserRole();
				userRole.setUser(user);
				userRole.setRole(role);
				roles.add(userRole);
				user.setRoles(roles);

				for (UserRole userR : user.getRoles()) {
					roleRepo.save(userR.getRole());
				}

				user = userRepository.save(user);

				response.setSuccess(true);
				response.setResponse(props.getConditionTrue());

			} else {

				response.setSuccess(false);
				response.setResponse(props.getConditionFalse());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + e.getStackTrace().toString());
		}

		return response;

	}

}
