package com.vehicle.main.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Vehicle")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Vehicle {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE)
	private Long id;
	@Valid
	@Column(unique = true, nullable = false)
	private String vin;
	@Valid
	@Column(unique = true, nullable = false)
	private String reg_Number;
	@Valid
	@Column(unique = false, nullable = false)
	private Date reg_Date;
	@Valid
	@Column(unique = false, nullable = false)
	private String brand;
	@Valid
	@Column(unique = false, nullable = false)
	private Date mfg_Date;
	@Valid
	@Column(unique = false, nullable = false)
	private Long price;
	private String currency;
	private Boolean is_Insured = false;
	private Date warranty_Date;
	private String warranty_Coverage;
	private String selling_Dealer;
	@Column(name = "`created_user`")
	@CreatedBy
	private String created_user;
	@Column(name = "`created_date`")
	@CreationTimestamp
	private LocalDateTime created_date;

}
