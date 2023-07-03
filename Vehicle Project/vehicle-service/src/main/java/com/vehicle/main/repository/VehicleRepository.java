package com.vehicle.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vehicle.main.entity.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
			
	@Query("select e from Vehicle e where e.vin like %:vin% and e.reg_Number like %:regNo% ")
	List<Vehicle> findByVinAndRegNum(String vin, String regNo);

	@Query("select e from Vehicle e where e.vin like %:vin% ")
	List<Vehicle> findByVin(String vin);

	@Query("select e from Vehicle e where e.reg_Number like %:regNo% ")
	List<Vehicle> findByRegNum(String regNo);

}
