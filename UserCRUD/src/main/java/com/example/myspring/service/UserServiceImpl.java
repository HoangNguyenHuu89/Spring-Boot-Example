package com.example.myspring.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myspring.model.User;
import com.example.myspring.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository res;

	@Override
	public List<User> findAll() {
		return res.findAll();
	}

	@Override
	public List<User> search(String username) {
		return res.findByNameContaining(username);
	}

	@Override
	public User findOne(long id) {
		return res.findById(id).get();
	}

	@Override
	public void save(User u) {
		res.save(u);
	}

	@Override
	public void delete(Long id) {
		res.deleteById(id);

	}

}
