package com.example.dnd6th3moneyroutineserver.expenditure.service;

import com.example.dnd6th3moneyroutineserver.category.CategoryRepository;
import com.example.dnd6th3moneyroutineserver.customCategory.CustomCategoryRepository;
import com.example.dnd6th3moneyroutineserver.expenditure.dto.ExpenditureCategoryDto;
import com.example.dnd6th3moneyroutineserver.expenditure.dto.ExpenditureWriteDto;
import com.example.dnd6th3moneyroutineserver.expenditure.dto.StatisticsRequestDto;
import com.example.dnd6th3moneyroutineserver.expenditure.dto.StatisticsResponseDto;
import com.example.dnd6th3moneyroutineserver.expenditure.entity.CategoryType;
import com.example.dnd6th3moneyroutineserver.expenditure.entity.Expenditure;
import com.example.dnd6th3moneyroutineserver.expenditure.repository.ExpenditureRepository;
import com.example.dnd6th3moneyroutineserver.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ExpenditureService {

    private final ExpenditureRepository expenditureRepository;
    private final CategoryRepository categoryRepository;
    private final CustomCategoryRepository customCategoryRepository;
    private final UserService userService;

    @Transactional
    public Long write(ExpenditureWriteDto expenditureWriteDto) {

        Long userId = userService.currentUser();

        Expenditure expenditure = Expenditure.builder()
                .userId(userId)
                .date(expenditureWriteDto.getDate())
                .expense(expenditureWriteDto.getExpense())
                .expenseDetail(expenditureWriteDto.getExpenseDetail())
                .isCustom(expenditureWriteDto.isCustom())
                .emotion(expenditureWriteDto.getEmotion())
                .emotionDetail(expenditureWriteDto.getEmotionDetail())
                .build();

        if (!expenditure.isCustom()) {
            expenditure.setCategoryId(expenditureWriteDto.getCategoryId());
        }
        else {
            expenditure.setCustomCategoryId(expenditureWriteDto.getCategoryId());
        }

        return expenditureRepository.save(expenditure).getId();
    }

    @Transactional
    public StatisticsResponseDto getStatistics(StatisticsRequestDto statisticsRequestDto) {

        List<ExpenditureCategoryDto> expenditureCategoryDtoList = new ArrayList<>();

        Long userId = userService.currentUser();

        //기간 내 카테고리별 소비 내역(expense, categoryId, customCategoryId, isCustom)을 모두 불러온다
        List<Expenditure> expenditureList = expenditureRepository.findAllByDateBetweenAndUserId(statisticsRequestDto.getStartDate(), statisticsRequestDto.getEndDate(), userId);

        //카테고리별 총액을 계산한다
        HashMap<CategoryType, Long> expense_per_category = new HashMap<>();

        for (Expenditure exp : expenditureList) {
            CategoryType categoryType = new CategoryType(exp.getCategoryId(), exp.isCustom());
            if (expense_per_category.containsKey(categoryType)) {
                Long expense = expense_per_category.get(categoryType);
                expense += exp.getExpense();
                expense_per_category.put(categoryType, expense);
            }
            else {
                expense_per_category.put(categoryType, exp.getExpense());
            }
        }

        //카테고리별 비율을 계산하고 리스트를 생성한다
        Long total_expense = 0L;
        for (Map.Entry<CategoryType, Long> entry : expense_per_category.entrySet()) {
            total_expense += entry.getValue();
        }

        Long max_expense = 0L;
        for (Map.Entry<CategoryType, Long> entry : expense_per_category.entrySet()) {
            String categoryName = "";
            if (!entry.getKey().isCustom()) {
                categoryName = categoryRepository.getById(entry.getKey().getCategoryId()).getName();
            }
            else {
                categoryName = customCategoryRepository.getById(entry.getKey().getCategoryId()).getName();
            }
            max_expense = Math.max(max_expense, entry.getValue());
            expenditureCategoryDtoList.add(new ExpenditureCategoryDto(entry.getKey(), categoryName, entry.getValue(), entry.getValue()/total_expense * 100));
        }

        Comparator<ExpenditureCategoryDto> comparator = new Comparator<ExpenditureCategoryDto>() {
            @Override
            public int compare(ExpenditureCategoryDto o1, ExpenditureCategoryDto o2) {
                return (int) (o1.getExpense() - o2.getExpense());
            }
        };

        Collections.sort(expenditureCategoryDtoList, comparator);
        return new StatisticsResponseDto(expenditureCategoryDtoList);
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
