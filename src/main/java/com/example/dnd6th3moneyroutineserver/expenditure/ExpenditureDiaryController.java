package com.example.dnd6th3moneyroutineserver.expenditure;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/expenditure-diary")
@RequiredArgsConstructor
public class ExpenditureDiaryController {

//    /**
//     * 일별 감정 다이어리 반환
//     */
//    @GetMapping
//    public List getDailyEmotionDiary() {
//
//    }
//
//    /**
//     * 주별 감정 다이어리 반환
//     */
//    @GetMapping
//    public List getWeeklyEmotionDiary() {
//
//    }
//
//
//    /**
//     * 월별 감정 다이어리 반환
//     */
//    @GetMapping
//    public List getMonthlyEmotionDiary() {
//
//    }
}
