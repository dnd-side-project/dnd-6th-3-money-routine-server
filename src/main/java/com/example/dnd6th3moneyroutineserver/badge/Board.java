package com.example.dnd6th3moneyroutineserver.badge;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate date;

    @OneToMany(mappedBy = "board")
    private List<Achieve> achieveList = new ArrayList<>();
}
