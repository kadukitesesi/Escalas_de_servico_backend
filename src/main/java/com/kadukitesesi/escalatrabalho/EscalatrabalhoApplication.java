package com.kadukitesesi.escalatrabalho;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootApplication
public class EscalatrabalhoApplication {

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
		System.out.println("Timezone definido para: " + ZoneId.systemDefault());
	}


	public static void main(String[] args) {
		SpringApplication.run(EscalatrabalhoApplication.class, args);
	}

}
