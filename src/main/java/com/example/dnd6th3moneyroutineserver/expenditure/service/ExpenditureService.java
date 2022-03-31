package com.example.dnd6th3moneyroutineserver.expenditure.service;

import com.example.dnd6th3moneyroutineserver.category.Category;
import com.example.dnd6th3moneyroutineserver.category.CategoryRepository;
import com.example.dnd6th3moneyroutineserver.customCategory.CustomCategory;
import com.example.dnd6th3moneyroutineserver.customCategory.CustomCategoryRepository;
import com.example.dnd6th3moneyroutineserver.expenditure.dto.*;
import com.example.dnd6th3moneyroutineserver.expenditure.entity.CategoryType;
import com.example.dnd6th3moneyroutineserver.expenditure.entity.Expenditure;
import com.example.dnd6th3moneyroutineserver.expenditure.repository.ExpenditureRepository;
import com.example.dnd6th3moneyroutineserver.goal.Goal;
import com.example.dnd6th3moneyroutineserver.goal.GoalCategory;
import com.example.dnd6th3moneyroutineserver.goal.GoalCategoryRepository;
import com.example.dnd6th3moneyroutineserver.goal.GoalRepository;
import com.example.dnd6th3moneyroutineserver.user.entity.User;
import com.example.dnd6th3moneyroutineserver.user.repository.UserRepository;
import com.example.dnd6th3moneyroutineserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ExpenditureService {

    private final ExpenditureRepository expenditureRepository;
    private final GoalRepository goalRepository;
    private final GoalCategoryRepository goalCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final CustomCategoryRepository customCategoryRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Transactional
    public Long write(ExpenditureWriteDto expenditureWriteDto) {

        Long userId = userService.currentUser();
        User user = userRepository.findById(userId).orElseThrow();

        Expenditure expenditure = Expenditure.builder()
                .user(user)
                .date(expenditureWriteDto.getDate())
                .expense(expenditureWriteDto.getExpense())
                .expenseDetail(expenditureWriteDto.getExpenseDetail())
                .isCustom(expenditureWriteDto.isCustom())
                .emotion(expenditureWriteDto.getEmotion())
                .emotionDetail(expenditureWriteDto.getEmotionDetail())
                .build();

        if (!expenditureWriteDto.isCustom()) {
            Category category = categoryRepository.getById(expenditureWriteDto.getCategoryId());
            expenditure.setCategory(category);

            //userId, date, category
            GoalCategory goalCategory = goalCategoryRepository.findByGoalDateAndUserIdAndCategoryId(expenditureWriteDto.getDate().withDayOfMonth(1), userId, category.getId());
            goalCategory.addExpense(expenditureWriteDto.getExpense().intValue());
        }
        else {
            CustomCategory customCategory = customCategoryRepository.getById(expenditureWriteDto.getCategoryId());
            expenditure.setCustomCategory(customCategory);

            //userId, date, customcategory
            GoalCategory goalCategory = goalCategoryRepository.findByGoalDateAndUserIdAndCustomCategoryId(expenditureWriteDto.getDate().withDayOfMonth(1), userId, customCategory.getId());
            goalCategory.addExpense(expenditureWriteDto.getExpense().intValue());
        }

        return expenditureRepository.save(expenditure).getId();
    }

    @Transactional
    public StatisticsResponseDto getStatistics(String term, LocalDate startDate, LocalDate endDate) {

        Long userId = userService.currentUser();

        //1. 유저의 goal을 가져온다.

        //2. 유저의 goalCategoryList를 가져온다.
        List<GoalCategory> goalCategoryList = goalCategoryRepository
                .findByGoalDateAndUserId(startDate.withDayOfMonth(1), userId);

        //B.전체 지출 금액
        Long totalExpense = 0L;

        List<GoalCategoryInfoDto> goalCategoryInfoDtoList = new ArrayList<>();

        //3. 카테고리별로 지출 내역을 조회한다.
        for (GoalCategory gc : goalCategoryList) {

            CategoryType categoryType = new CategoryType();
            List<Expenditure> expenditureList;
            String categoryName = "";

            if (gc.getCategory() != null){
                categoryType = new CategoryType(gc.getCategory().getId(), false);
                categoryName = gc.getCategory().getName();
                expenditureList = expenditureRepository.findAllByDateBetweenAndUserIdAndCategory(startDate, endDate, userId, gc.getCategory());
            }
            else {
                categoryType = new CategoryType(gc.getCustomCategory().getId(), true);
                categoryName = gc.getCustomCategory().getName();
                expenditureList = expenditureRepository.findAllByDateBetweenAndUserIdAndCustomCategory(startDate, endDate, userId, gc.getCustomCategory());
            }

            if (expenditureList.size() == 0) {
                continue;
            }

            //3-1. 지출 내역을 순회하면서 지출의 합을 구한다.
            Long categoryExpenseSum = 0L;
            List<WeeklyExpenditureDetailDto> weeklyExpenditureDetailDtoList= new ArrayList<>();
            for (Expenditure exp : expenditureList) {
                WeeklyExpenditureDetailDto wdto = new WeeklyExpenditureDetailDto(exp);
                if (term.equals("weekly")) {
                    weeklyExpenditureDetailDtoList.add(wdto);
                }
                categoryExpenseSum += wdto.getExpense();
            }

            GoalCategoryInfoDto goalCategoryInfoDto = GoalCategoryInfoDto.builder()
                    .categoryType(categoryType)
                    .categoryName(categoryName)
                    .expense(categoryExpenseSum)
                    .weeklyExpenditureDetailDtoList(weeklyExpenditureDetailDtoList)
                    .build();

            goalCategoryInfoDtoList.add(goalCategoryInfoDto);
            totalExpense += categoryExpenseSum;
        }

        for (GoalCategoryInfoDto gci : goalCategoryInfoDtoList) {
            double per = (gci.getExpense()/(float) totalExpense) * 100;
            gci.setPercentage(Math.round(per*100) / 100.0);
        }

        Comparator<GoalCategoryInfoDto> comparator = new Comparator<GoalCategoryInfoDto>() {
            @Override
            public int compare(GoalCategoryInfoDto o1, GoalCategoryInfoDto o2) {
                return (int) (o2.getExpense() - o1.getExpense());
            }
        };

        Collections.sort(goalCategoryInfoDtoList, comparator);

        if (goalCategoryInfoDtoList.size() == 0) {
            return StatisticsResponseDto.builder()
                    .totalExpense(totalExpense)
                    .goalCategoryInfoDtoList(goalCategoryInfoDtoList)
                    .build();
        }
        else {
            return StatisticsResponseDto.builder()
                    .topCategory(goalCategoryInfoDtoList.get(0).getCategoryName())
                    .totalExpense(totalExpense)
                    .goalCategoryInfoDtoList(goalCategoryInfoDtoList)
                    .build();
        }

        //StatisticsResponseDto
            //A.최다 지출 분야
            //B.전체 지출 금액
            //C.분야별 정보(GoalCategoryInfoDto)
                //C-0.분야 타입(categoryId, isCustom)
                //C-a.분야 이름
                //C-b.분야 비율
                //C-c.분야 금액
                //C-d.분야 지출 내역(WeeklyExpenditureDetailDto)
                    //C-d-1.지출 날짜
                    //C-d-2.지출 상세
                    //C-d-3.지출 금액
    }

    @Transactional
    public Map<LocalDate, List<MonthlyDetailsDto>> getMonthlyDetails(LocalDate startDate, LocalDate endDate, Long categoryId, boolean isCustom) {
        Long userId = userService.currentUser();
        List<Expenditure> expenditureList;
        if (!isCustom){
            Category category = categoryRepository.getById(categoryId);
            expenditureList = expenditureRepository.findAllByDateBetweenAndUserIdAndCategory(startDate, endDate, userId, category);
        }
        else {
            CustomCategory customCategory = customCategoryRepository.getById(categoryId);
            expenditureList = expenditureRepository.findAllByDateBetweenAndUserIdAndCustomCategory(startDate, endDate, userId, customCategory);
        }

        Map<LocalDate, List<MonthlyDetailsDto>> monthlyDetailsDtoMap = new HashMap<>();

        for (Expenditure exp: expenditureList) {
            if (monthlyDetailsDtoMap.containsKey(exp.getDate())) {
                monthlyDetailsDtoMap.get(exp.getDate()).add(new MonthlyDetailsDto(exp.getExpenseDetail(), exp.getExpense()));
            } else {
                List<MonthlyDetailsDto> monthlyDetailsDtoList = new ArrayList<>();
                monthlyDetailsDtoList.add(new MonthlyDetailsDto(exp.getExpenseDetail(), exp.getExpense()));
                monthlyDetailsDtoMap.put(exp.getDate(), monthlyDetailsDtoList);
            }
        }
        return monthlyDetailsDtoMap;
    }

    @Transactional
    public WeeklyTendencyResponseDto getWeeklyTendency(LocalDate currentDate) {

        Long userId = userService.currentUser();
        Goal goal = goalRepository.findByStartDateAndUserId(LocalDate.now().withDayOfMonth(1), userId);

        while (!currentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            currentDate = currentDate.minusDays(1);
        }

        List<WeekExpenseInfoDto> weekExpenseInfoDtoList = new ArrayList<>();

        LocalDate endDate;
        for (int i=0; i<5; i++) {
            endDate = currentDate.plusDays(6);
            List<Expenditure> expenditureList = expenditureRepository.findAllByDateBetweenAndUserId(currentDate, endDate, userId);

            //총사용금액 계산
            Long weekExpense = 0L;
            for (Expenditure exp : expenditureList) {
                weekExpense += exp.getExpense();
            }

            weekExpenseInfoDtoList.add(WeekExpenseInfoDto.builder()
                    .startDate(currentDate)
                    .endDate(endDate)
                    .weekExpense(weekExpense)
                    .build());

            currentDate = currentDate.minusWeeks(1);
        }

        Collections.reverse(weekExpenseInfoDtoList);

        return WeeklyTendencyResponseDto.builder()
                .recommendExpense(goal.getTotalBudget() / weekExpenseInfoDtoList.size())
                .weekExpenseInfoDtoList(weekExpenseInfoDtoList)
                .build();
    }

    @Transactional
    public MonthlyTendencyResponseDto getMonthlyTendency(LocalDate currentDate) {

        Long userId = userService.currentUser();

        List<MonthExpenseInfoDto> monthExpenseInfoDtoList = new ArrayList<>();

        currentDate = currentDate.withDayOfMonth(1);
        LocalDate endDate;
        for (int i=0; i<5; i++) {
            endDate = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
            List<Expenditure> expenditureList = expenditureRepository.findAllByDateBetweenAndUserId(currentDate, endDate, userId);

            //총사용금액 계산
            Long monthExpense = 0L;
            for (Expenditure exp : expenditureList) {
                monthExpense += exp.getExpense();
            }

            Goal goal = goalRepository.findByStartDateAndUserId(currentDate, userId);

            if (goal != null) {
                monthExpenseInfoDtoList.add(MonthExpenseInfoDto.builder()
                        .month(currentDate.getMonthValue())
                        .budget(goal.getTotalBudget())
                        .monthExpense(monthExpense.intValue())
                        .build());
            } else {
                monthExpenseInfoDtoList.add(MonthExpenseInfoDto.builder()
                        .month(currentDate.getMonthValue())
                        .monthExpense(monthExpense.intValue())
                        .build());
            }

            currentDate = currentDate.minusMonths(1);
        }

        Collections.reverse(monthExpenseInfoDtoList);

        return new MonthlyTendencyResponseDto(monthExpenseInfoDtoList);
    }

}
