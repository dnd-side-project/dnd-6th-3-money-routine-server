package com.example.dnd6th3moneyroutineserver.expenditure.controller;

import com.example.dnd6th3moneyroutineserver.common.CustomResponse;
import com.example.dnd6th3moneyroutineserver.common.ResponseMessage;
import com.example.dnd6th3moneyroutineserver.common.StatusCode;
import com.example.dnd6th3moneyroutineserver.expenditure.service.ExpenditureService;
import com.example.dnd6th3moneyroutineserver.expenditure.dto.ExpenditureWriteDto;
import com.example.dnd6th3moneyroutineserver.expenditure.dto.StatisticsRequestDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/statistics")
    @ApiOperation(value = "기간 소비 내역 조회", notes = "기간동안 소비 내역을 조회한다. 원형 그래프 영역")
    public ResponseEntity getExpenditureStatistics(StatisticsRequestDto statisticsRequestDto) {
        return new ResponseEntity(CustomResponse
                .response(StatusCode.OK, ResponseMessage.STATISTICS_SUCCESS, expenditureService.getStatistics(statisticsRequestDto)), HttpStatus.OK);
    }

    @GetMapping("/weekly")
    @ApiOperation(value = "주별 소비 동향 조회", notes = "주별 소비 동향을 조회한다. 막대 그래프 영역")
    public ResponseEntity getWeeklyTendency() {
        return new ResponseEntity(CustomResponse
                .response(StatusCode.OK, ResponseMessage.WEEKLY_SUCCESS, expenditureService.getWeekly()), HttpStatus.OK);
    }

    @GetMapping("/monthly")
    @ApiOperation(value = "월별 소비 동향 조회", notes = "월별 소비 동향을 조회한다. 막대 그래프 영역")
    public ResponseEntity getMonthlyTendency() {
        return new ResponseEntity(CustomResponse
                .response(StatusCode.OK, ResponseMessage.MONTHLY_SUCCESS, expenditureService.getMonthly()), HttpStatus.OK);
    }
}
