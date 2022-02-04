package com.example.dnd6th3moneyroutineserver.goal.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoalCategoryExpenseInsertDto {

    private Long categoryId;
    private boolean isCustom;
    private String name;
}
