package com.example.dnd6th3moneyroutineserver.customCategory;

import com.example.dnd6th3moneyroutineserver.customCategory.dto.CustomCategoryCreateDto;
import com.example.dnd6th3moneyroutineserver.user.entity.User;
import com.example.dnd6th3moneyroutineserver.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = {"spring.config.location=classpath:application-dev.properties"})
@Transactional
class CustomCategoryControllerTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomCategoryController customCategoryController;

    @Autowired
    CustomCategoryRepository customCategoryRepository;

    @Test
    public void 생성테스트() throws Exception {
        // given
        User user = User.builder()
                .email("wotj7687@naver.com")
                .password("pwpwpw")
                .image("image")
                .build();
        userRepository.save(user);
        // when
        for (int i = 0; i < 5; i++) {
            customCategoryController.create(CustomCategoryCreateDto.builder()
                    .detail("i")
                    .name("식품")
                    .build()
            );
        }
        // then

        Assertions.assertThat(customCategoryRepository.findAll().size()).isEqualTo(5);
    }
}