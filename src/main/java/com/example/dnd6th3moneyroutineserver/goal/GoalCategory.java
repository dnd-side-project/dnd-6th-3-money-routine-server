package com.example.dnd6th3moneyroutineserver.goal;

import com.example.dnd6th3moneyroutineserver.category.Category;
import com.example.dnd6th3moneyroutineserver.customCategory.CustomCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoalCategory {

    @Id @GeneratedValue
    @Column(name = "GOAL_CATEGORY_ID")
    private Long id;

    private int budget;

    private int totalExpense;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GOAL_ID")
    private Goal goal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOM_CATEGORY_ID")
    private CustomCategory customCategory;

    public void addExpense(int expense) {
        this.totalExpense += expense;
    }

    public void modifyBudget(int budget) {
        this.budget = budget;
    }
}
