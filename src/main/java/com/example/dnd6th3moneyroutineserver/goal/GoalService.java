package com.example.dnd6th3moneyroutineserver.goal;

import com.example.dnd6th3moneyroutineserver.category.CategoryRepository;
import com.example.dnd6th3moneyroutineserver.customCategory.CustomCategory;
import com.example.dnd6th3moneyroutineserver.customCategory.CustomCategoryRepository;
import com.example.dnd6th3moneyroutineserver.goal.dto.GoalCategoryCreateDto;
import com.example.dnd6th3moneyroutineserver.goal.dto.GoalCreateDto;
import com.example.dnd6th3moneyroutineserver.user.User;
import com.example.dnd6th3moneyroutineserver.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;
    private final GoalCategoryRepository goalCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final CustomCategoryRepository customCategoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long addGoal(GoalCreateDto goalCreateDto) {
        // 1. create goal
        User user = userRepository.findById(goalCreateDto.getUserId()).orElseThrow();
        LocalDate now = LocalDate.now();
        Goal goal = goalRepository.save(
                Goal.builder()
                        .user(user)
                        .startDate(now.withDayOfMonth(1))
                        .endDate(now.withDayOfMonth(now.lengthOfMonth()))
                        .totalBudget(goalCreateDto.getTotal_budget())
                        .build()
        );

        // 2. create goal category
        List<GoalCategoryCreateDto> goalCategoryCreateDtoList = goalCreateDto.getGoalCategoryCreateDtoList();
        for (GoalCategoryCreateDto goalCategoryCreateDto : goalCategoryCreateDtoList) {
            if (goalCategoryCreateDto.isCustom()) {
                saveGoalCategory(goal, goalCategoryCreateDto);
            } else {
                saveCustomGoalCategory(goal, goalCategoryCreateDto);
            }
        }

        return goal.getId();
    }

    private void saveGoalCategory(Goal goal, GoalCategoryCreateDto goalCategoryCreateDto) {
        goalCategoryRepository.save(
                GoalCategory.builder()
                        .budget(goalCategoryCreateDto.getBudget())
                        .totalExpense(0)
                        .goal(goal)
                        .customCategory(customCategoryRepository.findById(goalCategoryCreateDto.getCategoryId()).orElseThrow())
                        .build()
        );
    }

    private void saveCustomGoalCategory(Goal goal, GoalCategoryCreateDto goalCategoryCreateDto) {
        goalCategoryRepository.save(
                GoalCategory.builder()
                        .budget(goalCategoryCreateDto.getBudget())
                        .totalExpense(0)
                        .goal(goal)
                        .category(categoryRepository.findById(goalCategoryCreateDto.getCategoryId()).orElseThrow())
                        .build()
        );
    }

}
