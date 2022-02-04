package com.example.dnd6th3moneyroutineserver.goal.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoalCategoryCreateDto {

    private Long categoryId;
    private int budget;
    private boolean isCustom;
}
