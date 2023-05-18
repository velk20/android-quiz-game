package com.quizgame.quizgame.exceptions;

public class QuestionNotFoundException extends RuntimeException {
    private final String message;

    public QuestionNotFoundException() {
        this.message = "Question was not found!";
    }

    public QuestionNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}