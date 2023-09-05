package com.javenock.officesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OfficesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OfficesServiceApplication.class, args);
	}

}
