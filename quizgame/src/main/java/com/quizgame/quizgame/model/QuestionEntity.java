package com.quizgame.quizgame.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "questions")
public class QuestionEntity extends BaseEntity {

    @Column(nullable = false)
    private String question;
    @Column(nullable = false)
    private String option1;
    @Column(nullable = false)
    private String option2;
    @Column(nullable = false)
    private String option3;
    @Column(nullable = false)
    private String option4;
    @Column(nullable = false)
    private String answer;
    @ManyToOne(optional = false)
    private CategoryEntity category;

    public QuestionEntity() {
    }

    public String getQuestion() {
        return question;
    }

    public QuestionEntity setQuestion(String question) {
        this.question = question;
        return this;
    }

    public String getOption1() {
        return option1;
    }

    public QuestionEntity setOption1(String option1) {
        this.option1 = option1;
        return this;
    }

    public String getOption2() {
        return option2;
    }

    public QuestionEntity setOption2(String option2) {
        this.option2 = option2;
        return this;
    }

    public String getOption3() {
        return option3;
    }

    public QuestionEntity setOption3(String option3) {
        this.option3 = option3;
        return this;
    }

    public String getOption4() {
        return option4;
    }

    public QuestionEntity setOption4(String option4) {
        this.option4 = option4;
        return this;
    }

    public String getAnswer() {
        return answer;
    }

    public QuestionEntity setAnswer(String answer) {
        this.answer = answer;
        return this;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public QuestionEntity setCategory(CategoryEntity category) {
        this.category = category;
        return this;
    }
}
