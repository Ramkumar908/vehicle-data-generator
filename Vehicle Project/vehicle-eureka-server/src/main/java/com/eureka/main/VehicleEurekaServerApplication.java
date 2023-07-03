package com.eureka.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class VehicleEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleEurekaServerApplication.class, args);
	}

}
