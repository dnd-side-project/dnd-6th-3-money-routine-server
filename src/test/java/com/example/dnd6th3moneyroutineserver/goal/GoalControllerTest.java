package com.example.dnd6th3moneyroutineserver.goal;

import com.example.dnd6th3moneyroutineserver.category.Category;
import com.example.dnd6th3moneyroutineserver.category.CategoryRepository;
import com.example.dnd6th3moneyroutineserver.customCategory.CustomCategory;
import com.example.dnd6th3moneyroutineserver.customCategory.CustomCategoryRepository;
import com.example.dnd6th3moneyroutineserver.goal.dto.GoalCategoryCreateDto;
import com.example.dnd6th3moneyroutineserver.goal.dto.GoalCreateDto;
import com.example.dnd6th3moneyroutineserver.user.entity.User;
import com.example.dnd6th3moneyroutineserver.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(properties = {"spring.config.location=classpath:application-dev.properties"})
@Transactional
class GoalControllerTest {

    @Autowired
    GoalController goalController;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GoalCategoryRepository goalCategoryRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CustomCategoryRepository customCategoryRepository;

    @Autowired
    GoalRepository goalRepository;

    @Test
    public void 목표생성() throws Exception {
        // given
        // 1. 유저 생성
        User user = User.builder()
                .email("wotj7687@naver.com")
                .password("pwpwpw")
                .image("image")
                .build();
        userRepository.save(user);

        // 2. category, customCategory 생성
        Category category = Category.builder()
                .name("넷플릭스")
                .detail("movie")
                .build();
        categoryRepository.save(category);

        CustomCategory customCategory = CustomCategory.builder()
                .user(user)
                .name("커스텀카테고리")
                .detail("custom")
                .build();
        customCategoryRepository.save(customCategory);

        List<GoalCategoryCreateDto> goalCategoryCreateDtoList = new ArrayList<>();

        goalCategoryCreateDtoList.add(GoalCategoryCreateDto.builder()
                .budget(100000)
                .categoryId(category.getId())
                .isCustom(false)
                .build());

        goalCategoryCreateDtoList.add(GoalCategoryCreateDto.builder()
                .budget(100000)
                .categoryId(customCategory.getId())
                .isCustom(true)
                .build());

        // when
        goalController.createGoal(
                GoalCreateDto.builder()
                        .total_budget(1000000)
                        .goalCategoryCreateDtoList(goalCategoryCreateDtoList)
                        .build()
        );

        // then
        Assertions.assertThat(goalCategoryRepository.findAll().size()).isEqualTo(2);
        Assertions.assertThat(goalRepository.findAll().size()).isEqualTo(1);
    }
}