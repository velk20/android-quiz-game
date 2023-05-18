package com.quizgame.quizgame.service;

import com.quizgame.quizgame.exceptions.CategoryNotFoundException;
import com.quizgame.quizgame.exceptions.QuestionNotFoundException;
import com.quizgame.quizgame.model.CategoryEntity;
import com.quizgame.quizgame.model.QuestionEntity;
import com.quizgame.quizgame.model.dto.QuestionDTO;
import com.quizgame.quizgame.model.dto.QuestionListDTO;
import com.quizgame.quizgame.repository.CategoryRepository;
import com.quizgame.quizgame.repository.QuestionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    public QuestionService(QuestionRepository questionRepository, CategoryRepository categoryRepository, ModelMapper mapper) {
        this.questionRepository = questionRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    public QuestionEntity createQuestion(QuestionDTO questionDTO) {
        if (!this.categoryRepository.existsById(questionDTO.getCategoryId())) {
            throw new CategoryNotFoundException();
        }
        CategoryEntity category = this.categoryRepository
                .findById(questionDTO.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        QuestionEntity questionEntity = mapper.map(questionDTO, QuestionEntity.class);
        questionEntity.setCategory(category);
        questionEntity.setId(null);

        return this.questionRepository.save(questionEntity);
    }

    @Modifying
    public QuestionEntity updateQuestion(Long questionId, QuestionDTO questionDTO) {
        QuestionEntity questionEntity = this.questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);

        CategoryEntity categoryEntity = this.categoryRepository.findById(questionDTO.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        questionEntity.setQuestion(questionDTO.getQuestion());
        questionEntity.setOption1(questionDTO.getOption1());
        questionEntity.setOption2(questionDTO.getOption2());
        questionEntity.setOption3(questionDTO.getOption3());
        questionEntity.setOption4(questionDTO.getOption4());
        questionEntity.setAnswer(questionDTO.getAnswer());
        questionEntity.setCategory(categoryEntity);

        return this.questionRepository.save(questionEntity);
    }

    @Modifying
    public void deleteQuestion(Long questionId) {
        QuestionEntity questionEntity = this.questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);

        this.questionRepository.delete(questionEntity);
    }

    public QuestionListDTO findAllByCategoryId(Long categoryId) {
        CategoryEntity category = this.categoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);

        QuestionListDTO questionListDTO = new QuestionListDTO();

        List<QuestionEntity> allByCategory = this.questionRepository.findAllByCategory(category);
        for (int i = 0; i < allByCategory.size(); i++) {
            QuestionDTO questionDTO = this.mapper.map(allByCategory.get(i), QuestionDTO.class);
            questionListDTO.add(questionDTO);
        }

        return questionListDTO;
    }
}
