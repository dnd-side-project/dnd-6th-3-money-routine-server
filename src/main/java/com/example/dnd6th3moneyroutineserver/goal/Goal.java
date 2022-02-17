package com.example.dnd6th3moneyroutineserver.goal;

import com.example.dnd6th3moneyroutineserver.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Goal {

    @Id @GeneratedValue
    @Column(name = "GOAL_ID")
    private Long id;

    private int totalBudget;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    // Business methods
    public void changeTotalBudget(int changeBudget) {
        this.totalBudget = changeBudget;
    }

}
