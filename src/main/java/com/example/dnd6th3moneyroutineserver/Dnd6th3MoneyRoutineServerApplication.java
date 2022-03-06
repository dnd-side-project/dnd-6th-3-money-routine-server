package com.example.dnd6th3moneyroutineserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
public class Dnd6th3MoneyRoutineServerApplication {

	@PostConstruct
	public void started() {
		// timezone UTC set
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
		System.out.println("현재시각: " + new Date());
	}

	public static void main(String[] args) {
		SpringApplication.run(Dnd6th3MoneyRoutineServerApplication.class, args);
	}

}
