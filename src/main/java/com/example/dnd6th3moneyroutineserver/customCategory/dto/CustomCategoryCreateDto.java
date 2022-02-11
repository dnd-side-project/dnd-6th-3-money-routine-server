package com.example.dnd6th3moneyroutineserver.customCategory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomCategoryCreateDto {
    private String name;
    private String detail;
}
