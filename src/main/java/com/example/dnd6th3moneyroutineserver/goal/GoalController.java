package com.example.dnd6th3moneyroutineserver.goal;

import com.example.dnd6th3moneyroutineserver.common.CustomResponse;
import com.example.dnd6th3moneyroutineserver.common.ResponseMessage;
import com.example.dnd6th3moneyroutineserver.common.StatusCode;
import com.example.dnd6th3moneyroutineserver.goal.dto.GoalCreateDto;
import com.example.dnd6th3moneyroutineserver.goal.dto.GoalDetailDto;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/goal")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    /**
     * 새 목표 생성
     */
    @PostMapping
    @ApiOperation(value = "목표생성", notes = "목표 + 목표 카테고리 생성.")
    public ResponseEntity createGoal(@RequestBody GoalCreateDto goalCreateDto) {
        return new ResponseEntity(CustomResponse.response(StatusCode.OK, ResponseMessage.CREATE_GOAL_SUCCESS, goalService.addGoal(goalCreateDto)), HttpStatus.OK);
    }

    /**
     * 메인 화면에서 목표 정보를 반환
     * @Param userId, date
     */
    @GetMapping("/info")
    @ApiOperation(value = "목표정보", notes = "20XX년 XX월의 목표 정보 반환")
    public ResponseEntity getGoalList(@RequestParam @NotNull Long userId, @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam LocalDate date) {
        return new ResponseEntity(CustomResponse.response(StatusCode.OK, ResponseMessage.GOAL_INFO_SUCCESS, goalService.infoWithDate(userId, date)), HttpStatus.OK);
    }

    /**
     * 이전 달의 목표를 그대로 이어받아서 진행
     */
    @PostMapping("/continue")
    public Long continueGoal(@RequestBody Long userId) {
        return 1L;
    }


}
