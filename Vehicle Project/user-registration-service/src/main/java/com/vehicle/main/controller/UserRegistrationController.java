package com.vehicle.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle.main.dto.UserDto;
import com.vehicle.main.response.UserResponse;
import com.vehicle.main.service.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/v2/api/user")
public class UserRegistrationController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponse> registerUser(@RequestBody UserDto user) {
		UserResponse userRespose1 = null;

		userRespose1 = userService.saveUser(user);
		return new ResponseEntity<UserResponse>(userRespose1, HttpStatus.OK);

	}
}
