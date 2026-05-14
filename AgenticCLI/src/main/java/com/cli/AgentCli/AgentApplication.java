package com.cli.AgentCli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AgentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgentApplication.class, args);
        System.out.println("Welcome, the spring server is running");
	}
}
