package com.example.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quizgame.request.RequestBuilder;
import com.example.quizgame.util.QuestionModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class QuestionAdd extends AppCompatActivity {
    private Button addBtn;
    private RadioGroup radioGroup;
    private EditText question, option1, option2, option3, option4;
    private ImageView backBtn;
    String getSelectedTopicName;
    private RequestBuilder requestBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_add);

        requestBuilder = new RequestBuilder();
        question = findViewById(R.id.question);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        radioGroup = findViewById(R.id.radioGroup);

        getSelectedTopicName = getIntent().getStringExtra("selectedTopic");
        backBtn = findViewById(R.id.backManageBtn);
        addBtn = (Button) findViewById(R.id.addButton);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuestionAdd.this, QuestionManagePanel.class);
                intent.putExtra("selectedTopic", getSelectedTopicName);
                startActivity(intent);
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (
                                question.getText().toString().trim().isEmpty() ||
                                option1.getText().toString().trim().isEmpty() ||
                                option2.getText().toString().trim().isEmpty() ||
                                option3.getText().toString().trim().isEmpty() ||
                                option4.getText().toString().trim().isEmpty() ||
                                        radioGroup.getCheckedRadioButtonId() == -1
                ) {
                    Toast.makeText(QuestionAdd.this, "All fields must be filled before adding new question", Toast.LENGTH_SHORT).show();
                    return;
                }

                QuestionModel questionModel = new QuestionModel(
                        question.getText().toString().trim(),
                        option1.getText().toString().trim(),
                        option2.getText().toString().trim(),
                        option3.getText().toString().trim(),
                        option4.getText().toString().trim(),
                        getRadioButtonOptionAnswer(radioGroup.getCheckedRadioButtonId()),
                        getCategoryIdByName(getSelectedTopicName)
                );

                try {
                    createQuestion(questionModel);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

    private void createQuestion(QuestionModel questionModel) throws JSONException {
        String url = requestBuilder.createQuestion();

        RequestQueue queue = Volley.newRequestQueue(QuestionAdd.this);

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("question", questionModel.getQuestion());
        jsonBody.put("option1", questionModel.getOption1());
        jsonBody.put("option2", questionModel.getOption2());
        jsonBody.put("option3", questionModel.getOption3());
        jsonBody.put("option4", questionModel.getOption4());
        jsonBody.put("answer", questionModel.getAnswer());
        jsonBody.put("categoryId", questionModel.getCategoryId());

        final String requestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);
                Toast.makeText(QuestionAdd.this, "Question was successfully created.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(QuestionAdd.this, QuestionManagePanel.class);
                intent.putExtra("selectedTopic",getSelectedTopicName);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
                Toast.makeText(QuestionAdd.this, "Question was NOT created, something went wrong!", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        queue.add(stringRequest);
    }

    private String getRadioButtonOptionAnswer(int checkedRadioButtonId) {
        switch (checkedRadioButtonId) {
            case R.id.option1RadioButton:
                return option1.getText().toString();
            case R.id.option2RadioButton:
                return option2.getText().toString();
            case R.id.option3RadioButton:
                return option3.getText().toString();
            case R.id.option4RadioButton:
                return option4.getText().toString();
        }
        return null;
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