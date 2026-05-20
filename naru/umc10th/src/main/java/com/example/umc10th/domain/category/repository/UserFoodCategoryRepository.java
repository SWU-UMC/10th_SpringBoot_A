package com.example.umc10th.domain.category.repository;

import com.example.umc10th.domain.category.entity.mapping.UserFoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFoodCategoryRepository extends JpaRepository<UserFoodCategory, Long> {
}
