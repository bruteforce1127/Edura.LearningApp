package com.kucp1127.KidsLearningApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KidsLearningAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(KidsLearningAppApplication.class, args);
	}
}
