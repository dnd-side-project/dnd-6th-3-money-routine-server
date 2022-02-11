package com.example.dnd6th3moneyroutineserver.goal;

import com.example.dnd6th3moneyroutineserver.goal.Goal;
import com.example.dnd6th3moneyroutineserver.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface GoalRepository extends JpaRepository<Goal, Long> {

    Goal findByStartDateAndUser(LocalDate date, User user);

    Goal findByStartDateAndUserId(LocalDate date, Long userId);
}
