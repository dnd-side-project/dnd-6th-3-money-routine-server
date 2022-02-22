package com.example.dnd6th3moneyroutineserver.goal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoalCategoryExpenseInsertDto {

    private Long categoryId;
    private boolean isCustom;
    private String name;
    private String emoji;
    private String detail;
}
