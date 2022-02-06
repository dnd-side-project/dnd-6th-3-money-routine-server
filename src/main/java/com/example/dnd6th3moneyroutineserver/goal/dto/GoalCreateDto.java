package com.example.dnd6th3moneyroutineserver.goal.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GoalCreateDto {

    private int total_budget;
    private List<GoalCategoryCreateDto> goalCategoryCreateDtoList;
    private Long userId;

}
