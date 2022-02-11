package com.example.dnd6th3moneyroutineserver.expenditure.dto;

import com.example.dnd6th3moneyroutineserver.expenditure.entity.CategoryType;
import lombok.Builder;
import lombok.Data;

@Data
public class ExpenditureCategoryDto {
    private Long categoryId;
    private boolean isCustom;
    private String name;
    private Long expense;
    private double percentage;

    public ExpenditureCategoryDto(CategoryType categoryType, String name, Long expense, double percentage) {
        this.categoryId = categoryType.getCategoryId();
        this.isCustom = categoryType.isCustom();
        this.name = name;
        this.expense = expense;
        this.percentage = percentage;
    }
}
