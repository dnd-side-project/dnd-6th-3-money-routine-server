package com.example.dnd6th3moneyroutineserver.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Goal {

    @Id @GeneratedValue
    @Column(name = "GOAL_ID")
    private Long id;

    private int totalBudget;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
