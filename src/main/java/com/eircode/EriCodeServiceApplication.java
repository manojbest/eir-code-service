package com.eircode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
public class EriCodeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EriCodeServiceApplication.class, args);
	}
}
