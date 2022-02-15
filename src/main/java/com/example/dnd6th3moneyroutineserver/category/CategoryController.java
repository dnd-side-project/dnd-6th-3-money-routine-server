package com.example.dnd6th3moneyroutineserver.category;

import com.example.dnd6th3moneyroutineserver.category.dto.CategoryDto;
import com.example.dnd6th3moneyroutineserver.common.CustomResponse;
import com.example.dnd6th3moneyroutineserver.common.ResponseMessage;
import com.example.dnd6th3moneyroutineserver.common.StatusCode;
import com.example.dnd6th3moneyroutineserver.customCategory.CustomCategory;
import com.example.dnd6th3moneyroutineserver.customCategory.CustomCategoryRepository;
import com.example.dnd6th3moneyroutineserver.user.repository.UserRepository;
import com.example.dnd6th3moneyroutineserver.user.service.UserService;
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
    private final UserService userService;
    private final CategoryService categoryService;

    /**
     * Category + Custom Category 목록 반환
     */
    @GetMapping
    @ApiOperation(value = "유저 카테고리 리스트", notes = "유저의 카테고리 리스트를 반환합니다.")
    public ResponseEntity getCategoryList() {
        Long userId = userService.currentUser();
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

    /**
     * 지출 분야 추가 버튼 선택 시
     * @return
     */
    @GetMapping("/except-list")
    @ApiOperation(value = "추천 분야 리스트", notes = "지출 분야 추가 화면의 사용자가 진행중이지 않은 기본 추천 분야를 반환")
    public ResponseEntity getExceptCategoryList() {
        return new ResponseEntity(
                CustomResponse.response(StatusCode.OK, ResponseMessage.EXCEPT_CATEGORY_LIST_SUCCESS, categoryService.getExceptCategory())
                , HttpStatus.OK);
    }
}
