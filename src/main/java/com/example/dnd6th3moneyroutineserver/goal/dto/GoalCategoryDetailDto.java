package com.example.dnd6th3moneyroutineserver.goal.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoalCategoryDetailDto {

    private Long categoryId;
    private String emoji;
    private String name;
    private int budget;
    private int totalExpense;
    private boolean isCustom;
}
