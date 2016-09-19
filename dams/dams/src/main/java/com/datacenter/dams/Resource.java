package com.datacenter.dams;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath:spring.xml"})
public class Resource {

	public static void main(String[] args) {
		SpringApplication.run(Resource.class, args);
	}
}
