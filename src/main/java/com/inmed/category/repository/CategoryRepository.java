package com.inmed.category.repository;

import com.inmed.category.entity.Category;
import com.inmed.category.entity.CategoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository
        extends JpaRepository<Category, Long> {
    boolean existsByCode(String code);
    Optional<Category> findByCode(String code);
    List<Category> findAllByStatus(CategoryStatus status);
}