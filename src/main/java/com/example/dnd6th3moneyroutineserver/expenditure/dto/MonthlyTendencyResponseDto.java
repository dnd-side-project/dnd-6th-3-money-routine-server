package com.example.dnd6th3moneyroutineserver.expenditure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyTendencyResponseDto {
    List<MonthExpenseInfoDto> monthExpenseInfoDtoList;
}
