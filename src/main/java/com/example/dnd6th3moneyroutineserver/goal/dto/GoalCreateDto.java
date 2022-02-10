package com.example.dnd6th3moneyroutineserver.goal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoalCreateDto {

    private int total_budget;
    private List<GoalCategoryCreateDto> goalCategoryCreateDtoList;
    private Long userId;

}
