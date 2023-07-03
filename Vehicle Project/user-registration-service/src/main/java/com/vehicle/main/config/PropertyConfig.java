package com.vehicle.main.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "service.response")
@Configuration
public class PropertyConfig {
	private String conditionTrue;
	private String conditionFalse;

	public String getConditionTrue() {
		return conditionTrue;
	}

	public void setConditionTrue(String conditionTrue) {
		this.conditionTrue = conditionTrue;
	}

	public String getConditionFalse() {
		return conditionFalse;
	}

	public void setConditionFalse(String conditionFalse) {
		this.conditionFalse = conditionFalse;
	}

	@Override
	public String toString() {
		return "PropertyConfig [conditionTrue=" + conditionTrue + ", conditionFalse=" + conditionFalse + "]";
	}

}
