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
        GoalState goalState = GoalState.SUCCESS;

        if (date == null || date.withDayOfMonth(1).equals(LocalDate.now().withDayOfMonth(1))) {
            date = LocalDate.now();
            goalState = GoalState.PROCEED;
        }

        Goal goal = goalRepository.findByStartDateAndUserId(date.withDayOfMonth(1), userService.currentUser());
        if (goal == null) { return null; }

        List<GoalCategory> goalCategoryList = goalCategoryRepository.findByGoalId(goal.getId());
        List<GoalCategoryDetailDto> detailDtoList = new ArrayList<>();
        int remainder = goal.getTotalBudget();

        for (GoalCategory goalCategory : goalCategoryList) {
            if (goalCategory.getCategory() == null) {
                detailDtoList.add(GoalCategoryDetailDto.builder()
                        .categoryId(goalCategory.getCustomCategory().getId())
                        .emoji(goalCategory.getCustomCategory().getEmoji())
                        .name(goalCategory.getCustomCategory().getName())
                        .detail(goalCategory.getCustomCategory().getDetail())
                        .budget(goalCategory.getBudget())
                        .totalExpense(goalCategory.getTotalExpense())
                        .goalCategoryId(goalCategory.getId())
                        .isCustom(true)
                        .build());
            } else {
                detailDtoList.add(GoalCategoryDetailDto.builder()
                        .categoryId(goalCategory.getCategory().getId())
                        .name(goalCategory.getCategory().getName())
                        .detail(goalCategory.getCategory().getDetail())
                        .budget(goalCategory.getBudget())
                        .totalExpense(goalCategory.getTotalExpense())
                        .goalCategoryId(goalCategory.getId())
                        .isCustom(false)
                        .build());
            }

            if (goalCategory.getBudget() < goalCategory.getTotalExpense()) {
                goalState = GoalState.FAIL;
            }
            remainder -= goalCategory.getTotalExpense();
        }

        if (goalState.equals(GoalState.FAIL) || goalState.equals(GoalState.PROCEED) && remainder < 0) goalState = GoalState.FAIL;

        return GoalDetailDto.builder()
                .goalId(goal.getId())
                .remainder(Math.max(remainder, 0))
                .totalBudget(goal.getTotalBudget())
                .goalCategoryDetailDtoList(detailDtoList)
                .goalState(goalState)
                .build();
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

    @Transactional
    public int changeTotalBudget(Long goalId, int changeBudget) {
        Goal goal = goalRepository.findById(goalId).orElseThrow();
        goal.changeTotalBudget(changeBudget);
        return changeBudget;
    }

    @Transactional
    public boolean checkEmpty() {
        return !goalRepository.findByUserId(userService.currentUser()).isEmpty();
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
