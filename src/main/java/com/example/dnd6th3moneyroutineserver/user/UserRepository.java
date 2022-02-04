package com.example.dnd6th3moneyroutineserver.user;

import com.example.dnd6th3moneyroutineserver.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
