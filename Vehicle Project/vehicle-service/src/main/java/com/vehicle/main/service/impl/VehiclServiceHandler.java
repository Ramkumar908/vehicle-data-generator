package com.vehicle.main.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.vehicle.main.dto.VehicleDto;
import com.vehicle.main.entity.Vehicle;
import com.vehicle.main.exception.VehicleServiceException;
import com.vehicle.main.repository.VehicleRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
// THIS CLASS IS USED FOR REGISTER VEHICLE DATA AND FETCH THAT DATA
public class VehiclServiceHandler {

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private ModelMapper modelMapper;

//	THIS METHOD WILL ABLE TO GET DATA FROM REPOSITORY
	public Map<String, Object> saveVehicle(Vehicle vehicle) throws VehicleServiceException {
		try {
			Vehicle createdVehical = null;
			Map<String, Object> resultMap = new HashMap<>();
			log.info("saveVehicle(-) method started");
			vehicle.setVin(vehicle.getVin().toUpperCase());
			createdVehical = vehicleRepository.save(vehicle);
			if (createdVehical != null) {
				resultMap.put("message", "Vehicle Created Successfully");
//				resultMap.put("isVehicleCreated", true);
				log.trace("Vehicle Created {}" + true);
			}
			log.warn("saveVehicle(-) method end");
			return resultMap;
		} catch (Exception e) {
			log.error("Exception occured while saving the vehicle,{}", e.getMessage());
			throw new VehicleServiceException(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

//THIS METHOD WILL ABLE TO GET DATA FROM REPOSITORY
	public List<VehicleDto> getAllVehicles() throws VehicleServiceException {
		try {
			List<Vehicle> listVehicles = null;
			log.trace("getAllVehicles() method start");
			listVehicles = vehicleRepository.findAll();
			log.trace("getAllVehicles() method end");
			// return listVehicles.stream().map(m -> modelMapper.map(m,
			// VehicleDto.class)).collect(Collectors.toList());
			return formatedVehicleData(listVehicles);
		} catch (Exception e) {
			log.error("Exception occured while geting the vehicle data,{}", e.getMessage());
			throw new VehicleServiceException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//THIS METHOD WILL ABLE TO GET DATA FROM REPOSITORY
	public List<VehicleDto> searchVehicleByVinOrRegNum(String vin, String regNumber) throws VehicleServiceException {
		try {
			List<Vehicle> listVehicles = null;
			log.trace("searchVehicleByVinOrRegNum(-,-) method start");
			if (!StringUtils.isEmpty(vin)) {
				vin = vin.toUpperCase();

				listVehicles = vehicleRepository.findByVin(vin);
				if (!StringUtils.isEmpty(regNumber)) {
					listVehicles = listVehicles.stream().filter(t -> t.getReg_Number().contains(regNumber))
							.collect(Collectors.toList());
				}

				if (listVehicles.isEmpty()) {
					log.warn("Vehicle Not Registred");
					throw new VehicleServiceException(HttpStatus.BAD_REQUEST);
				}
			} else if (!StringUtils.isEmpty(regNumber)) {
				listVehicles = vehicleRepository.findByRegNum(regNumber);
				if (listVehicles.isEmpty()) {
					log.warn("Vehicle Not Registred");
					throw new VehicleServiceException(HttpStatus.BAD_REQUEST);
				}
			} else {
				if (listVehicles.isEmpty()) {
					log.warn("Vehicle Not Registred");
					throw new VehicleServiceException(HttpStatus.BAD_REQUEST);
				}
			}
			log.trace("searchVehicleByVinOrRegNum(-,-) method end");
			// return listVehicles.stream().map(m -> modelMapper.map(m,
			// VehicleDto.class)).collect(Collectors.toList());
			return formatedVehicleData(listVehicles);
		} catch (VehicleServiceException e) {
			log.error("Vehicle not registered", e.getMessage());
			throw new VehicleServiceException(HttpStatus.BAD_REQUEST);
		}

		catch (Exception e) {
			log.error("Exception occured while searching the vehicle {}", e.getMessage());
			throw new VehicleServiceException(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

//THIS METHOD WILL ABLE TO GET DATA FROM REPOSITORY
	public Vehicle convertDtoToEntity(VehicleDto vehicle) throws ParseException {

		Vehicle newVehicle = new Vehicle();
		newVehicle.setVin(vehicle.getVin());
		newVehicle.setReg_Number(vehicle.getReg_Number());
		newVehicle.setBrand(vehicle.getBrand());
		newVehicle.setPrice(vehicle.getPrice());
		newVehicle.setCurrency(vehicle.getCurrency());
		newVehicle.setIs_Insured(vehicle.getIs_Insured());
		newVehicle.setWarranty_Coverage(vehicle.getWarranty_Coverage());
		newVehicle.setCreated_user(vehicle.getCreated_user());
		newVehicle.setSelling_Dealer(vehicle.getSelling_Dealer());

		String mfg_Date = vehicle.getMfg_Date();
		SimpleDateFormat mfDate = new SimpleDateFormat("yyyy-MM-dd");
		Date parseMfDate = mfDate.parse(mfg_Date);
		newVehicle.setMfg_Date(parseMfDate);

		String reg_Date = vehicle.getReg_Date();
		SimpleDateFormat regDate = new SimpleDateFormat("yyyy-MM-dd");
		Date parseregDate = regDate.parse(reg_Date);
		newVehicle.setReg_Date(parseregDate);

		String warranty_Date = vehicle.getWarranty_Date();
		SimpleDateFormat warrDate = new SimpleDateFormat("yyyy-MM-dd");
		Date parseWarDate = warrDate.parse(warranty_Date);
		newVehicle.setWarranty_Date(parseWarDate);

		return newVehicle;
	}

	public static List<VehicleDto> formatedVehicleData(List<Vehicle> listOfVehicle) {
		List<VehicleDto> listData = new ArrayList<>();

		listOfVehicle.stream().forEach(data -> {

			VehicleDto dto = new VehicleDto();
			Date mfg_Date = data.getMfg_Date();
			Date reg_Date = data.getReg_Date();
			Date warranty_Date = data.getWarranty_Date();

			String mfDate = new SimpleDateFormat("yyyy-MM-dd").format(mfg_Date);
			String regDate = new SimpleDateFormat("yyyy-MM-dd").format(reg_Date);
			String warrantyDate = new SimpleDateFormat("yyyy-MM-dd").format(warranty_Date);
			dto.setVin(data.getVin());
			dto.setReg_Number(data.getReg_Number());
			dto.setReg_Date(regDate);
			dto.setMfg_Date(mfDate);
			dto.setPrice(data.getPrice());
			dto.setCurrency(data.getCurrency());
			dto.setIs_Insured(data.getIs_Insured());
			dto.setBrand(data.getBrand());
			dto.setWarranty_Date(warrantyDate);
			dto.setSelling_Dealer(data.getSelling_Dealer());
			dto.setWarranty_Coverage(data.getWarranty_Coverage());

			listData.add(dto);
		});

		return listData;
	}
}
