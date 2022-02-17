package com.example.dnd6th3moneyroutineserver.badge;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String badgeName;

    private boolean isComplete;

    @OneToMany(mappedBy = "badge")
    private List<Achieve> achieveList = new ArrayList<>();
}
