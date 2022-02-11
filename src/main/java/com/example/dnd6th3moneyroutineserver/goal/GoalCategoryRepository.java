package com.example.dnd6th3moneyroutineserver.goal;

import com.example.dnd6th3moneyroutineserver.goal.GoalCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface GoalCategoryRepository extends JpaRepository<GoalCategory, Long> {

    @Query(value = "select c from GoalCategory c left outer join fetch c.category left join fetch c.customCategory where c.goal.id =:goalId")
    List<GoalCategory> findByGoalId(@Param("goalId") Long goalId);

    @Query(value = "select c from GoalCategory c join c.goal g left join fetch c.category left join fetch c.customCategory where g.startDate =:date and g.user.id =:userId")
    List<GoalCategory> findByGoalDateAndUserId(@Param("date") LocalDate date, @Param("userId") Long userId);
}
