package org.example.um.example.repositories;

import java.util.List;

import org.example.um.example.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, String> {

	public User findByUsername(String username);

	@Modifying
	public void deleteByUsername(String username);

	public List<User> findByFirstNameContaining(String firstName);

	public List<User> findByLastNameContaining(String lastName);

	public List<User> findByFirstNameContainingAndLastNameContaining(String firstname, String lastName);
}
