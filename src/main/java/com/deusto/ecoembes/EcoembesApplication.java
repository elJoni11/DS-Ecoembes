package com.deusto.ecoembes;

import org.springframework.boot.SpringApplication;	
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.deusto.ecoembes", "Ecoembes"})
public class EcoembesApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcoembesApplication.class, args);
	}

}

