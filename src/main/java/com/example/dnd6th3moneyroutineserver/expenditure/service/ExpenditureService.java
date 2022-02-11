package com.example.dnd6th3moneyroutineserver.expenditure;

import com.example.dnd6th3moneyroutineserver.expenditure.dto.ExpenditureWriteDto;
import com.example.dnd6th3moneyroutineserver.expenditure.dto.StatisticsRequestDto;
import com.example.dnd6th3moneyroutineserver.expenditure.dto.StatisticsResponseDto;
import com.example.dnd6th3moneyroutineserver.expenditure.entity.Expenditure;
import com.example.dnd6th3moneyroutineserver.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenditureService {

    private final ExpenditureRepository expenditureRepository;
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
            expenditure.setCategoryId(expenditure.getCategoryId());
        }
        else {
            expenditure.setCustomCategoryId(expenditure.getCategoryId());
        }

        return expenditureRepository.save(expenditure).getId();
    }

    @Transactional
    public StatisticsResponseDto getStatistics(StatisticsRequestDto statisticsRequestDto) {
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
