package com.quizgame.quizgame.repository;

import com.quizgame.quizgame.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    boolean existsById(Long categoryId);
}
