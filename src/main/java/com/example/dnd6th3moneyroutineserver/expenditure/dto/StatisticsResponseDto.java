package com.example.dnd6th3moneyroutineserver.expenditure.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExpenditureStatisticsDto {

    private String topCategory; // 가장 많이 지출한 카테고리
    private List<ExpenditureCategoryDto> expenditureCategoryDtoList;
}
