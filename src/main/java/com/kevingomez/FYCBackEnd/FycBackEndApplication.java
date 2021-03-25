package com.kevingomez.FYCBackEnd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class FycBackEndApplication implements CommandLineRunner {

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(FycBackEndApplication.class, args);
	}

	/**
	 * Metodo que se ejecuta antes del propio main
	 *
	 * @param args
	 * @throws Exception
	 */
	@Override
	public void run(String... args) throws Exception {
		String pass = "123456";
		for (int i = 0; i < 4; i++) {
			String passBCry = passwordEncoder.encode(pass);
			System.out.println(passBCry);
		}
	}
}
