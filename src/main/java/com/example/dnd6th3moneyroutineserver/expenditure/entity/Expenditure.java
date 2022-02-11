package com.example.dnd6th3moneyroutineserver.expenditure.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Expenditure {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EXPENDITURE_ID")
    private Long id;

    private Long userId;

    private LocalDate date;

    private Long expense;

    private String expenseDetail;

    private Long categoryId;

    private Long customCategoryId;

    private boolean isCustom;

    @Enumerated(EnumType.STRING)
    private Emotion emotion;

    private String emotionDetail;
}
