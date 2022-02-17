package com.example.dnd6th3moneyroutineserver.category;

import com.example.dnd6th3moneyroutineserver.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select gc.category.id from GoalCategory gc join gc.goal g where g.user.id =:userId and g.startDate =:date")
    Set<Long> findIdByUserIdAndStartDate(@Param("userId") Long userId, @Param("date") LocalDate date);
}
