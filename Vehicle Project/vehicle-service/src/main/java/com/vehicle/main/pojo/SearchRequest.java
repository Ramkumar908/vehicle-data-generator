package com.vehicle.main.pojo;

public class SearchRequest {

	private String vin;
	private String reg_Number;

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getReg_Number() {
		return reg_Number;
	}

	public void setReg_Number(String reg_Number) {
		this.reg_Number = reg_Number;
	}

	@Override
	public String toString() {
		return "SearchRequest [vin=" + vin + ", reg_Number=" + reg_Number + "]";
	}

}
