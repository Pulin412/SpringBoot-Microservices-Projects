package com.kalaha.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.kalaha.game.*"})
public class KalahaGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(KalahaGameApplication.class, args);
	}

}
