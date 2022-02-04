package com.example.dnd6th3moneyroutineserver.goal.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GoalDetailDto {

    private int remainder;
    private List<GoalCategoryDetailDto> goalCategoryDetailDtoList;
}
