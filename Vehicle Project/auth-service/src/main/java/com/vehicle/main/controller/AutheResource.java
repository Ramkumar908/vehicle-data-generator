package com.vehicle.main.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle.main.pojo.AuthRequest;
import com.vehicle.main.service.impl.UserServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth/")
@Slf4j
public class AutheResource {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping(path = "token", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> getToken(@RequestBody AuthRequest authRequest) {
		try {
			String generateToken = "";
			String userRole = "";
			Map<String, Object> respMap = new HashMap<>();
			log.trace("The getToken() Endpoint start");

			String username = authRequest.getUsername();
			String password = authRequest.getPassword();
			log.info("the authenticate username  {}" + authRequest.getUsername());

			Authentication authenticate = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));

			if (authenticate.isAuthenticated()) {
				generateToken = userServiceImpl.generateToken(username);
				List<String> userByRole = userServiceImpl.getUserByRole(username);
				log.info("the userRole   {}" + userByRole);
				for (String string : userByRole) {
					userRole = string;
				}
				respMap.put("userRole", userRole);
				respMap.put("token", generateToken);
			}
			return new ResponseEntity<>(respMap, HttpStatus.OK);
		} catch (Exception e) {
			log.error("the userRole   {}" + "INTERNAL_SERVER_ERROR");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping(path = "validate-token", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> validateToken(@RequestParam("token") String token) {
		try {
			log.info("The getToken() Enpoint start");
			String generateToken = userServiceImpl.validateToken(token);
			log.trace("The Genarte token " + generateToken);
			return new ResponseEntity<>(generateToken, HttpStatus.OK);
		} catch (Exception e) {
			log.error("INTERNAL_SERVER_ERROR");
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
