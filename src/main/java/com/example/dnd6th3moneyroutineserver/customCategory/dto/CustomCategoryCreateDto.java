package com.example.dnd6th3moneyroutineserver.customCategory.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomCategoryCreateDto {
    private String name;
    private String detail;
    private Long userId;
}
