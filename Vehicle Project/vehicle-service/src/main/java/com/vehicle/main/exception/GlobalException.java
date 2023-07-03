package com.vehicle.main.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.vehicle.main.entity.VehicleResponse;

@ControllerAdvice
public class GlobalException {

	@Autowired
	private Environment env;
	

	@ExceptionHandler(VehicleServiceException.class)
	public ResponseEntity<VehicleResponse> handleMethodNotAllowed(VehicleServiceException a) {
		
		VehicleResponse response = new VehicleResponse();
		response.setSuccess(false);
		if (a.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
			response.setResponse(env.getProperty("9000"));
			return ResponseEntity.badRequest().body(response);
		}
		if (a.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
			response.setResponse(env.getProperty("9001"));
			return ResponseEntity.badRequest().body(response);
		}
		if (a.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
			response.setResponse(env.getProperty("9002"));
			return ResponseEntity.badRequest().body(response);
		}
		response.setResponse(a.getMessage());
		return ResponseEntity.internalServerError().body(response);
	}

}