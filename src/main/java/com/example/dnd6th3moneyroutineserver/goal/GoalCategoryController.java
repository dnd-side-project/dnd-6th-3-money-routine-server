package com.example.dnd6th3moneyroutineserver.goal;

import com.example.dnd6th3moneyroutineserver.common.CustomResponse;
import com.example.dnd6th3moneyroutineserver.common.ResponseMessage;
import com.example.dnd6th3moneyroutineserver.common.StatusCode;
import com.example.dnd6th3moneyroutineserver.customCategory.CustomCategoryRepository;
import com.example.dnd6th3moneyroutineserver.goal.dto.DirectAddGoalCategoryDto;
import com.example.dnd6th3moneyroutineserver.goal.dto.GoalCategoryExpenseInsertDto;
import com.example.dnd6th3moneyroutineserver.goal.dto.GoalCategoryModifyDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goal-category")
@RequiredArgsConstructor
public class GoalCategoryController {

    private final GoalCategoryService goalCategoryService;
    private final GoalCategoryRepository goalCategoryRepository;
    private final CustomCategoryRepository customCategoryRepository;

    /**
     * 유저의 지출 카테고리 리스트 반환
     */
    @GetMapping
    @ApiOperation(value = "유저 카테고리", notes = "유저의 카테고리 리스트 조회")
    public ResponseEntity getGoalCategoryList() {
        return new ResponseEntity(CustomResponse.response(StatusCode.OK, ResponseMessage.GOAL_CATEGORY_LIST_SUCCESS, goalCategoryService.getGoalCategoryList()), HttpStatus.OK);
    }

    /**
     * GoalCategory 삭제
     */
    @DeleteMapping
    @ApiOperation(value = "목표 카테고리 제거", notes = "예산 수정 페이지의 분야 별 목표 카테고리 제거")
    public ResponseEntity removeGoalCategory(@RequestBody Long goalCategoryId) {
        return new ResponseEntity(CustomResponse.response
                (StatusCode.OK, ResponseMessage.REMOVE_GOAL_CATEGORY_SUCCESS, goalCategoryService.remove(goalCategoryId))
                , HttpStatus.OK);
    }

    @PatchMapping
    @ApiOperation(value = "목표 카테고리 수정", notes = "목표 카테고리 예산 수정")
    public ResponseEntity modifyGoalCategoryBudget(@RequestBody GoalCategoryModifyDto goalCategoryModifyDto) {
        return new ResponseEntity(
                CustomResponse.response(StatusCode.OK, ResponseMessage.MODIFY_GOAL_CATEGORY_SUCCESS, goalCategoryService.modifyGoalCategoryBudget(goalCategoryModifyDto))
                , HttpStatus.OK);
    }

    @PostMapping("/custom")
    @ApiOperation(value = "지출 분야 직접 추가", notes = "지출 분야 추가 페이지의 직접 추가")
    public ResponseEntity addCustomCategoryWithGoalCategory(@RequestBody DirectAddGoalCategoryDto directAddGoalCategoryDto) {
        return new ResponseEntity(CustomResponse.response(StatusCode.OK, ResponseMessage.DIRECT_ADD_SUCCESS, goalCategoryService.directAddGoalCategory(directAddGoalCategoryDto)), HttpStatus.CREATED);
    }

//    @GetMapping("/test")
//    public ResponseEntity testCategory(@RequestParam Long categoryId) {
//        return new ResponseEntity(CustomResponse.response(StatusCode.OK, ResponseMessage.REMOVE_GOAL_CATEGORY_SUCCESS, goalCategoryRepository.findGoalAndUserById(categoryId).getGoal().getUser().getEmail()), HttpStatus.OK);
//    }
}
