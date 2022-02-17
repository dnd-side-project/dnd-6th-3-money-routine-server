package com.example.dnd6th3moneyroutineserver.badge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Achieve {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "badge_id")
    private Badge badge;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    private LocalDateTime achieveTime;
}
