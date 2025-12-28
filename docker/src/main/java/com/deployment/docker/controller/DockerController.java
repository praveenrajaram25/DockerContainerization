package com.deployment.docker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deployment.docker.component.User;
import com.deployment.docker.component.UserRepository;

@RestController
public class DockerController {
	
	
	@Autowired
	public UserRepository userRepo;

	
	@GetMapping("/container")
	public String goDocker() {
		return "App running in Docker Container";
	}
	
	

	@GetMapping("/user")
	public List<User> getUser() {
		return userRepo.findAll();
	}
	
}
