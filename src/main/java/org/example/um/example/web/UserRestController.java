package org.example.um.example.web;

import java.util.List;

import org.example.um.example.entities.User;
import org.example.um.example.services.UserService;
import org.example.um.example.web.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userRestController")
@RequestMapping(value = "/users", produces = "application/json")
public class UserRestController {

	@Autowired
	private UserService service;

	@GetMapping("/id/{id}")
	public User findUserById(@PathVariable(name = "id") String id) {
		return service.findUserById(id);
	}

	@GetMapping("/username/{name}")
	public User findUserByUsername(@PathVariable(name = "name") String name) {
		return service.findUserByUsername(name);
	}

	@GetMapping("/all")
	public List<User> findAll() {
		return service.findAll();
	}

	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable(name = "id") String id) {
		service.deleteUserById(id);
	}

	@PostMapping("/add")
	public User createUser(@RequestBody UserDTO request) {
		return service.createUser(request.getFirstName(), request.getLastName(), request.getEmail(),
				request.getEmailVerified(), request.getUsername());
	}


	@PutMapping("/{id}/update")
	public User updateUser(@PathVariable("id") String id, @RequestBody UserDTO request) {
		return service.update(id, request.getFirstName(), request.getLastName(), request.getEmail(), request.getEmailVerified(), request.getUsername());
	}

}
