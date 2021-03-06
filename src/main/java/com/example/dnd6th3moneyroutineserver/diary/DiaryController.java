package com.example.dnd6th3moneyroutineserver.diary;

import com.example.dnd6th3moneyroutineserver.common.CustomResponse;
import com.example.dnd6th3moneyroutineserver.common.ResponseMessage;
import com.example.dnd6th3moneyroutineserver.common.StatusCode;
import com.example.dnd6th3moneyroutineserver.expenditure.entity.Emotion;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @GetMapping("/weekly")
    @ApiOperation(value = "주차별 소비 다이어리 조회", notes = "(year)년도 (month)월 (week)주차 소비 다이어리 조회")
    public ResponseEntity getWeeklyDiary(@RequestParam("year") int year, @RequestParam("month") int month, @RequestParam("week") int week) {
        return new ResponseEntity(
                CustomResponse.response
                        (StatusCode.OK, ResponseMessage.WEEKLY_DIARY_SUCCESS, diaryService.getWeeklyData(year, month, week))
                , HttpStatus.OK
        );
    }

    @GetMapping("/daily")
    @ApiOperation(value = "특정 날짜의 소비 다이어리", notes = "특정 날짜의 소비 다이어리를 반환")
    public ResponseEntity getDailyDiary(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return new ResponseEntity(
                CustomResponse.response(StatusCode.OK, ResponseMessage.DAILY_DIARY_SUCCESS, diaryService.getDailyExpenseInWeeklyDiary(date))
                , HttpStatus.OK
        );
    }

    @GetMapping("/monthly-best")
    @ApiOperation(value = "최다 감정 월별 소비 데이터", notes = "월별 소비 감정 다이어리의 상단 부분에 쓰이는 가장 많은 감정 데이터와 수 반환")
    public ResponseEntity getMonthlyBestEmotion(@RequestParam("year") int year, @RequestParam("month") int month) {
        return new ResponseEntity(
                CustomResponse.response(StatusCode.OK, ResponseMessage.MONTHLY_DIARY_SUCCESS, diaryService.getMonthlyBestEmotionBy(LocalDate.of(year, month, 1))), HttpStatus.OK
        );
    }

    @GetMapping("/monthly")
    @ApiOperation(value = "월별 소비 다이어리", notes = "감정에 따른 월별 소비 다이어리 데이터 반환")
    public ResponseEntity getMonthlyDiaryBy(@RequestParam("year") int year, @RequestParam("month") int month, @RequestParam("emotion") Emotion emotion) {
        return new ResponseEntity(
                CustomResponse.response(StatusCode.OK, ResponseMessage.MONTHLY_DIARY_SUCCESS, diaryService.getMonthlyDiaryBy(LocalDate.of(year, month, 1), emotion)), HttpStatus.OK
        );
    }
}