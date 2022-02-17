package com.example.dnd6th3moneyroutineserver.badge;

import com.example.dnd6th3moneyroutineserver.badge.repository.AchieveRepository;
import com.example.dnd6th3moneyroutineserver.badge.repository.BadgeRepository;
import com.example.dnd6th3moneyroutineserver.badge.repository.BoardRepository;
import com.example.dnd6th3moneyroutineserver.expenditure.entity.Emotion;
import com.example.dnd6th3moneyroutineserver.expenditure.entity.Expenditure;
import com.example.dnd6th3moneyroutineserver.expenditure.repository.ExpenditureRepository;
import com.example.dnd6th3moneyroutineserver.user.service.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BadgeService {

    public UserService userService;
    public BadgeRepository badgeRepository;
    public BoardRepository boardRepository;
    public AchieveRepository achieveRepository;
    public ExpenditureRepository expenditureRepository;

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
        Long userId = userService.currentUser();
        List<Expenditure> expenditureList = expenditureRepository.findAllByDateBetweenAndUserId(
                LocalDate.now().withDayOfMonth(1),
                LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()),
                userId);
        Long badgeId = 0L;
        if (expenditureList.size() == 1) {
            badgeId = 1L;
        }
        else if (expenditureList.size() == 7) {
            badgeId = 2L;
        }
        else if (expenditureList.size() == 15) {
            badgeId = 3L;
        }
        else if (expenditureList.size() == 24) {
            badgeId = 4L;
        }
        else if (expenditureList.size() == LocalDate.now().lengthOfMonth()) {
            badgeId = 5L;
        }

        Badge badge = badgeRepository.findById(badgeId).orElseThrow();
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
    public void writeDiary() {

    }

    @Transactional
    public void satisfied() {
        Long userId = userService.currentUser();
        List<Expenditure> expenditureList = expenditureRepository.findAllByDateBetweenAndUserIdAndEmotion(
                LocalDate.now().withDayOfMonth(1),
                LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()),
                userId,
                Emotion.GOOD);

        Long badgeId = 0L;
        if (expenditureList.size() == 1) {
            badgeId = 1L;
        }
        else if (expenditureList.size() == 5) {
            badgeId = 2L;
        }
        else if (expenditureList.size() == 10) {
            badgeId = 3L;
        }
        else if (expenditureList.size() == 20) {
            badgeId = 4L;
        }
        else if (expenditureList.size() == 30) {
            badgeId = 5L;
        }

        Badge badge = badgeRepository.findById(badgeId).orElseThrow();
        Board board = boardRepository.findByUserId(userId).orElseThrow();
        Achieve achieve = achieveRepository.save(Achieve.builder()
                .badge(badge)
                .board(board)
                .achieveTime(LocalDateTime.now())
                .build());
        board.getAchieveList().add(achieve);
        badge.getAchieveList().add(achieve);
    }
}
