package com.example.dnd6th3moneyroutineserver.expenditure.dto;

import com.example.dnd6th3moneyroutineserver.expenditure.entity.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoalCategoryInfoDto {
    private CategoryType categoryType;
    private String categoryName;
    private int percentage;
    private Long expense;
    private List<WeeklyExpenditureDetailDto> weeklyExpenditureDetailDtoList;
}

//C.분야별 정보(WeeklyGoalCategoryInfoDto)
//C-0.분야 타입(categoryId, isCustom)
//C-a.분야 이름
//C-b.분야 비율
//C-c.분야 금액
//C-d.분야 지출 내역(WeeklyExpenditureDetailDto)
//C-d-1.지출 날짜
//C-d-2.지출 상세
//C-d-3.지출 금액