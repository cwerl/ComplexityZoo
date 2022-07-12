package de.cwerl.complexityzoo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.cwerl.complexityzoo.service.InitDatabaseService;

@SpringBootApplication
public class ComplexityzooApplication implements CommandLineRunner {

	@Autowired
	InitDatabaseService initDatabaseService;

	public static void main(String[] args) {
		SpringApplication.run(ComplexityzooApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		initDatabaseService.init();
	}
}
