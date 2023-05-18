package com.example.quizgame.request;

public abstract class RequestConstants {
    public static String REST_API_LOCALHOST_IP = "10.1.5.111";
    public static String REST_API_URL = "http://" + REST_API_LOCALHOST_IP + ":8080/api";

    public static String GET_QUESTION_BY_ID = REST_API_URL + "/question/%d";
    public static String GET_ALL_QUESTIONS_BY_CATEGORY_ID = REST_API_URL + "/questions/%d";
    public static String GET_ALL_QUESTIONS_BY_CATEGORY_ID_WITH_LIMIT = REST_API_URL + "/questions/%d/%d";
    public static String CREATE_QUESTION = REST_API_URL + "/questions";
    public static String UPDATE_QUESTION = REST_API_URL + "/questions/%d";
    public static String DELETE_QUESTION = REST_API_URL + "/question/%d";

}
