package com.vehicle.main.entity;

public class VehicleResponse {

	private Boolean success;
	private Object response;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "VehicleResponse [success=" + success + ", response=" + response + "]";
	}

}
