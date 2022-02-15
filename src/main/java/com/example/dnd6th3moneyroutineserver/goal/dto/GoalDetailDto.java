package com.example.dnd6th3moneyroutineserver.goal.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GoalDetailDto {

    private Long goalId;
    private int remainder;
    private int totalBudget;
    private List<GoalCategoryDetailDto> goalCategoryDetailDtoList;
}
