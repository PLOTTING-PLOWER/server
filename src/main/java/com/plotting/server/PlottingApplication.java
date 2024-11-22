package com.plotting.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class PlottingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlottingApplication.class, args);
	}

}
