package com.example.myspring.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.myspring.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findByNameContaining(String keyword);
}
