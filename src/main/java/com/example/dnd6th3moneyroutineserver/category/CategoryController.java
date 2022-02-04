package com.example.dnd6th3moneyroutineserver.category;

import com.example.dnd6th3moneyroutineserver.category.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    /**
     * Category + Custom Category 목록 반환
     */
    @GetMapping("/{userId}")
    public List<CategoryDto> getCategoryList(@RequestParam Long userId) {
        return null;
    }


}
