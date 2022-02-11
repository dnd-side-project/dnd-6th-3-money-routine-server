package com.example.dnd6th3moneyroutineserver.user.repository;

import com.example.dnd6th3moneyroutineserver.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
