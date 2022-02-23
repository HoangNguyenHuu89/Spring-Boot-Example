package com.example.myspring.service;

import java.util.List;

import com.example.myspring.model.User;

public interface UserService {
	List<User> findAll();

	List<User> search(String title);

	User findOne(long id);

	void save(User t);

	void delete(Long id);

}
