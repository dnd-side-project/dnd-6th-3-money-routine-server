package com.example.dnd6th3moneyroutineserver.expenditure.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 소비내역의 카테고리별 지출내역을 담은 DTO
 */
@Data
@Builder
public class ExpenditureCategoryDto {

    private Long categoryId;
    private boolean isCustom;
    private String name;
    private int expense;
    private double percentage;

}
