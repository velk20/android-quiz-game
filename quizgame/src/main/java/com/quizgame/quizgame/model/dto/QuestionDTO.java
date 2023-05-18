package com.quizgame.quizgame.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class QuestionDTO {
    @NotEmpty(message = "Question can not be empty.")
    private String question;
    @NotEmpty(message = "Option 1 can not be empty.")
    private String option1;

    @NotEmpty(message = "Option 1 can not be empty.")
    private String option2;

    @NotEmpty(message = "Option 1 can not be empty.")
    private String option3;

    @NotEmpty(message = "Option 1 can not be empty.")
    private String option4;

    @NotEmpty(message = "Answer can not be empty.")
    private String answer;

    @Positive
    private Long categoryId;

    public String getQuestion() {
        return question;
    }

    public QuestionDTO setQuestion(String question) {
        this.question = question;
        return this;
    }

    public String getOption1() {
        return option1;
    }

    public QuestionDTO setOption1(String option1) {
        this.option1 = option1;
        return this;
    }

    public String getOption2() {
        return option2;
    }

    public QuestionDTO setOption2(String option2) {
        this.option2 = option2;
        return this;
    }

    public String getOption3() {
        return option3;
    }

    public QuestionDTO setOption3(String option3) {
        this.option3 = option3;
        return this;
    }

    public String getOption4() {
        return option4;
    }

    public QuestionDTO setOption4(String option4) {
        this.option4 = option4;
        return this;
    }

    public String getAnswer() {
        return answer;
    }

    public QuestionDTO setAnswer(String answer) {
        this.answer = answer;
        return this;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public QuestionDTO setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }
}
