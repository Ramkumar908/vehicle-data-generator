package com.gateway.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class VehicleApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleApiGatewayApplication.class, args);
	}

}
