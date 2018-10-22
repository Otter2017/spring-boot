package org.springframework.boot.peach;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = "org.springframework.boot.peach.mybatis.mapper")
public class PeachApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeachApplication.class, args);
	}
}
