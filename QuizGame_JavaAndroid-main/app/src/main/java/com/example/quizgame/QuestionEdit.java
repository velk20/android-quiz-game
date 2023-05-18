package com.example.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.quizgame.request.RequestConstants;
import com.example.quizgame.util.QuestionModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class QuestionEdit extends AppCompatActivity {
    private Button updateBtn, deleteBtn;
    private String topic;
    private RadioGroup radioGroup;
    private ImageView backBtn;
    private QuestionModel questionModel;
    private EditText question,option1,option2,option3, option4;
    private RequestBuilder requestBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_edit);
        backBtn = findViewById(R.id.backManageBtn);

        requestBuilder = new RequestBuilder();
        topic = getIntent().getStringExtra("selectedTopic");
        questionModel = getIntent().getParcelableExtra("question");

        updateBtn = (Button) findViewById(R.id.updateBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        question = (EditText) findViewById(R.id.question);
        option1 = (EditText) findViewById(R.id.option1);
        option2 = (EditText) findViewById(R.id.option2);
        option3 = (EditText) findViewById(R.id.option3);
        option4 = (EditText) findViewById(R.id.option4);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        radioGroup.check(selectCorrectOption(questionModel.getAnswer()));

        question.setText(questionModel.getQuestion());
        option1.setText(questionModel.getOption1());
        option2.setText(questionModel.getOption2());
        option3.setText(questionModel.getOption3());
        option4.setText(questionModel.getOption4());

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuestionEdit.this, QuestionManagePanel.class);
                intent.putExtra("selectedTopic",topic);
                startActivity(intent);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (
                        question.getText().toString().trim().isEmpty() ||
                                option1.getText().toString().trim().isEmpty() ||
                                option2.getText().toString().trim().isEmpty() ||
                                option3.getText().toString().trim().isEmpty() ||
                                option4.getText().toString().trim().isEmpty() ||
                                radioGroup.getCheckedRadioButtonId() == -1
                ) {
                    Toast.makeText(QuestionEdit.this, "All fields must be filled before adding new question", Toast.LENGTH_SHORT).show();
                    return;
                }

                QuestionModel newQuestionModel = new QuestionModel(
                        questionModel.getId(),
                        question.getText().toString().trim(),
                        option1.getText().toString().trim(),
                        option2.getText().toString().trim(),
                        option3.getText().toString().trim(),
                        option4.getText().toString().trim(),
                        getRadioButtonOptionAnswer(radioGroup.getCheckedRadioButtonId()),
                        getCategoryIdByName(topic)
                );

                try {
                    updateQuestion(newQuestionModel);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuestionEdit.this);
                builder.setCancelable(true);
                builder.setTitle("Deleting question?");
                builder.setMessage("Are you sure you want to delete this question?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteQuestion(questionModel.getId());
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void deleteQuestion(Long id) {
        String url = requestBuilder.deleteQuestion(id);

        RequestQueue queue = Volley.newRequestQueue(QuestionEdit.this);

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);
                Toast.makeText(QuestionEdit.this, "Question was successfully deleted.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(QuestionEdit.this, QuestionManagePanel.class);
                intent.putExtra("selectedTopic",topic);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
                Toast.makeText(QuestionEdit.this, "Question was NOT created, something went wrong!", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
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
    private void updateQuestion(QuestionModel questionModel) throws JSONException {
        String url = RequestConstants.REST_API_URL + "/question/" + questionModel.getId();

        RequestQueue queue = Volley.newRequestQueue(QuestionEdit.this);

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("id", questionModel.getId());
        jsonBody.put("question", questionModel.getQuestion());
        jsonBody.put("option1", questionModel.getOption1());
        jsonBody.put("option2", questionModel.getOption2());
        jsonBody.put("option3", questionModel.getOption3());
        jsonBody.put("option4", questionModel.getOption4());
        jsonBody.put("answer", questionModel.getAnswer());
        jsonBody.put("categoryId", questionModel.getCategoryId());

        final String requestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);
                Toast.makeText(QuestionEdit.this, "Question was successfully updated.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(QuestionEdit.this, QuestionManagePanel.class);
                intent.putExtra("selectedTopic",topic);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
                Toast.makeText(QuestionEdit.this, "Question was NOT created, something went wrong!", Toast.LENGTH_SHORT).show();

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
    private int selectCorrectOption(String answer) {
        if (questionModel.getOption1().equals(answer)) {
            return R.id.option1RadioButton;
        } else if (questionModel.getOption2().equals(answer)) {
            return R.id.option2RadioButton;
        }else if (questionModel.getOption3().equals(answer)) {
            return R.id.option3RadioButton;
        }else{
            return R.id.option4RadioButton;
        }
    }
}