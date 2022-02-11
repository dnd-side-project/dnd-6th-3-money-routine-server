package com.example.dnd6th3moneyroutineserver.expenditure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryType {
    private Long categoryId;
    private boolean isCustom;
}