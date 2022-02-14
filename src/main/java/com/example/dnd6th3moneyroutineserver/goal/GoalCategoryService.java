package com.example.dnd6th3moneyroutineserver.goal;

import com.example.dnd6th3moneyroutineserver.goal.dto.GoalCategoryExpenseInsertDto;
import com.example.dnd6th3moneyroutineserver.goal.dto.GoalCategoryModifyDto;
import com.example.dnd6th3moneyroutineserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalCategoryService {

    private final GoalCategoryRepository goalCategoryRepository;
    private final GoalRepository goalRepository;
    private final UserService userService;

    /**
     * User의 GoalCategory List 반환
     * @return
     */
    @Transactional
    public List<GoalCategoryExpenseInsertDto> getGoalCategoryList() {
        Long userId = userService.currentUser();
        List<GoalCategory> goalCategoryList = goalCategoryRepository
                .findByGoalDateAndUserId(LocalDate.now().withDayOfMonth(1), userId);

        List<GoalCategoryExpenseInsertDto> goalCategoryDtoList = new ArrayList<>();

        for (GoalCategory goalCategory : goalCategoryList) {
            if (goalCategory.getCategory() == null) {
                goalCategoryDtoList.add(GoalCategoryExpenseInsertDto.builder()
                        .categoryId(goalCategory.getCustomCategory().getId())
                        .isCustom(true)
                        .name(goalCategory.getCustomCategory().getName())
                        .build());
            } else {
                goalCategoryDtoList.add(GoalCategoryExpenseInsertDto.builder()
                        .categoryId(goalCategory.getCategory().getId())
                        .isCustom(false)
                        .name(goalCategory.getCategory().getName())
                        .build());
            }
        }

        return goalCategoryDtoList;
    }

    @Transactional
    public boolean remove(Long goalCategoryId) {
        GoalCategory removeCategory = goalCategoryRepository.findById(goalCategoryId).orElseThrow();
        if (!removeCategory.getGoal().getUser().getId().equals(userService.currentUser())) {
            goalCategoryRepository.delete(removeCategory);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean modifyGoalCategoryBudget(GoalCategoryModifyDto goalCategoryModifyDto) {
        if (!userService.currentUser().equals(goalCategoryModifyDto.getUserId())) {
            return false;
        }
        GoalCategory goalCategory = goalCategoryRepository.findById(goalCategoryModifyDto.getGoalCategoryId()).orElseThrow();
        goalCategory.modifyBudget(goalCategoryModifyDto.getChangeBudget());
        return true;
    }
}
