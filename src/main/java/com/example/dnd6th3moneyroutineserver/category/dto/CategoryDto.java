package com.example.dnd6th3moneyroutineserver.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private String name;
    private String detail;
    private String emoji;
    private boolean isCustom;
}
