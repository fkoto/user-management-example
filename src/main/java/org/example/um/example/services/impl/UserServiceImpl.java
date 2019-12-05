package org.example.um.example.services.impl;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.example.um.example.entities.User;
import org.example.um.example.repositories.UserRepository;
import org.example.um.example.services.UserService;
import org.example.um.example.utils.RandomIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
public class UserServiceImpl implements UserService {

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository repo;

	@Autowired
	private RandomIdGenerator generator;

	@Override
	public User findUserById(String id) {
		log.info("findUserById invoked with: {}", id);

		if (id.isEmpty()) {
			throw new IllegalArgumentException();
		}

		Optional<User> userOpt = repo.findById(id);

		log.debug("Database Returned: {}", userOpt);

		if (!userOpt.isPresent()) {
			throw new EntityNotFoundException("Unknown User");
		}

		return userOpt.get();
	}

	@Override
	public User findUserByUsername(String username) {
		log.info("findUserByUsername invoked with: {}", username);

		if (username.isEmpty()) {
			throw new IllegalArgumentException();
		}

		User user = repo.findByUsername(username);

		log.debug("Database Returned: {}", user);

		if (user == null) {
			throw new EntityNotFoundException("Unknown User");
		}

		return user;
	}

	@Override
	public List<User> findAll() {
		log.info("findAll invoked.");
		return repo.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean deleteUserById(String id) {
		log.info("deleteUserById invoked with: {}", id);

		repo.deleteById(id);
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean deleteUserByUsername(String username) {
		log.info("deleteUserByUsername invoked with: {}", username);

		repo.deleteByUsername(username);
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public User createUser(String firstName, String lastName, String email, Boolean mailVerified, String username) {
		log.info("create user invoked with: firstName={}, lastname={}, email={}, mailVerified={}, username={}",
				firstName, lastName, email, mailVerified, username);

		String id = generator.generate();
		if (username == null || username.trim().isEmpty()) {
			username = id;
		}

		if (email == null || email.trim().isEmpty()) { // in case both values are null
			email = "not provided";
			mailVerified = false;
		}

		User u = new User();
		u.setId(id);
		u.setFirstName(firstName.trim());
		u.setLastName(lastName.trim());
		u.setEmail(email.trim());
		u.setEmailVerified(mailVerified == null ? false : mailVerified);
		u.setUsername(username.trim());

		u.setCreationDate(ZonedDateTime.now());

		try {
			u = repo.save(u);

			log.info("Created user: {}", u);

			return u;
		} catch (Exception e) {
			log.error("Failed to create user. Error: {}", e.getMessage());
			if (log.isDebugEnabled()) {
				log.error("", e);
			}
			throw new RuntimeException("Could not create user.");
		}
	}

	/**
	 * This will retrieve a user object based on the id and will update any field provided that is not null.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public User update(String id, String firstName, String lastName, String email, Boolean mailVerified, String username) {
		log.info("update invoked for id: {} and fields: firstName={}, lastName={}, email={}, mailVerified={}, username={}", id, firstName, lastName, email, mailVerified, username);

		Optional<User> userOpt = repo.findById(id);

		if (userOpt.isEmpty()) {
			throw new EntityNotFoundException("The specified user does not exist.");
		}

		User user = userOpt.get();

		if (firstName != null) {
			user.setFirstName(firstName.trim().isEmpty()? null: firstName);
		}

		if (lastName != null) {
			user.setLastName(lastName.trim().isEmpty()? null: lastName);
		}

		if (email != null) {
			user.setEmail(email.trim().isEmpty()? null: email);
			user.setEmailVerified(false);
		}

		if (mailVerified != null) {
			user.setEmailVerified(mailVerified);
		}

		if (username != null) {
			if (username.trim().isEmpty()) {
				throw new RuntimeException("Username cannot be left blank!");
			}

			User userTest = repo.findByUsername(username);
			if (userTest != null) {
				throw new RuntimeException("Username already taken!");
			}

			user.setLastName(username);
		}

		log.debug("Returning updated user: {}", user);

		return user;
	}

}
