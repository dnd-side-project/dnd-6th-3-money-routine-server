package com.example.dnd6th3moneyroutineserver.expenditure.repository;

import com.example.dnd6th3moneyroutineserver.category.Category;
import com.example.dnd6th3moneyroutineserver.customCategory.CustomCategory;
import com.example.dnd6th3moneyroutineserver.expenditure.entity.Expenditure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenditureRepository extends JpaRepository<Expenditure, Long> {
    List<Expenditure> findAllByDateBetweenAndUserId(LocalDate startDate, LocalDate endDate, Long userId);
    List<Expenditure> findAllByDateBetweenAndUserIdAndCategory(LocalDate startDate, LocalDate endDate, Long userId, Category category);
    List<Expenditure> findAllByDateBetweenAndUserIdAndCustomCategory(LocalDate startDate, LocalDate endDate, Long userId, CustomCategory category);
}
