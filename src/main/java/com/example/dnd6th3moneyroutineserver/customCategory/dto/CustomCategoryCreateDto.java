package com.example.dnd6th3moneyroutineserver.customCategory.dto;

import lombok.Data;

@Data
public class CustomCategoryCreateDto {
    private String name;
    private String detail;
    private Long userId;
}
