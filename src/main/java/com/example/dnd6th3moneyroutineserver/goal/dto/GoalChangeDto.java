package com.example.dnd6th3moneyroutineserver.goal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoalChangeDto {

    private Long goalId;
    private int budget;
}
