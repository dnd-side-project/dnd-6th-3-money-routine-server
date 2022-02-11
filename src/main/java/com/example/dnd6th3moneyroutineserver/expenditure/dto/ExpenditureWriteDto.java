package com.example.dnd6th3moneyroutineserver.expenditure.dto;

import com.example.dnd6th3moneyroutineserver.expenditure.entity.Emotion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpenditureWriteDto {

    private LocalDate date;
    private Long expense;
    private String expenseDetail;
    private Long categoryId;
    private boolean isCustom;
    private Emotion emotion;
    private String emotionDetail;
}
