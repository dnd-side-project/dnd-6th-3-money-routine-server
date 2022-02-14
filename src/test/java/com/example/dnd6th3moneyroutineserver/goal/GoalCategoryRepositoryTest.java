package com.example.dnd6th3moneyroutineserver.goal;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"spring.config.location=classpath:application-dev.properties"})
@Transactional
class GoalCategoryRepositoryTest {


    @Autowired GoalCategoryRepository goalCategoryRepository;

    @Test
    public void findByIdTest() throws Exception {
        // given
        GoalCategory goalCategory = goalCategoryRepository.findGoalAndUserById(81L);
        // when
        System.out.println(goalCategory.getGoal().getUser().getId());
        // then
        Assertions.assertThat(goalCategory.getGoal().getUser().getId()).isEqualTo(45L);
    }
}