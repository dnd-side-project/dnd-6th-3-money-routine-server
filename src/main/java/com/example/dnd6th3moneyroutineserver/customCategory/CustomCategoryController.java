package com.example.dnd6th3moneyroutineserver.customCategory;

import com.example.dnd6th3moneyroutineserver.common.CustomResponse;
import com.example.dnd6th3moneyroutineserver.common.ResponseMessage;
import com.example.dnd6th3moneyroutineserver.common.StatusCode;
import com.example.dnd6th3moneyroutineserver.customCategory.dto.CustomCategoryCreateDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/custom-category")
@RequiredArgsConstructor
@Slf4j
public class CustomCategoryController {

    private final CustomCategoryService customCategoryService;

    /**
     * Create new custom category
     */
    @PostMapping
    @ApiOperation(value = "사용자 카테고리 생성", notes = "사용자가 만들고싶은 추가 카테고리 생성")
    public ResponseEntity create(@RequestBody CustomCategoryCreateDto customCategoryCreateDto) {
        return new ResponseEntity(CustomResponse
                .response(StatusCode.OK, ResponseMessage.CREATE_CUSTOM_CATEGORY_SUCCESS, customCategoryService.add(customCategoryCreateDto)), HttpStatus.OK);
    }
}
