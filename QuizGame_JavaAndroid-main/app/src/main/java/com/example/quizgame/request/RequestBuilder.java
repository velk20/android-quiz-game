package com.example.quizgame.request;

public class RequestBuilder implements IRequest{
    @Override
    public String getQuestionById(Long questionId) {
        return String.format(RequestConstants.GET_QUESTION_BY_ID, questionId);
    }

    @Override
    public String getAllQuestionsByCategoryId(Long categoryId) {
        return String.format(RequestConstants.GET_ALL_QUESTIONS_BY_CATEGORY_ID, categoryId);
    }

    @Override
    public String getAllQuestionByCategoryIdWithLimit(Long categoryId, Integer limit) {
        return String.format(RequestConstants.GET_ALL_QUESTIONS_BY_CATEGORY_ID_WITH_LIMIT, categoryId, limit);
    }

    @Override
    public String createQuestion() {
        return String.format(RequestConstants.CREATE_QUESTION);
    }

    @Override
    public String updateQuestion(Long questionId) {
        return String.format(RequestConstants.UPDATE_QUESTION, questionId);
    }

    @Override
    public String deleteQuestion(Long questionId) {
        return String.format(RequestConstants.DELETE_QUESTION, questionId);
    }
}
