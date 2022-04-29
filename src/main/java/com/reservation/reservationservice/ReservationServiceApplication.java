package com.reservation.reservationservice;

import com.reservation.reservationservice.service.FileStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class ReservationServiceApplication implements CommandLineRunner {

	@Resource
	FileStorageService filesStorageService;

	public static void main(String[] args) {
		SpringApplication.run(ReservationServiceApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
		filesStorageService.init();
	}

}
