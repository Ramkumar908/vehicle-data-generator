package com.vehicle.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class VehicleServiceException extends HttpStatusCodeException {

	public VehicleServiceException(HttpStatus statusCode) {
		super(statusCode);
		// TODO Auto-generated constructor stub
	}

	public VehicleServiceException(HttpStatus statusCode, String message) {
		super(statusCode, message);
		// TODO Auto-generated constructor stub
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

	

}
