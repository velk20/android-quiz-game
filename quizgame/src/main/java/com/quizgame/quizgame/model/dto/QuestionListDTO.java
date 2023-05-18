package com.quizgame.quizgame.model.dto;

import java.util.ArrayList;
import java.util.List;

public class QuestionListDTO {
    List<QuestionDTO> questions;

    public QuestionListDTO() {
        this.questions = new ArrayList<>();
    }

    public void add(QuestionDTO questionDTO) {
        this.questions.add(questionDTO);
    }
    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public QuestionListDTO setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
        return this;
    }
}
