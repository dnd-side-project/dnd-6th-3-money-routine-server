package com.example.dnd6th3moneyroutineserver.expenditure.repository;

import com.example.dnd6th3moneyroutineserver.expenditure.entity.Expenditure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenditureRepository extends JpaRepository<Expenditure, Long> {
    List<Expenditure> findAllByDateBetweenAndUserId(LocalDate startDate, LocalDate endDate, Long userId);
}
