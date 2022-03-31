package com.example.dnd6th3moneyroutineserver.expenditure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyDetailsDto {
    private String expenseDetail;
    private Long expense;
}
