package com.example.dnd6th3moneyroutineserver.expenditure;

import com.example.dnd6th3moneyroutineserver.expenditure.dto.ExpenditureCreateDto;
import com.example.dnd6th3moneyroutineserver.expenditure.dto.ExpenditureStatisticsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/expenditure")
@RequiredArgsConstructor
public class ExpenditureController {

    /**
     * 지출 입력
     */
    @PostMapping
    public Long create(@RequestBody ExpenditureCreateDto expenditureCreateDto) {
        return 1L;
    }

    /**
     * 기간 소비 내역 반환
     * 원형 그래프 영역
     * @Param UserId, Start Date
     */
    @GetMapping("/statistics/{startDate}/{endDate}/{userId}")
    public ExpenditureStatisticsDto getExpenditureStatistics(@RequestParam Date startDate, @RequestParam Date endDate, @RequestParam Long userId) {
        return null;
    }

    /**
     * 주별 소비 내역 반환
     * 막대 그래프 영역
     */
    @GetMapping("/weekly/{userId}")
    public List<Integer> getWeeklyTendency(@RequestParam Long userId) {
        return null;
    }

    /**
     * 월별 소비 내역 반환
     * 막대 그래프 영역
     */
    @GetMapping("/monthly/{userId}")
    public List<Integer> getMonthlyTendency(@RequestParam Long userId) {
        return null;
    }
}
