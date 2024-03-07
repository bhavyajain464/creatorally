package com.creatorally.centralplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class CentralplatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(CentralplatformApplication.class, args);
	}

}
