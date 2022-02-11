package com.example.dnd6th3moneyroutineserver.expenditure.dto;

import com.example.dnd6th3moneyroutineserver.expenditure.entity.Expenditure;
import lombok.Data;

import java.time.LocalDate;

@Data
public class WeeklyExpenditureDetailDto {
    private LocalDate date;
    private Long expense;
    private String expenseDetail;

    public WeeklyExpenditureDetailDto(Expenditure expenditure) {
        this.date = expenditure.getDate();
        this.expense = expenditure.getExpense();
        this.expenseDetail = expenditure.getExpenseDetail();
    }
}
