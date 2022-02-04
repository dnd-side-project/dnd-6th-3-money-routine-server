package com.example.dnd6th3moneyroutineserver.customCategory;

import com.example.dnd6th3moneyroutineserver.customCategory.dto.CustomCategoryCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/custom-category")
@RequiredArgsConstructor
public class CustomCategoryController {

    /**
     * Create new custom category
     */
    @PostMapping
    public Long create(@RequestBody CustomCategoryCreateDto customCategoryCreateDto) {
        return 1L;
    }
}
