package com.quizgame.quizgame.service;

import com.quizgame.quizgame.exceptions.CategoryNotFoundException;
import com.quizgame.quizgame.exceptions.QuestionNotFoundException;
import com.quizgame.quizgame.model.QuestionEntity;
import com.quizgame.quizgame.model.dto.QuestionDTO;
import com.quizgame.quizgame.repository.CategoryRepository;
import com.quizgame.quizgame.repository.QuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class RestService {
    private final Random rand = new Random();
    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;
    private final QuestionService questionService;

    public RestService(CategoryRepository categoryRepository, QuestionRepository questionRepository, QuestionService questionService) {
        this.categoryRepository = categoryRepository;
        this.questionRepository = questionRepository;
        this.questionService = questionService;
    }

    @GetMapping("/question/{questionId}")
    public QuestionEntity getQuestionById(@PathVariable("questionId") Long questionId){
        return questionRepository.findById(questionId).orElseThrow(QuestionNotFoundException::new);
    }

    @GetMapping("/questions/{categoryId}")
    public List<QuestionEntity> getAllQuestionsByCategoryId(@PathVariable("categoryId") Long categoryId) {
        return this.questionRepository.findAllByCategoryIdOrderByIdDesc(categoryId);
    }

    @GetMapping("/questions/{categoryId}/{limit}")
    public List<QuestionEntity> getAllQuestionByCategoryIdWithLimit(@PathVariable("categoryId") Long categoryId,
                                                                    @PathVariable("limit") Integer limit) {
        List<QuestionEntity> allByCategoryId = this.questionRepository.findAllByCategoryId(categoryId);
        int limitQuestions = allByCategoryId.size() - limit;
        for (int i = 0; i < limitQuestions; i++) {
            int randomIndex = rand.nextInt(allByCategoryId.size());
            QuestionEntity randomElement = allByCategoryId.get(randomIndex);
            allByCategoryId.remove(randomIndex);
        }
        return allByCategoryId;
    }

    @PostMapping("/questions")
    public QuestionEntity createQuestion(@RequestBody QuestionDTO questionDTO) {
        QuestionEntity question = this.questionService.createQuestion(questionDTO);
        return question;
    }

    @PutMapping("/question/{questionId}")
    public QuestionEntity updateQuestionById(@PathVariable("questionId") Long questionId,
                                                             @RequestBody QuestionDTO questionDTO){
        QuestionEntity updateQuestion = this.questionService.updateQuestion(questionId, questionDTO);
        return updateQuestion;
    }

    @DeleteMapping("/question/{questionId}")
    public void updateQuestionById(@PathVariable("questionId") Long questionId){
        this.questionService.deleteQuestion(questionId);
    }

    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ExceptionHandler({CategoryNotFoundException.class})
    public String onCategoryNotFound(CategoryNotFoundException exception){
        return exception.getMessage();
    }

    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ExceptionHandler({QuestionNotFoundException.class})
    public String onQuestionNotFound(QuestionNotFoundException exception){
        return exception.getMessage();
    }
}
