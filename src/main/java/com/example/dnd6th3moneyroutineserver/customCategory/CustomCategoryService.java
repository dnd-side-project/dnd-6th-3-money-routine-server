package com.example.dnd6th3moneyroutineserver.customCategory;

import com.example.dnd6th3moneyroutineserver.customCategory.dto.CustomCategoryCreateDto;
import com.example.dnd6th3moneyroutineserver.user.UserRepository;
import com.example.dnd6th3moneyroutineserver.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomCategoryService {

    private final CustomCategoryRepository customCategoryRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Transactional
    public Long add(CustomCategoryCreateDto customCategoryCreateDto) {
        CustomCategory save = customCategoryRepository.save(CustomCategory.builder()
                .name(customCategoryCreateDto.getName())
                .detail(customCategoryCreateDto.getDetail())
                .user(userRepository.findById(userService.currentUser()).orElseThrow())
                .build());
        return save.getId();
    }
}
