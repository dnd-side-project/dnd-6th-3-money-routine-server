package com.example.dnd6th3moneyroutineserver.customCategory;

import com.example.dnd6th3moneyroutineserver.customCategory.dto.CustomCategoryCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Long create(@RequestBody CustomCategoryCreateDto customCategoryCreateDto) {
        return customCategoryService.add(customCategoryCreateDto);
    }
}
