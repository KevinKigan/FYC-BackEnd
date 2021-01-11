package com.kevingomez.FYCBackEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FycBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(FycBackEndApplication.class, args);
	}

}
