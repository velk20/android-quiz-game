package com.quizgame.quizgame.repository;

import com.quizgame.quizgame.model.CategoryEntity;
import com.quizgame.quizgame.model.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    List<QuestionEntity> findAllByCategory(CategoryEntity category);
    List<QuestionEntity> findAllByCategoryId(Long categoryId);
    List<QuestionEntity> findAllByCategoryIdOrderByIdDesc(Long categoryId);
}
