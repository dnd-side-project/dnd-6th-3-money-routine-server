package com.example.dnd6th3moneyroutineserver.customCategory;

import com.example.dnd6th3moneyroutineserver.customCategory.CustomCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomCategoryRepository extends JpaRepository<CustomCategory, Long> {

    @Query("select c from CustomCategory c join c.user u where u.id =:userId")
    List<CustomCategory> findByUserId(Long userId);
}
