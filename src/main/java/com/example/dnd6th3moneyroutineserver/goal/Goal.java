package com.example.dnd6th3moneyroutineserver.goal;

import com.example.dnd6th3moneyroutineserver.user.User;

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
