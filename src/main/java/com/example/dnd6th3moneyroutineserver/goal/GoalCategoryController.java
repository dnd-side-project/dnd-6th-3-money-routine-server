package com.example.dnd6th3moneyroutineserver.goal;

import com.example.dnd6th3moneyroutineserver.common.CustomResponse;
import com.example.dnd6th3moneyroutineserver.common.ResponseMessage;
import com.example.dnd6th3moneyroutineserver.common.StatusCode;
import com.example.dnd6th3moneyroutineserver.goal.dto.GoalCategoryExpenseInsertDto;
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

    /**
     * 유저의 지출 카테고리 리스트 반환
     */
    @GetMapping
    @ApiOperation(value = "유저 카테고리", notes = "유저의 카테고리 리스트 조회")
    public ResponseEntity getGoalCategoryList() {
        return new ResponseEntity(CustomResponse.response(StatusCode.OK, ResponseMessage.GOAL_CATEGORY_LIST_SUCCESS, goalCategoryService.getGoalCategoryList()), HttpStatus.OK);
    }
}
