package com.example.dnd6th3moneyroutineserver.goal;

import com.example.dnd6th3moneyroutineserver.category.CategoryRepository;
import com.example.dnd6th3moneyroutineserver.customCategory.CustomCategory;
import com.example.dnd6th3moneyroutineserver.customCategory.CustomCategoryRepository;
import com.example.dnd6th3moneyroutineserver.goal.dto.GoalCategoryCreateDto;
import com.example.dnd6th3moneyroutineserver.goal.dto.GoalCategoryDetailDto;
import com.example.dnd6th3moneyroutineserver.goal.dto.GoalCreateDto;
import com.example.dnd6th3moneyroutineserver.goal.dto.GoalDetailDto;
import com.example.dnd6th3moneyroutineserver.user.User;
import com.example.dnd6th3moneyroutineserver.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
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
            if (goalCategoryCreateDto.getIsCustom()) {
                log.info("custom category save" + "category id:" + goalCategoryCreateDto.getCategoryId());
                saveCustomGoalCategory(goal, goalCategoryCreateDto);
            } else {
                log.info("category save"+ "category id:" + goalCategoryCreateDto.getCategoryId());
                saveGoalCategory(goal, goalCategoryCreateDto);
            }
        }

        return goal.getId();
    }

    @Transactional
    public GoalDetailDto infoWithDate(Long userId, LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }

        Goal goal = goalRepository.findByStartDateAndUser(date.withDayOfMonth(1), userRepository.findById(userId).orElseThrow());
        List<GoalCategory> goalCategoryList = goalCategoryRepository.findByGoalId(goal.getId());
        List<GoalCategoryDetailDto> detailDtoList = new ArrayList<>();
        int remainder = goal.getTotalBudget();

        for (GoalCategory goalCategory : goalCategoryList) {
            log.info("search");
            if (goalCategory.getCategory() == null) {
                detailDtoList.add(GoalCategoryDetailDto.builder()
                        .name(goalCategory.getCustomCategory().getName())
                        .budget(goalCategory.getBudget())
                        .total_expense(goalCategory.getTotalExpense())
                        .build());
            } else {
                detailDtoList.add(GoalCategoryDetailDto.builder()
                        .name(goalCategory.getCategory().getName())
                        .budget(goalCategory.getBudget())
                        .total_expense(goalCategory.getTotalExpense())
                        .build());
            }
            remainder -= goalCategory.getTotalExpense();
        }

        return GoalDetailDto.builder().remainder(remainder).goalCategoryDetailDtoList(detailDtoList).build();
    }

    private void saveCustomGoalCategory(Goal goal, GoalCategoryCreateDto goalCategoryCreateDto) {
        goalCategoryRepository.save(
                GoalCategory.builder()
                        .budget(goalCategoryCreateDto.getBudget())
                        .totalExpense(0)
                        .goal(goal)
                        .customCategory(customCategoryRepository.findById(goalCategoryCreateDto.getCategoryId()).orElseThrow())
                        .build()
        );
    }

    private void saveGoalCategory(Goal goal, GoalCategoryCreateDto goalCategoryCreateDto) {
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
