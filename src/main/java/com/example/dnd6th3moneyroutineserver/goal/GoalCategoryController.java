package com.example.dnd6th3moneyroutineserver.goal;

import com.example.dnd6th3moneyroutineserver.goal.dto.GoalCategoryExpenseInsertDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goal-category")
@RequiredArgsConstructor
public class GoalCategoryController {

    /**
     * 유저의 지출 카테고리 리스트 반환
     */
    @GetMapping("/{userId}")
    public List<GoalCategoryExpenseInsertDto> getGoalCategoryList(@PathVariable Long userId) {
        return null;
    }
}
