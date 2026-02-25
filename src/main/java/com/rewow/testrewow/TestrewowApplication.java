package com.rewow.testrewow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TestrewowApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestrewowApplication.class, args);
	}

}
