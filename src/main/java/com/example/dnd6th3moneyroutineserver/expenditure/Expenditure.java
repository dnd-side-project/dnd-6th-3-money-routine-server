package com.example.dnd6th3moneyroutineserver.expenditure;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Expenditure {

    @Id @GeneratedValue
    @Column(name = "EXPENDITURE_ID")
    private Long id;

    private LocalDateTime date;

    private int expense;

    private String detail;

    @Enumerated(EnumType.STRING)
    private Emotion emotion;
}
