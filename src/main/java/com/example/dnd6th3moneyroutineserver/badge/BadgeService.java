package com.example.dnd6th3moneyroutineserver.badge;

import com.example.dnd6th3moneyroutineserver.badge.repository.AchieveRepository;
import com.example.dnd6th3moneyroutineserver.badge.repository.BadgeRepository;
import com.example.dnd6th3moneyroutineserver.badge.repository.BoardRepository;
import com.example.dnd6th3moneyroutineserver.user.service.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class BadgeService {

    public UserService userService;
    public BadgeRepository badgeRepository;
    public BoardRepository boardRepository;
    public AchieveRepository achieveRepository;

    @Transactional
    public void setNewGoal() {
        Long userId = userService.currentUser();
        Badge badge = badgeRepository.findById(1L).orElseThrow();
        Board board = boardRepository.findByUserId(userId).orElseThrow();
        Achieve achieve = achieveRepository.save(Achieve.builder()
                .badge(badge)
                .board(board)
                .achieveTime(LocalDateTime.now())
                .build());
        board.getAchieveList().add(achieve);
        badge.getAchieveList().add(achieve);
    }

    @Transactional
    public void writeExpenditure() {

    }

    @Transactional
    public void writeDiary() {

    }

    @Transactional
    public void satisfied() {

    }
}
