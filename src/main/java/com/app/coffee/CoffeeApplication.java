package com.app.coffee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.app.coffee.config.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@EnableJpaRepositories
public class CoffeeApplication {
	public static void main(String[] args) {
		SpringApplication.run(CoffeeApplication.class, args);
	}
}
