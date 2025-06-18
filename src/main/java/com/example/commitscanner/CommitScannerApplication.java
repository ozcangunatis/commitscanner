package com.example.commitscanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CommitScannerApplication {
	public static void main(String[] args) {
		SpringApplication.run(CommitScannerApplication.class, args);
	}
}
