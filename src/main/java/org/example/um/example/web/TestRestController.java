package org.example.um.example.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("testRestController")
public class TestRestController {

	@GetMapping(value = "/", produces = "text/html")
	public String helloWorld() {
		return " <!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "<title>Hello World!</title>\r\n" + "</head>\r\n"
				+ "<body>\r\n" + "\r\n" + "<h1>Hello user</h1>\r\n"
				+ "<p>This is the user management example application, developed with Spring Boot 2.2.1</p>\r\n"
				+ "\r\n" + "</body>\r\n" + "</html> ";
	}
}
