package com.example.dnd6th3moneyroutineserver.expenditure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeekExpenseInfoDto {
    public LocalDate startDate;
    public LocalDate endDate;
    public Long weekExpense;
}
