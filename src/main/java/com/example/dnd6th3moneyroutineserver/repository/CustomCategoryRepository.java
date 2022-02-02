package com.example.dnd6th3moneyroutineserver.repository;

import com.example.dnd6th3moneyroutineserver.entity.CustomCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomCategoryRepository extends JpaRepository<CustomCategory, Long> {
}
