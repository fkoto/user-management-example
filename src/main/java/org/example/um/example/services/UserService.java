package org.example.um.example.services;

import java.util.List;

import org.example.um.example.entities.User;

public interface UserService {

	public User findUserById(String id);

	public User findUserByUsername(String username);

	public List<User> findAll();

	public boolean deleteUserById(String id);

	public boolean deleteUserByUsername(String username);

	public User createUser(String firstName, String lastName, String email, Boolean mailVerified, String username);

	public User update(String id, String firstName, String lastName, String email, Boolean mailVerified, String username);

	public List<User> search(String firstName, String lastName);
}
