package com.example.dnd6th3moneyroutineserver.expenditure.dto;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class WeeklyTendencyResponseDto {
    private int recommendExpense;
    private List<WeekExpenseInfoDto> weekExpenseInfoDtoList;
}
