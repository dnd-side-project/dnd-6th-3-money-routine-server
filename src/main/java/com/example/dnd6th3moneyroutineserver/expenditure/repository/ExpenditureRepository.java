package com.example.dnd6th3moneyroutineserver.expenditure.repository;

import com.example.dnd6th3moneyroutineserver.category.Category;
import com.example.dnd6th3moneyroutineserver.customCategory.CustomCategory;
import com.example.dnd6th3moneyroutineserver.expenditure.entity.Emotion;
import com.example.dnd6th3moneyroutineserver.expenditure.entity.Expenditure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenditureRepository extends JpaRepository<Expenditure, Long> {
    List<Expenditure> findAllByDateBetweenAndUserId(LocalDate startDate, LocalDate endDate, Long userId);
    List<Expenditure> findAllByDateBetweenAndUserIdAndCategory(LocalDate startDate, LocalDate endDate, Long userId, Category category);
    List<Expenditure> findAllByDateBetweenAndUserIdAndCustomCategory(LocalDate startDate, LocalDate endDate, Long userId, CustomCategory category);

    @Query("select e from Expenditure e left join fetch e.category left join fetch e.customCategory where e.user.id =:userId and e.date between :startDate and :endDate order by e.date asc")
    List<Expenditure> findByDateBetweenAndUserIdOrderByDateAsc(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("userId") Long userId);

    @Query("select e from Expenditure e left join fetch e.category left join fetch e.customCategory where e.user.id =:userId and e.date =:date")
    List<Expenditure> findByDateAndUserId(@Param("date") LocalDate date, @Param("userId") Long userId);

    @Query("select e from Expenditure e left join fetch e.category left join fetch e.customCategory where e.user.id =:userId and e.emotion =:emotion and e.date between :startDate and :endDate")
    List<Expenditure> findByDateBetweenAndUserIdAndEmotion(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("userId") Long userId, @Param("emotion") Emotion emotion);
}
