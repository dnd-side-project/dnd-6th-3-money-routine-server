package com.example.dnd6th3moneyroutineserver.category.dto;

import lombok.Builder;

@Builder
public class CategoryDto {
    private String name;
    private String detail;
    private boolean isCustom;
}
