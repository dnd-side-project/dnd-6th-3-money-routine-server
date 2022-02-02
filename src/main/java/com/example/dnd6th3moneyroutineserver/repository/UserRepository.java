package com.example.dnd6th3moneyroutineserver.repository;

import com.example.dnd6th3moneyroutineserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
