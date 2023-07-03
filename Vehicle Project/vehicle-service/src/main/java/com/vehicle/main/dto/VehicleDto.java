package com.vehicle.main.dto;

import javax.validation.Valid;

import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Validated
public class VehicleDto {

	@Valid
	@NonNull
	private String vin;
	@Valid
	@NonNull
	private String reg_Number;
	@Valid
	@NonNull
	private String reg_Date;
	@Valid
	@NonNull
	private String brand;
	@Valid
	@NonNull
	private String mfg_Date;
	@Valid
	@NonNull
	private Long price;
	private String currency;
	private Boolean is_Insured;
	private String warranty_Date;
	private String warranty_Coverage;
	private String selling_Dealer;
	private String created_user;
	private String created_date;

}