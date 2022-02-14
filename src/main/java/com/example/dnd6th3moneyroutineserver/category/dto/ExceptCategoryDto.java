package com.example.dnd6th3moneyroutineserver.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptCategoryDto {

    private Long categoryId;
    private String name;
    private String detail;
    private String emoji;
    private boolean isCustom;
}
