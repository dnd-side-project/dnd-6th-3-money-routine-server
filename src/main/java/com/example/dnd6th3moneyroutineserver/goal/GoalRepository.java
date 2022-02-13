package com.example.dnd6th3moneyroutineserver.goal;

import com.example.dnd6th3moneyroutineserver.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface GoalRepository extends JpaRepository<Goal, Long> {

    Goal findByStartDateAndUser(LocalDate date, User user);

    Goal findByStartDateAndUserId(LocalDate date, Long userId);

//    @Query("select g from Goal g join g.user u on u.id =: userId order by g.startDate desc")
//    Goal findTop1ByUserIdAndOrderByStartDateDesc(Long userId);

    Goal findTop1ByUserIdOrderByStartDateDesc(Long userId);
}
