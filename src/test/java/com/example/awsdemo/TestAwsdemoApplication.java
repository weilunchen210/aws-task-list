package com.example.awsdemo;

import org.springframework.boot.SpringApplication;

public class TestAwsdemoApplication {

	public static void main(String[] args) {
		SpringApplication.from(AwsdemoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
