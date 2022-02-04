package com.example.dnd6th3moneyroutineserver.goal;

import com.example.dnd6th3moneyroutineserver.category.Category;
import com.example.dnd6th3moneyroutineserver.category.CustomCategory;
import com.example.dnd6th3moneyroutineserver.goal.Goal;

import javax.persistence.*;

@Entity
public class GoalCategory {

    @Id @GeneratedValue
    @Column(name = "GOAL_CATEGORY_ID")
    private Long id;

    private int budget;

    private int totalExpense;

    @ManyToOne
    @JoinColumn(name = "GOAL_ID")
    private Goal goal;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "CUSTOM_CATEGORY_ID")
    private CustomCategory customCategory;
}
