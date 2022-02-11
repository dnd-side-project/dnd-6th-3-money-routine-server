package com.example.dnd6th3moneyroutineserver.expenditure.service;

import com.example.dnd6th3moneyroutineserver.category.Category;
import com.example.dnd6th3moneyroutineserver.category.CategoryRepository;
import com.example.dnd6th3moneyroutineserver.customCategory.CustomCategory;
import com.example.dnd6th3moneyroutineserver.customCategory.CustomCategoryRepository;
import com.example.dnd6th3moneyroutineserver.expenditure.dto.*;
import com.example.dnd6th3moneyroutineserver.expenditure.entity.CategoryType;
import com.example.dnd6th3moneyroutineserver.expenditure.entity.Expenditure;
import com.example.dnd6th3moneyroutineserver.expenditure.repository.ExpenditureRepository;
import com.example.dnd6th3moneyroutineserver.goal.GoalCategory;
import com.example.dnd6th3moneyroutineserver.goal.GoalCategoryRepository;
import com.example.dnd6th3moneyroutineserver.user.entity.User;
import com.example.dnd6th3moneyroutineserver.user.repository.UserRepository;
import com.example.dnd6th3moneyroutineserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ExpenditureService {

    private final ExpenditureRepository expenditureRepository;
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
    public StatisticsResponseDto weeklyStatistics(LocalDate startDate, LocalDate endDate) {

        Long userId = userService.currentUser();
        User user = userRepository.findById(userId).orElseThrow();

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

            //3-1. 지출 내역을 순회하면서 지출의 합을 구한다.
            Long categoryExpenseSum = 0L;
            List<WeeklyExpenditureDetailDto> weeklyExpenditureDetailDtoList= new ArrayList<>();
            for (Expenditure exp : expenditureList) {
                WeeklyExpenditureDetailDto wdto = new WeeklyExpenditureDetailDto(exp);
                weeklyExpenditureDetailDtoList.add(wdto);
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
            gci.setPercentage(gci.getExpense()/totalExpense * 100);
        }

        Comparator<GoalCategoryInfoDto> comparator = new Comparator<GoalCategoryInfoDto>() {
            @Override
            public int compare(GoalCategoryInfoDto o1, GoalCategoryInfoDto o2) {
                return (int) (o1.getExpense() - o2.getExpense());
            }
        };

        Collections.sort(goalCategoryInfoDtoList, comparator);

        return StatisticsResponseDto.builder()
                .topCategory(goalCategoryInfoDtoList.get(0).getCategoryName())
                .totalExpense(totalExpense)
                .goalCategoryInfoDtoList(goalCategoryInfoDtoList)
                .build();

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
    public StatisticsResponseDto monthlyStatistics(LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Transactional
    public StatisticsResponseDto monthlyDetails(Long categoryId, boolean isCustom, StatisticsRequestDto statisticsRequestDto) {
        return null;
    }

    @Transactional
    public StatisticsResponseDto getStatistics(StatisticsRequestDto statisticsRequestDto) {

//        List<WeeklyGoalCategoryInfoDto> expenditureCategoryDtoList = new ArrayList<>();
//
//        Long userId = userService.currentUser();
//
//        //기간 내 카테고리별 소비 내역(expense, categoryId, customCategoryId, isCustom)을 모두 불러온다
//        List<Expenditure> expenditureList = expenditureRepository.findAllByDateBetweenAndUserId(statisticsRequestDto.getStartDate(), statisticsRequestDto.getEndDate(), userId);
//
//        //카테고리별 총액을 계산한다
//        HashMap<CategoryType, Long> expensePerCategory = new HashMap<>();
//
//        for (Expenditure exp : expenditureList) {
//            CategoryType categoryType = new CategoryType(exp.getCategoryId(), exp.isCustom());
//            if (expensePerCategory.containsKey(categoryType)) {
//                Long expense = expensePerCategory.get(categoryType);
//                expense += exp.getExpense();
//                expensePerCategory.put(categoryType, expense);
//            }
//            else {
//                expensePerCategory.put(categoryType, exp.getExpense());
//            }
//        }
//
//        //카테고리별 비율을 계산하고 리스트를 생성한다
//        Long totalExpense = 0L;
//        for (Map.Entry<CategoryType, Long> entry : expensePerCategory.entrySet()) {
//            totalExpense += entry.getValue();
//        }
//
//        Long maxExpense = 0L;
//        for (Map.Entry<CategoryType, Long> entry : expensePerCategory.entrySet()) {
//            String categoryName = "";
//            if (!entry.getKey().isCustom()) {
//                categoryName = categoryRepository.getById(entry.getKey().getCategoryId()).getName();
//            }
//            else {
//                categoryName = customCategoryRepository.getById(entry.getKey().getCategoryId()).getName();
//            }
//            maxExpense = Math.max(maxExpense, entry.getValue());
//            expenditureCategoryDtoList.add(new WeeklyGoalCategoryInfoDto(entry.getKey(), categoryName, entry.getValue(), entry.getValue()/totalExpense * 100));
//        }
//
//        Comparator<WeeklyGoalCategoryInfoDto> comparator = new Comparator<WeeklyGoalCategoryInfoDto>() {
//            @Override
//            public int compare(WeeklyGoalCategoryInfoDto o1, WeeklyGoalCategoryInfoDto o2) {
//                return (int) (o1.getExpense() - o2.getExpense());
//            }
//        };
//
//        Collections.sort(expenditureCategoryDtoList, comparator);
////        return new WeeklyStatisticsResponseDto(expenditureCategoryDtoList.get(0).getCategoryName(), totalExpense, expenditureCategoryDtoList);
        return null;
    }

    @Transactional
    public List<Integer> getWeekly() {

        return null;
    }

    @Transactional
    public List<Integer> getMonthly() {
        return null;
    }

}
