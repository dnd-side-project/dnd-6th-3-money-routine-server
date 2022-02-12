package com.example.dnd6th3moneyroutineserver.expenditure.controller;

import com.example.dnd6th3moneyroutineserver.common.CustomResponse;
import com.example.dnd6th3moneyroutineserver.common.ResponseMessage;
import com.example.dnd6th3moneyroutineserver.common.StatusCode;
import com.example.dnd6th3moneyroutineserver.expenditure.service.ExpenditureService;
import com.example.dnd6th3moneyroutineserver.expenditure.dto.ExpenditureWriteDto;
import com.example.dnd6th3moneyroutineserver.expenditure.dto.StatisticsRequestDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expenditure")
public class ExpenditureController {

    private final ExpenditureService expenditureService;

    @PostMapping
    @ApiOperation(value = "지출 입력", notes = "지출을 입력한다.")
    public ResponseEntity writeExpenditure(@RequestBody ExpenditureWriteDto expenditureWriteDto) {
        return new ResponseEntity(CustomResponse
                .response(StatusCode.OK, ResponseMessage.WRITE_SUCCESS, expenditureService.write(expenditureWriteDto)), HttpStatus.OK);
    }

    @GetMapping("/statistics/weekly/{startDate}/{endDate}")
    @ApiOperation(value = "주별 소비 내역 조회", notes = "가장 많이 지출한 분야명, 총 지출 금액, 분야별 지출 금액, 비율 및 지출 내역")
    public ResponseEntity getWeeklyStatistics(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return new ResponseEntity(CustomResponse
                .response(StatusCode.OK, ResponseMessage.WEEKLY_STATISTICS_SUCCESS, expenditureService.getStatistics("weekly", startDate, endDate)), HttpStatus.OK);
    }

    @GetMapping("/statistics/monthly/{startDate}/{endDate}")
    @ApiOperation(value = "월별 소비 내역 조회", notes = "가장 많이 지출한 분야명, 총 지출 금액, 분야별 지출 금액, 비율 (지출 내역은 제외)")
    public ResponseEntity getMonthlyStatistics(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return new ResponseEntity(CustomResponse
                .response(StatusCode.OK, ResponseMessage.MONTHLY_STATISTICS_SUCCESS, expenditureService.getStatistics("monthly", startDate, endDate)), HttpStatus.OK);
    }

    @GetMapping("/statistics/monthly/detail/{categoryId}/{isCustom}")
    @ApiOperation(value = "월별 카테고리별 소비 내역 상세 조회", notes = "해당 월 해당 카테고리에 대한 소비 내역 상세 조회")
    public ResponseEntity getMonthlyDetails(@PathVariable Long categoryId, @PathVariable boolean isCustom, StatisticsRequestDto statisticsRequestDto) {
        return new ResponseEntity(CustomResponse
                .response(StatusCode.OK, ResponseMessage.MONTHLY_STATISTICS_SUCCESS, expenditureService.monthlyDetails(categoryId, isCustom, statisticsRequestDto)), HttpStatus.OK);
    }

    @GetMapping("/weekly")
    @ApiOperation(value = "주별 소비 동향 조회", notes = "주별 소비 동향을 조회한다. 막대 그래프 영역")
    public ResponseEntity getWeeklyTendency() {
        return new ResponseEntity(CustomResponse
                .response(StatusCode.OK, ResponseMessage.WEEKLY_TENDENCY_SUCCESS, expenditureService.getWeekly()), HttpStatus.OK);
    }

    @GetMapping("/monthly")
    @ApiOperation(value = "월별 소비 동향 조회", notes = "월별 소비 동향을 조회한다. 막대 그래프 영역")
    public ResponseEntity getMonthlyTendency() {
        return new ResponseEntity(CustomResponse
                .response(StatusCode.OK, ResponseMessage.MONTHLY_TENDENCY_SUCCESS, expenditureService.getMonthly()), HttpStatus.OK);
    }
}
