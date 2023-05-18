package com.example.quizgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.quizgame.request.RequestBuilder;
import com.example.quizgame.util.QuestionModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private final Integer QUESTIONS_LIMIT = 7;
    private TextView questions;
    private TextView question;
    private AppCompatButton option1, option2, option3, option4;
    private AppCompatButton nextBtn;
    private List<QuestionModel> questionModel = new ArrayList<>();
    private int currentQuestionPosition = 0;

    private String selectedOptionByUser = "";

    private RequestQueue requestQueue;

    private RequestBuilder requestBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        final ImageView backBtn = findViewById(R.id.backBtn);
        final TextView selectedTopicName = findViewById(R.id.topicName);

        requestBuilder = new RequestBuilder();
        requestQueue  = Volley.newRequestQueue(this);

        questions = findViewById(R.id.questions);
        question = findViewById(R.id.question);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        nextBtn = findViewById(R.id.nextBtn);


        final String getSelectedTopicName = getIntent().getStringExtra("selectedTopic");

        selectedTopicName.setText(getSelectedTopicName);

        getQuestionViaAPI(getSelectedTopicName);


        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedOptionByUser.isEmpty()) {
                    selectedOptionByUser = option1.getText().toString();

                    option1.setBackgroundResource(R.drawable.wrong_option);
                    option1.setTextColor(Color.WHITE);
                    revealAnswer();
                    questionModel.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);

                }

            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedOptionByUser.isEmpty()) {
                    selectedOptionByUser = option2.getText().toString();

                    option2.setBackgroundResource(R.drawable.wrong_option);
                    option2.setTextColor(Color.WHITE);
                    revealAnswer();

                    questionModel.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);

                }

            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedOptionByUser.isEmpty()) {
                    selectedOptionByUser = option3.getText().toString();

                    option3.setBackgroundResource(R.drawable.wrong_option);
                    option3.setTextColor(Color.WHITE);
                    revealAnswer();


                    questionModel.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);

                }

            }
        });

        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedOptionByUser.isEmpty()) {
                    selectedOptionByUser = option4.getText().toString();

                    option4.setBackgroundResource(R.drawable.wrong_option);
                    option4.setTextColor(Color.WHITE);

                    revealAnswer();

                    questionModel.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);

                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedOptionByUser.isEmpty()) {
                    Toast.makeText(QuizActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                } else {
                    changeNextQuestion();
                }

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void getQuestionViaAPI(String getSelectedTopicName) {
        Long categoryId = getCategoryIdByName(getSelectedTopicName);
        String url = requestBuilder.getAllQuestionByCategoryIdWithLimit(categoryId, QUESTIONS_LIMIT);

        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                System.out.println(
//                        response
//                );

//                Toast.makeText(QuizActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        QuestionModel questionJSON = new QuestionModel(
                                jsonObject.getLong("id"),
                                jsonObject.getString("question"),
                                jsonObject.getString("option1"),
                                jsonObject.getString("option2"),
                                jsonObject.getString("option3"),
                                jsonObject.getString("option4"),
                                jsonObject.getString("answer")
                        );
                        if (i == 0) {
                            questions.setText((currentQuestionPosition + 1) + "/" + response.length());
                            question.setText(questionJSON.getQuestion());
                            option1.setText(questionJSON.getOption1());
                            option2.setText(questionJSON.getOption2());
                            option3.setText(questionJSON.getOption3());
                            option4.setText(questionJSON.getOption4());

                        }
                        questionModel.add(questionJSON);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
        }}, new Response.ErrorListener(){
          @Override
          public void onErrorResponse (VolleyError error){
              Toast.makeText(QuizActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
              System.out.println(error.getMessage());
      }
  });

        requestQueue.add(stringRequest);
    }

    private void changeNextQuestion() {
        currentQuestionPosition++;

        if ((currentQuestionPosition + 1) == questionModel.size()) {
            nextBtn.setText("Submit Quiz");
        }
        if (currentQuestionPosition < questionModel.size()) {
            selectedOptionByUser = "";

            option1.setBackgroundResource(R.drawable.options_background);
            option1.setTextColor(Color.parseColor("#1F6BB8"));

            option2.setBackgroundResource(R.drawable.options_background);
            option2.setTextColor(Color.parseColor("#1F6BB8"));

            option3.setBackgroundResource(R.drawable.options_background);
            option3.setTextColor(Color.parseColor("#1F6BB8"));

            option4.setBackgroundResource(R.drawable.options_background);
            option4.setTextColor(Color.parseColor("#1F6BB8"));

            questions.setText((currentQuestionPosition + 1) + "/" + questionModel.size());
            question.setText(questionModel.get(currentQuestionPosition).getQuestion());
            option1.setText(questionModel.get(currentQuestionPosition).getOption1());
            option2.setText(questionModel.get(currentQuestionPosition).getOption2());
            option3.setText(questionModel.get(currentQuestionPosition).getOption3());
            option4.setText(questionModel.get(currentQuestionPosition).getOption4());

        } else {
            Intent intent = new Intent(QuizActivity.this, QuizResults.class);
            intent.putExtra("correct", getCorrectAnswers());
            intent.putExtra("incorrect", getInCorrectAnswers());
            startActivity(intent);

            finish();

        }
    }


    private int getCorrectAnswers() {

        int correctAnswers = 0;

        for (int i = 0; i < questionModel.size(); i++) {
            final String getUserSelectedAnswer = questionModel.get(i).getUserSelectedAnswer();
            final String getAnswer = questionModel.get(i).getAnswer();

            if (getUserSelectedAnswer.equals(getAnswer)) {
                correctAnswers++;
            }
        }

        return correctAnswers;
    }

    private int getInCorrectAnswers() {

        int inCorrectAnswers = 0;

        for (int i = 0; i < questionModel.size(); i++) {
            final String getUserSelectedAnswer = questionModel.get(i).getUserSelectedAnswer();
            final String getAnswer = questionModel.get(i).getAnswer();

            if (!getUserSelectedAnswer.equals(getAnswer)) {
                inCorrectAnswers++;
            }
        }

        return inCorrectAnswers;
    }

    @Override
    public void onBackPressed() {


        startActivity(new Intent(QuizActivity.this, MainActivity.class));
        finish();

    }


    private void revealAnswer() {
        final String getAnswer = questionModel.get(currentQuestionPosition).getAnswer();
        if (option1.getText().toString().equals(getAnswer)) {
            option1.setBackgroundResource(R.drawable.correct_option);
            option1.setTextColor(Color.WHITE);


        } else if (option2.getText().toString().equals(getAnswer)) {
            option2.setBackgroundResource(R.drawable.correct_option);
            option2.setTextColor(Color.WHITE);

        } else if (option3.getText().toString().equals(getAnswer)) {
            option3.setBackgroundResource(R.drawable.correct_option);
            option3.setTextColor(Color.WHITE);

        } else if (option4.getText().toString().equals(getAnswer)) {
            option4.setBackgroundResource(R.drawable.correct_option);
            option4.setTextColor(Color.WHITE);

        }
    }

    private static Long getCategoryIdByName(String category) {
        Long id = null;
        switch (category) {
            case "java":
                id = 1L;
                break;
            case "python":
                id = 3L;
                break;
            case "php":
                id = 2L;
                break;
            case "js":
                id = 4L;
                break;
        }

        return id;
    }
}