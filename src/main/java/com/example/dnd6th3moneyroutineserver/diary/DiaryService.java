package com.example.dnd6th3moneyroutineserver.diary;

import com.example.dnd6th3moneyroutineserver.expenditure.entity.Emotion;
import com.example.dnd6th3moneyroutineserver.expenditure.entity.Expenditure;
import com.example.dnd6th3moneyroutineserver.expenditure.repository.ExpenditureRepository;
import com.example.dnd6th3moneyroutineserver.user.service.UserService;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final UserService userService;
    private final ExpenditureRepository expenditureRepository;

    @Transactional
    public List<WeeklyDiaryDto> getWeeklyData(int year, int month, int week) {
        Long userId = userService.currentUser();
        LocalDate[] weeklyStartAndEndDate = findWeeklyStartDateAndEndDate(year, month, week);
        List<Expenditure> weeklyExpenditure = expenditureRepository.findByDateBetweenAndUserIdOrderByDateAsc(weeklyStartAndEndDate[0], weeklyStartAndEndDate[1], userId);
        TreeMap<LocalDate, List<DailyExpenditureEmotionDto>> weeklyMap = new TreeMap<>(Comparator.naturalOrder());
        List<WeeklyDiaryDto> weeklyDiaryDtoList = new ArrayList<>();

        LocalDate startDate = weeklyStartAndEndDate[0];

        for (int i = 0; i < 7; i++) {
            weeklyMap.put(startDate.plusDays(i), new ArrayList<>());
        }

        for (Expenditure expenditure : weeklyExpenditure) {
            weeklyMap.get(expenditure.getDate()).add(DailyExpenditureEmotionDto.builder()
                    .emotion(expenditure.getEmotion())
                    .expenditureId(expenditure.getId())
                    .build());
        }

        for (Map.Entry<LocalDate, List<DailyExpenditureEmotionDto>> localDateListEntry : weeklyMap.entrySet()) {
            weeklyDiaryDtoList.add(WeeklyDiaryDto.builder()
                            .date(localDateListEntry.getKey())
                            .dailyExpenditureEmotionDtoList(localDateListEntry.getValue())
                    .build());
        }

        return weeklyDiaryDtoList;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class WeeklyDiaryDto {
        private LocalDate date;
        private List<DailyExpenditureEmotionDto> dailyExpenditureEmotionDtoList;
    }

    @Transactional
    public Map<Emotion, Integer> getMonthlyBestEmotionBy(LocalDate date) {
        List<Expenditure> expenditureList = expenditureRepository.findAllByDateBetweenAndUserId(date, date.withDayOfMonth(date.lengthOfMonth()), userService.currentUser());
        Map<Emotion, Integer> emotionMap = new HashMap<>();

        for (Expenditure expenditure : expenditureList) {
            emotionMap.put(expenditure.getEmotion(), emotionMap.getOrDefault(expenditure.getEmotion(), 0) + 1);
        }

        return emotionMap;
    }

    @Transactional
    public List<MonthlyDiaryResponse> getMonthlyDiaryBy(LocalDate date, Emotion emotion) {
        List<Expenditure> expenditureList = expenditureRepository.findByDateBetweenAndUserIdAndEmotion(date.withDayOfMonth(1), date.withDayOfMonth(date.lengthOfMonth()), userService.currentUser(), emotion);

        Map<String, List<Expenditure>> expenditureMap = new HashMap<>();
        for (Expenditure expenditure : expenditureList) {
            if (expenditure.getCategory() == null && !expenditureMap.containsKey(expenditure.getCustomCategory().getName())) {
                expenditureMap.put(expenditure.getCustomCategory().getName(), new ArrayList<>());
            }
            if (expenditure.getCategory() != null && !expenditureMap.containsKey(expenditure.getCategory().getName())) {
                expenditureMap.put(expenditure.getCategory().getName(), new ArrayList<>());
            }
            expenditureMap.get(expenditure.isCustom() ? expenditure.getCustomCategory().getName() : expenditure.getCategory().getName()).add(expenditure);
        }

        List<MonthlyDiaryResponse> monthlyDiaryResponseList = new ArrayList<>();
        for (String name : expenditureMap.keySet()) {
            List<ExpenditureDiaryDto> expenditureDiaryDtoList = new ArrayList<>();
            for (Expenditure expenditure : expenditureMap.get(name)) {
                expenditureDiaryDtoList.add(
                        ExpenditureDiaryDto.builder()
                                .date(expenditure.getDate())
                                .emotionDetail(expenditure.getEmotionDetail())
                                .expenseDetail(expenditure.getExpenseDetail())
                                .build()
                );
            }
            monthlyDiaryResponseList.add(MonthlyDiaryResponse.builder()
                    .name(name)
                    .count(expenditureDiaryDtoList.size())
                    .expenditureDiaryDtoList(expenditureDiaryDtoList)
                    .build());
        }
        monthlyDiaryResponseList.sort((o1, o2) -> o2.count - o1.count);
        return monthlyDiaryResponseList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class MonthlyDiaryResponse {
        private String name;
        private int count;
        private List<ExpenditureDiaryDto> expenditureDiaryDtoList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class ExpenditureDiaryDto {
        private String expenseDetail;
        private String emotionDetail;
        private LocalDate date;
    }

    @Transactional
    public List<DailyDiaryDto> getDailyExpenseInWeeklyDiary(LocalDate date) {
        Long userId = userService.currentUser();
        List<Expenditure> expenditureList = expenditureRepository.findByDateAndUserId(date, userId);
        Map<Emotion, List<Expenditure>> expenditureEmotionMap = new HashMap<>();

        for (Expenditure expenditure : expenditureList) {
            if (!expenditureEmotionMap.containsKey(expenditure.getEmotion())) {
                expenditureEmotionMap.put(expenditure.getEmotion(), new ArrayList<>());
            }
            expenditureEmotionMap.get(expenditure.getEmotion()).add(expenditure);
        }

        List<DailyDiaryDto> dailyDiaryDtos = new ArrayList<>();

        for (Emotion emotion : expenditureEmotionMap.keySet()) {
            List<Expenditure> list = expenditureEmotionMap.get(emotion);
            int amount = 0;
            int count = 0;
            ArrayList<ExpenditureDto> expenditureDtos = new ArrayList<>();
            for (Expenditure expenditure : list) {
                amount += expenditure.getExpense();
                count++;
                expenditureDtos.add(ExpenditureDto.builder()
                        .expenseDetail(expenditure.getExpenseDetail())
                        .expense(expenditure.getExpense().intValue())
                        .date(expenditure.getDate())
                        .expenditureId(expenditure.getId())
                        .emotionDetail(expenditure.getEmotionDetail())
                        .name((expenditure.getCategory() == null) ? expenditure.getCustomCategory().getName() : expenditure.getCategory().getName())
                        .detail((expenditure.getCategory() == null) ? expenditure.getCustomCategory().getDetail() : expenditure.getCategory().getDetail())
                        .categoryId((expenditure.getCategory() == null) ? expenditure.getCustomCategory().getId() : expenditure.getCategory().getId())
                        .isCustom(expenditure.getCategory() == null)
                        .build());
            }
            dailyDiaryDtos.add(DailyDiaryDto.builder()
                    .emotion(emotion)
                    .amount(amount)
                    .count(count)
                    .expenditureDtoList(expenditureDtos)
                    .build());
        }

        return dailyDiaryDtos;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class DailyDiaryDto {
        private Emotion emotion;
        private int count;
        private int amount;
        private List<ExpenditureDto> expenditureDtoList;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class ExpenditureDto {
        private Long expenditureId;
        private String emotionDetail;
        private Long categoryId;
        private String name;
        private String detail;
        private String expenseDetail;
        private LocalDate date;
        private int expense;
        private boolean isCustom;
    }



    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class DailyExpenditureEmotionDto {
        private Emotion emotion;
        private Long expenditureId;
    }

    // xxxx 년도 xx월 x주차의 시작 LocalDate ~ 끝 LocalDate 배열 반환
    private LocalDate[] findWeeklyStartDateAndEndDate(int year, int month, int week) {
        LocalDate startDateOfMonth = LocalDate.of(year, month, 1);
        int dayOfWeek = startDateOfMonth.getDayOfWeek().getValue();
        LocalDate startDateOfWeek = startDateOfMonth.plusWeeks(week - 1).minusDays(dayOfWeek);
        LocalDate endDateOfWeek = startDateOfMonth.plusWeeks(week - 1).plusDays(7 - dayOfWeek);

        return new LocalDate[]{startDateOfWeek, endDateOfWeek};
    }
}
