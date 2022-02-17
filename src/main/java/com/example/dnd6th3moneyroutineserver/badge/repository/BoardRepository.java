package com.example.dnd6th3moneyroutineserver.badge.repository;

import com.example.dnd6th3moneyroutineserver.badge.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByUserId(Long userId);
}
