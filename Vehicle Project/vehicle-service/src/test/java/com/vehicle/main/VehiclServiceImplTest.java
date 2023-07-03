package com.vehicle.main;


import com.vehicle.main.dto.VehicleDto;
import com.vehicle.main.entity.Vehicle;
import com.vehicle.main.exception.VehicleServiceException;
import com.vehicle.main.pojo.SearchRequest;
import com.vehicle.main.repository.VehicleRepository;
import com.vehicle.main.service.impl.VehiclServiceHandler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.mockito.Mockito.*;

class VehiclServiceImplTest {
    @Mock
    VehicleRepository vehicleRepository;
    @Mock
    ModelMapper modelMapper;
    @Mock
    Logger LOGGER;
    @InjectMocks
    VehiclServiceHandler vehiclServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveVehicle() throws VehicleServiceException {
        Map<String, Object> result1= new HashMap<>();
        Map<String, Object> result = vehiclServiceImpl.saveVehicle(new Vehicle(null, "vin", null, new GregorianCalendar(2023, Calendar.JUNE, 22, 11, 1).getTime(), "brand", new GregorianCalendar(2023, Calendar.JUNE, 22, 11, 1).getTime(), Long.valueOf(1), "currency", true, new GregorianCalendar(2023, Calendar.JUNE, 22, 11, 1).getTime(), "warranty_Coverage", "selling_Dealer", "created_user", LocalDateTime.of(2023, Month.JUNE, 22, 11, 1, 37)));
        Assertions.assertEquals(result1, result);
    }

    @Test
    void testGetAllVehicles() throws VehicleServiceException {
        List<VehicleDto> result1=new ArrayList<>();
        List<VehicleDto> result = vehiclServiceImpl.getAllVehicles();
        Assertions.assertEquals(result1, result);
    }

    @Test
    void testSearchVehicleByVinOrRegNum() throws VehicleServiceException {
        SearchRequest searchRequest1 = new SearchRequest();
        searchRequest1.setReg_Number("WB24B2620");
        searchRequest1.setVin("");
        SearchRequest searchRequest2 = new SearchRequest();
        searchRequest2.setReg_Number("WB24B2620");
        searchRequest2.setVin("422");

        SearchRequest searchRequest3 = new SearchRequest();
        searchRequest3.setReg_Number("");
        searchRequest3.setVin("422");
        long price=0;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        List<Vehicle> findByVinAndRegNum=new ArrayList<>();
        List<Vehicle> findByVin=new ArrayList<>();
        List<Vehicle> findByRegNum=new ArrayList<>();

        when(vehicleRepository.findByVinAndRegNum("WB24B2620","422")).thenReturn(findByVinAndRegNum);
        when(vehicleRepository.findByVin("422")).thenReturn(findByVin);
        when(vehicleRepository.findByRegNum("WB24B2627")).thenReturn(findByRegNum);

        List<VehicleDto> result = vehiclServiceImpl.searchVehicleByVinOrRegNum("", "WB24B2620");
        List<VehicleDto> result2 = vehiclServiceImpl.searchVehicleByVinOrRegNum("422","");
        List<VehicleDto> result1 = vehiclServiceImpl.searchVehicleByVinOrRegNum("WB24B2620","422");
       
        Assertions.assertEquals(findByRegNum.size(), result.size());
        Assertions.assertEquals(findByVinAndRegNum.size(), result1.size());
        Assertions.assertEquals(findByVin.size(), result2.size());
    }
}

