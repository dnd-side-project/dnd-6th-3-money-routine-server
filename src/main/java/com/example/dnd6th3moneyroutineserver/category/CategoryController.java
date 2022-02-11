package com.example.dnd6th3moneyroutineserver.category;

import com.example.dnd6th3moneyroutineserver.category.dto.CategoryDto;
import com.example.dnd6th3moneyroutineserver.common.CustomResponse;
import com.example.dnd6th3moneyroutineserver.common.ResponseMessage;
import com.example.dnd6th3moneyroutineserver.common.StatusCode;
import com.example.dnd6th3moneyroutineserver.customCategory.CustomCategory;
import com.example.dnd6th3moneyroutineserver.customCategory.CustomCategoryRepository;
import com.example.dnd6th3moneyroutineserver.user.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CustomCategoryRepository customCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    /**
     * Category + Custom Category 목록 반환
     */
    @GetMapping("/{userId}")
    @ApiOperation(value = "유저 카테고리 리스트", notes = "유저의 카테고리 리스트를 반환합니다.")
    public ResponseEntity getCategoryList(@PathVariable Long userId) {
        List<Category> categoryList = categoryRepository.findAll();
        List<CustomCategory> customCategoryList = customCategoryRepository.findByUserId(userId);
        List<CategoryDto> userCategoryList = new ArrayList<>();

        for (Category category : categoryList) {
            userCategoryList.add(CategoryDto.builder()
                    .detail(category.getDetail())
                    .name(category.getName())
                    .isCustom(false)
                    .build());
        }

        for (CustomCategory customCategory : customCategoryList) {
            userCategoryList.add(CategoryDto.builder()
                    .detail(customCategory.getDetail())
                    .name(customCategory.getName())
                    .isCustom(true)
                    .build());
        }

        return new ResponseEntity(CustomResponse
                .response(StatusCode.OK, ResponseMessage.CATEGORY_LIST_SUCCESS, userCategoryList), HttpStatus.OK);
    }


}
