package com.example.dnd6th3moneyroutineserver.goal.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PickedCategoryRequest {

    private Long goalId;
    private Long categoryId;
    private boolean isCustom;
}
