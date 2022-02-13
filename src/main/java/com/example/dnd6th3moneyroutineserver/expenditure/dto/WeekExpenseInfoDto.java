package com.example.dnd6th3moneyroutineserver.expenditure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeekExpenseInfoDto {
    public LocalDate startDate;
    public LocalDate endDate;
    public Long weekExpense;
}
