package com.example.dnd6th3moneyroutineserver.goal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoalCategoryCreateDto {

    private Long categoryId;
    private int budget;
    private Boolean isCustom; // lombok 에서 @Getter, @Data 사용시 자동으로 isBoolean() 메서드 생성으로 옳지않은값이 들어감 -> wrapper class로 수정
}
