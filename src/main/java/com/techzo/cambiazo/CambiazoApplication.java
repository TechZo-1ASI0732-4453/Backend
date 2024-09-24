package com.techzo.cambiazo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CambiazoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CambiazoApplication.class, args);
	}

}
