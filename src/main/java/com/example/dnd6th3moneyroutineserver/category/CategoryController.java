package com.example.dnd6th3moneyroutineserver.category;

import com.example.dnd6th3moneyroutineserver.category.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    /**
     * Category + Custom Category 목록 반환
     */
    @GetMapping("/{userId}")
    public List<CategoryDto> getCategoryList(@PathVariable Long userId) {
        return null;
    }


}
