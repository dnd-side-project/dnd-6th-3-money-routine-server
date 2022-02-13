package com.example.dnd6th3moneyroutineserver.goal;

import com.example.dnd6th3moneyroutineserver.category.CategoryRepository;
import com.example.dnd6th3moneyroutineserver.customCategory.CustomCategoryRepository;
import com.example.dnd6th3moneyroutineserver.goal.dto.GoalCategoryCreateDto;
import com.example.dnd6th3moneyroutineserver.goal.dto.GoalCategoryDetailDto;
import com.example.dnd6th3moneyroutineserver.goal.dto.GoalCreateDto;
import com.example.dnd6th3moneyroutineserver.goal.dto.GoalDetailDto;
import com.example.dnd6th3moneyroutineserver.user.entity.User;
import com.example.dnd6th3moneyroutineserver.user.repository.UserRepository;
import com.example.dnd6th3moneyroutineserver.user.service.UserService;
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
    private final UserService userService;

    @Transactional
    public Long addGoal(GoalCreateDto goalCreateDto) {
        // 1. create goal
        User user = userRepository.findById(userService.currentUser()).orElseThrow();
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
                saveCustomGoalCategory(goal, goalCategoryCreateDto);
            } else {
                saveGoalCategory(goal, goalCategoryCreateDto);
            }
        }

        return goal.getId();
    }

    @Transactional
    public GoalDetailDto infoWithDate(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }

        Goal goal = goalRepository.findByStartDateAndUserId(date.withDayOfMonth(1), userService.currentUser());
        List<GoalCategory> goalCategoryList = goalCategoryRepository.findByGoalId(goal.getId());
        List<GoalCategoryDetailDto> detailDtoList = new ArrayList<>();
        int remainder = goal.getTotalBudget();

        for (GoalCategory goalCategory : goalCategoryList) {
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

    @Transactional
    public Long continueLast() {
        // 1. find last goal
        Long userId = userService.currentUser();
        Goal findGoal = goalRepository.findTop1ByUserIdOrderByStartDateDesc(userId);

        // 2. find goal category
        List<GoalCategory> goalCategoryList = goalCategoryRepository.findByGoalId(findGoal.getId());

        // 3. create new Goal and GoalCategories
        LocalDate now = LocalDate.now();
        Goal saveGoal = goalRepository.save(
                Goal.builder()
                        .user(userRepository.findById(userId).orElseThrow())
                        .startDate(now.withDayOfMonth(1))
                        .endDate(now.withDayOfMonth(now.lengthOfMonth()))
                        .totalBudget(findGoal.getTotalBudget())
                        .build()
        );

        for (GoalCategory goalCategory : goalCategoryList) {
            if (goalCategory.getCategory() == null) {
                goalCategoryRepository.save(
                        GoalCategory.builder()
                                .totalExpense(0)
                                .customCategory(goalCategory.getCustomCategory())
                                .goal(saveGoal)
                                .budget(goalCategory.getBudget())
                                .build()
                );
            } else {
                goalCategoryRepository.save(
                        GoalCategory.builder()
                                .totalExpense(0)
                                .category(goalCategory.getCategory())
                                .goal(saveGoal)
                                .budget(goalCategory.getBudget())
                                .build()
                );
            }
        }

        return saveGoal.getId();
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
