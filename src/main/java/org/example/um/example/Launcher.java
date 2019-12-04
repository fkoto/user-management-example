package org.example.um.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Launcher {
	@Value("${spring.application.name}")
	private String name;

	public static void main(String[] args) {
		SpringApplication.run(Launcher.class, args);
	}

	@RequestMapping(value = "/", produces = "text/html")
	public String name() {
		return " <!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "<title>Hello World!</title>\r\n" + "</head>\r\n"
				+ "<body>\r\n" + "\r\n" + "<h1>Hello user</h1>\r\n" + "<p>This is the " + name
				+ " application, developed with Spring Boot 2.2.1</p>\r\n" + "\r\n" + "</body>\r\n" + "</html> ";
	}
}