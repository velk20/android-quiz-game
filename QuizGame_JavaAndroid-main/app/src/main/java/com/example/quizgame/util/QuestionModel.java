package com.example.quizgame.util;

import android.os.Parcel;
import android.os.Parcelable;

public class QuestionModel implements Parcelable {

    private Long id, categoryId;
    private String question, option1, option2, option3, option4, answer, userSelectedAnswer;

    public QuestionModel(Long id,String question, String option1, String option2, String option3, String option4,String answer, Long categoryId) {
        this.id = id;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
        this.categoryId = categoryId;
    }
    public QuestionModel(String question, String option1, String option2, String option3, String option4,String answer, Long categoryId) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
        this.categoryId = categoryId;
    }

    public QuestionModel(String question, String option1, String option2, String option3, String option4, String answer, String userSelectedAnswer) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
        this.userSelectedAnswer = userSelectedAnswer;
    }

    public QuestionModel(Long id, String question, String option1, String option2, String option3, String option4, String answer) {
        this.id = id;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
        this.userSelectedAnswer = "";
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getQuestion() {
        return question;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setUserSelectedAnswer(String userSelectedAnswer) {
        this.userSelectedAnswer = userSelectedAnswer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getUserSelectedAnswer() {
        return userSelectedAnswer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(question);
        parcel.writeString(option1);
        parcel.writeString(option2);
        parcel.writeString(option3);
        parcel.writeString(option4);
        parcel.writeString(answer);
        parcel.writeString(userSelectedAnswer);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<QuestionModel> CREATOR = new Parcelable.Creator<QuestionModel>() {
        public QuestionModel createFromParcel(Parcel in) {
            return new QuestionModel(in);
        }

        public QuestionModel[] newArray(int size) {
            return new QuestionModel[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private QuestionModel(Parcel in) {
        id = in.readLong();
        question = in.readString();
        option1 = in.readString();
        option2 = in.readString();
        option3 = in.readString();
        option4 = in.readString();
        answer = in.readString();
        userSelectedAnswer = in.readString();

    }

    @Override
    public String toString() {
        return "QuestionModel{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", question='" + question + '\'' +
                ", option1='" + option1 + '\'' +
                ", option2='" + option2 + '\'' +
                ", option3='" + option3 + '\'' +
                ", option4='" + option4 + '\'' +
                ", answer='" + answer + '\'' +
                ", userSelectedAnswer='" + userSelectedAnswer + '\'' +
                '}';
    }
}
