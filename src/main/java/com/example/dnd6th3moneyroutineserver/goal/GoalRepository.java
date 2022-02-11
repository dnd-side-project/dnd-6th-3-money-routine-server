package com.example.dnd6th3moneyroutineserver.goal;

import com.example.dnd6th3moneyroutineserver.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface GoalRepository extends JpaRepository<Goal, Long> {

    Goal findByStartDateAndUser(LocalDate date, User user);
}
