package com.example.dnd6th3moneyroutineserver.goal;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"spring.config.location=classpath:application-dev.properties"})
@Transactional
class GoalRepositoryTest {

    @Autowired
    GoalRepository goalRepository;

    @Test
    public void findByStartDateAndUserIdTest() throws Exception {
        // given
        Goal goal = goalRepository.findByStartDateAndUserId(LocalDate.now().withDayOfMonth(1), 45L);
        // when

        // then
        Assertions.assertThat(goal.getUser().getId()).isEqualTo(45L);
    }
}