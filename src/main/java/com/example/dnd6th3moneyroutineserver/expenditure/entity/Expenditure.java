package com.example.dnd6th3moneyroutineserver.expenditure.entity;

import com.example.dnd6th3moneyroutineserver.category.Category;
import com.example.dnd6th3moneyroutineserver.customCategory.CustomCategory;
import com.example.dnd6th3moneyroutineserver.user.entity.User;
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

    private LocalDate date;

    private Long expense;

    private String expenseDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOM_CATEGORY_ID")
    private CustomCategory customCategory;

    private boolean isCustom;

    @Enumerated(EnumType.STRING)
    private Emotion emotion;

    private String emotionDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
}
