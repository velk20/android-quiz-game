package com.example.quizgame.request;

public interface IRequest {
    String getQuestionById(Long questionId);
    String getAllQuestionsByCategoryId(Long categoryId);
    String getAllQuestionByCategoryIdWithLimit(Long categoryId, Integer limit);
    String createQuestion();
    String updateQuestion(Long questionId);
    String deleteQuestion(Long questionId);
}
