package com.example.dnd6th3moneyroutineserver.badge;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Achieve {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "badge_id")
    private Badge badge;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    private LocalDateTime achieveTime;
}
