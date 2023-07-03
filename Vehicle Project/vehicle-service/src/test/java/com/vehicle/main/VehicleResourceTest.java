package com.vehicle.main;

import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.vehicle.main.controller.VehicleResource;
import com.vehicle.main.dto.VehicleDto;
import com.vehicle.main.entity.Vehicle;
import com.vehicle.main.entity.VehicleResponse;
import com.vehicle.main.exception.VehicleServiceException;
import com.vehicle.main.pojo.SearchRequest;
import com.vehicle.main.service.impl.VehiclServiceHandler;

class VehicleResourceTest {
	@Mock
	VehiclServiceHandler vehicleService;
	@Mock
	Logger LOGGER;
	@InjectMocks
	VehicleResource vehicleResource;

	@Autowired
	private ModelMapper modelMapper;

	@InjectMocks
	private ModelMapper modelMapper1;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testRegisterVehicle() throws VehicleServiceException, ParseException {
		long price = 0;
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Vehicle vehicle = new Vehicle(price, "vin", "regNumber", timestamp, "brand", timestamp, price, "currency", true,
				timestamp, "warrantyCoverage", "sellingDealer", "createdUser", timestamp.toLocalDateTime());
		VehicleDto vehicleDto = this.modelMapper1.map(vehicle, VehicleDto.class);
		// Map<String,Object> resultMap=Map.of("String", "saveVehicleResponse");
		Map<String, Object> resultMap = Map.of();
		when(vehicleService.saveVehicle(vehicle)).thenReturn(resultMap);

		ResponseEntity<VehicleResponse> result = vehicleResource.registerVehicle(vehicleDto);
		Assertions.assertEquals(new ResponseEntity(resultMap, HttpStatus.OK), result);
	}

	@Test
	void testGetAllVehicles() throws VehicleServiceException {
		long price = 0;
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		List<Vehicle> list = Arrays
				.asList(new Vehicle(price, "vin", "regNumber", timestamp, "brand", timestamp, price, "currency", true,
						timestamp, "warrantyCoverage", "sellingDealer", "createdUser", timestamp.toLocalDateTime()));
		List<VehicleDto> vehicleDtos = list.stream().map(m -> modelMapper1.map(m, VehicleDto.class))
				.collect(Collectors.toList());
		when(vehicleService.getAllVehicles()).thenReturn(vehicleDtos);

		ResponseEntity<VehicleResponse> result = vehicleResource.getAllVehicles();
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
		Assertions.assertEquals(vehicleDtos, result.getBody());
	}

	@Test
	void testSearchVehicleByVinOrRegNum() throws VehicleServiceException {
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.setReg_Number("WB24B2620");
		searchRequest.setVin("");

		long price = 0;
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		List<Vehicle> list = Arrays
				.asList(new Vehicle(price, "vin", "regNumber", timestamp, "brand", timestamp, price, "currency", true,
						timestamp, "warrantyCoverage", "sellingDealer", "createdUser", timestamp.toLocalDateTime()));
		List<VehicleDto> vehicleDtos = list.stream().map(m -> modelMapper1.map(m, VehicleDto.class))
				.collect(Collectors.toList());

		when(vehicleService.searchVehicleByVinOrRegNum("WB24B2620", "")).thenReturn(vehicleDtos);

		ResponseEntity<VehicleResponse> result = vehicleResource.searchVehicleByVinOrRegNum("WB24B2620", "");
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
		Assertions.assertEquals(vehicleDtos, result.getBody());
	}

}
