package com.example.dnd6th3moneyroutineserver.expenditure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StatisticsResponseDto {
    private List<ExpenditureCategoryDto> expenditureCategoryDtoList;
}
