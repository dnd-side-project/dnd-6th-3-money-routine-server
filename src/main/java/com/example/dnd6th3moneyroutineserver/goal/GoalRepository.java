package com.example.dnd6th3moneyroutineserver.goal;

import com.example.dnd6th3moneyroutineserver.goal.Goal;
import com.example.dnd6th3moneyroutineserver.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface GoalRepository extends JpaRepository<Goal, Long> {

    Goal findByStartDateAndUser(LocalDate date, User user);
}
