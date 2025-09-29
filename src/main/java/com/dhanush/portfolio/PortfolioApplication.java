package com.dhanush.portfolio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class PortfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(ProjectRepository repo) {
		return (args) -> {
			repo.saveAll(List.of(
					new Project("Personal Website",
							"A responsive portfolio website built with Spring Boot and Thymeleaf.",
							"https://github.com/yourusername/personal-website",
							"/images/portfolio.jpeg"),
					new Project("To-Do App",
							"A Java-based to-do list app with CRUD functionality.",
							null,
							"/images/ToDo.jpg"),
					new Project("Weather Dashboard",
							"View the current weather in any city with this REST API project.",
							"https://github.com/yourusername/weather-dashboard",
							"/images/weather.jpeg")
			));
		};
	}
}
