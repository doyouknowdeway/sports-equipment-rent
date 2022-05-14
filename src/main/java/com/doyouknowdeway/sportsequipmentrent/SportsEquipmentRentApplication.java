package com.doyouknowdeway.sportsequipmentrent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:constants.properties")
@SpringBootApplication
public class SportsEquipmentRentApplication {

	public static void main(final String[] args) {
		SpringApplication.run(SportsEquipmentRentApplication.class, args);
	}

}
