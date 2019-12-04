package org.example.um.example.utils;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component("randomIdGenerator")
public class RandomIdGenerator {

	public String generate() {
		return UUID.randomUUID().toString();
	}
}
