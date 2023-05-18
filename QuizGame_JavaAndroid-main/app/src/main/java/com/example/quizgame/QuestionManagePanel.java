package com.example.quizgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.example.quizgame.util.QuestionAdapter;
import com.example.quizgame.util.QuestionModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuestionManagePanel extends AppCompatActivity {
    RecyclerView recyclerView;
    RequestQueue requestQueue;
    List<QuestionModel> questionModels;
    ImageView backBtn,addBtn;

    RequestBuilder requestBuilder;
    TextView topicName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_manage_panel);

        String getSelectedTopicName = getIntent().getStringExtra("selectedTopic");

        requestBuilder = new RequestBuilder();
        requestQueue = Volley.newRequestQueue(this);
        questionModels = new ArrayList<>();


        addBtn = findViewById(R.id.addBtn);
        backBtn = findViewById(R.id.backManageBtn);
        topicName = findViewById(R.id.topicName);
        topicName.setText("All " + getSelectedTopicName + " questions");

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getQuestionViaAPI(getSelectedTopicName, getSelectedTopicName);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuestionManagePanel.this, MainActivity.class));
                finish();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuestionManagePanel.this, QuestionAdd.class);
                intent.putExtra("selectedTopic", getSelectedTopicName);
                startActivity(intent);
            }
        });
    }


    private void getQuestionViaAPI(String getSelectedTopicName, String category) {
        Long categoryId = getCategoryIdByName(getSelectedTopicName);
        String url = requestBuilder.getAllQuestionsByCategoryId(categoryId);

        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                System.out.println(
//                        response
//                );
//
//                Toast.makeText(QuestionManagePanel.this, response.toString(), Toast.LENGTH_SHORT).show();
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
                        questionModels.add(questionJSON);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    recyclerView.setAdapter(new QuestionAdapter(getApplicationContext(), questionModels, category, new QuestionAdapter.ItemClickListener() {
                        @Override
                        public void onItemClick(QuestionModel questionModel) {
                            Intent intent = new Intent(QuestionManagePanel.this, QuestionEdit.class);
                            intent.putExtra("selectedTopic",getSelectedTopicName);
                            intent.putExtra("question", questionModel);
                            startActivity(intent);
                        }
                    }));
                }
            }}, new Response.ErrorListener(){
            @Override
            public void onErrorResponse (VolleyError error){
                Toast.makeText(QuestionManagePanel.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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