package com.vehicle.main.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

//import com.vehicle.main.service.VehicleService;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle.main.dto.VehicleDto;
import com.vehicle.main.entity.Vehicle;
import com.vehicle.main.entity.VehicleResponse;
import com.vehicle.main.exception.VehicleServiceException;
//import com.vehicle.main.service.VehiclServiceHandler;
import com.vehicle.main.service.impl.VehiclServiceHandler;

import lombok.extern.slf4j.Slf4j;

@RestController
@Validated
@RequestMapping("/v1/api/")
@Slf4j
// THIS CLASS ABLE TO PERFORM OPERATION FOR VEHICLE RESOURCE
public class VehicleResource {

	@Autowired
	private VehiclServiceHandler vehicleService;

//THIS METHOD WILL ABLE TO SEND DATA FROM SERVICE CLASS 
	@PostMapping(path = "vehicle", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<VehicleResponse> registerVehicle(@RequestBody @Valid VehicleDto vehicalDto)
			throws VehicleServiceException, ParseException {
		log.info(" VehicleDto {}" + vehicalDto);
		Vehicle vehicle = vehicleService.convertDtoToEntity(vehicalDto);
		Map<String, Object> saveVehicle = vehicleService.saveVehicle(vehicle);
		VehicleResponse response = new VehicleResponse();
		response.setSuccess(true);
		response.setResponse(saveVehicle);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

// THIS METHOD WILL ABLE TO GET DATA FROM SERVICE CLASS
	@GetMapping(path = "getAllVehicles", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<VehicleResponse> getAllVehicles() {
		log.info("The getAllVehicles() Enpoint start");
		List<VehicleDto> allVehicles = vehicleService.getAllVehicles();
		VehicleResponse response = new VehicleResponse();
		response.setSuccess(true);
		response.setResponse(allVehicles);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

//THIS METHOD WILL ABLE TO GET DATA FROM SERVICE CLASS
	@GetMapping(path = "searchVehicle", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<VehicleResponse> searchVehicleByVinOrRegNum(@RequestParam("vin") String vin,
			@RequestParam("regNumber") String reg_Number) {
		log.info("The searchVehicleByVinOrRegNum(-) Enpoint start");
		List<VehicleDto> allVehicles = vehicleService.searchVehicleByVinOrRegNum(vin, reg_Number);
		VehicleResponse response = new VehicleResponse();
		response.setSuccess(true);
		response.setResponse(allVehicles);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}