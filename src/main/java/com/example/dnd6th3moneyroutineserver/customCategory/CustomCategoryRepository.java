package com.example.dnd6th3moneyroutineserver.customCategory;

import com.example.dnd6th3moneyroutineserver.customCategory.CustomCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface CustomCategoryRepository extends JpaRepository<CustomCategory, Long> {

    @Query("select c from CustomCategory c join c.user u where u.id =:userId")
    List<CustomCategory> findByUserId(@Param("userId") Long userId);

    @Query("select gc.customCategory.id from GoalCategory gc join gc.goal g where g.user.id =:userId and g.startDate =:date")
    Set<Long> findIdByUserIdAndStartDate(@Param("userId") Long userId, @Param("date")LocalDate date);
}
