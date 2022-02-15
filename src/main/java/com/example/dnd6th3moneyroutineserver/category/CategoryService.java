package com.example.dnd6th3moneyroutineserver.category;

import com.example.dnd6th3moneyroutineserver.category.dto.ExceptCategoryDto;
import com.example.dnd6th3moneyroutineserver.customCategory.CustomCategory;
import com.example.dnd6th3moneyroutineserver.customCategory.CustomCategoryRepository;
import com.example.dnd6th3moneyroutineserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CustomCategoryRepository customCategoryRepository;
    private final UserService userService;

    // Too much query -> need opt
    // 현재는 날짜를 기준으로 쿼리 -> goal 로 가져올 필요 있음
    @Transactional
    public List<ExceptCategoryDto> getExceptCategory(Long goalId) {
        Long userId = userService.currentUser();
        List<Category> categoryList = categoryRepository.findAll();
        List<CustomCategory> customCategoryList = customCategoryRepository.findByUserId(userId);
        Set<Long> userCategories = categoryRepository.findIdByUserIdAndStartDate(userId, LocalDate.now().withDayOfMonth(1));
        Set<Long> userCustomCategories = customCategoryRepository.findIdByUserIdAndStartDate(userId, LocalDate.now().withDayOfMonth(1));
        List<ExceptCategoryDto> categoryDtoList = new ArrayList<>();

        for (Category category : categoryList) {
            if (!userCategories.contains(category.getId())) {
                categoryDtoList.add(
                        ExceptCategoryDto.builder()
                                .categoryId(category.getId())
                                .detail(category.getDetail())
                                .name(category.getName())
                                .isCustom(false)
                                .build()
                );
            }
        }

        for (CustomCategory customCategory : customCategoryList) {
            if (!userCustomCategories.contains(customCategory.getId())) {
                categoryDtoList.add(
                        ExceptCategoryDto.builder()
                                .categoryId(customCategory.getId())
                                .name(customCategory.getName())
                                .detail(customCategory.getDetail())
                                .isCustom(true)
                                .emoji(customCategory.getEmoji())
                                .build()
                );
            }
        }

        return categoryDtoList;
    }
}
