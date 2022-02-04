package com.example.dnd6th3moneyroutineserver.expenditure.dto;

import com.example.dnd6th3moneyroutineserver.expenditure.Emotion;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ExpenditureCreateDto {

    private Date date;
    private int expense;
    private String expenseDetail;
    private Long categoryId;
    private boolean isCustom;
    private String emotion;
    private String emotionDetail;
}
