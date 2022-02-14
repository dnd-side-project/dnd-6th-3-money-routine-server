package com.example.dnd6th3moneyroutineserver.expenditure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyTendencyResponseDto {
    List<MonthExpenseInfoDto> monthExpenseInfoDtoList;
}
