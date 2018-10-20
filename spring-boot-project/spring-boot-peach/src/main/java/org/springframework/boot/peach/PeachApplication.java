package org.springframework.boot.peach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class PeachApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeachApplication.class, args);
	}
}
