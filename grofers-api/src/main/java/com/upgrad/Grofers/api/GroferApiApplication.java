package com.upgrad.Grofers.api;

import com.upgrad.Grofers.service.ServiceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ServiceConfiguration.class)
public class GroferApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(GroferApiApplication.class, args);
	}
}
