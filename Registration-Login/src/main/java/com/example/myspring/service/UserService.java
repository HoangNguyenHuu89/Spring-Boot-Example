package com.example.myspring.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.myspring.model.User;

public interface UserService extends UserDetailsService {
	User save(User user);

	List<User> findAll();

}
