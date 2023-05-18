package com.example.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private String selectedTopicName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout java = findViewById(R.id.javaLayout);
        final LinearLayout php = findViewById(R.id.phpLayout);
        final LinearLayout python = findViewById(R.id.pythonLayout);
        final LinearLayout js = findViewById(R.id.jsLayout);

        final Button startBtn = findViewById(R.id.startQuizButton);
        final Button manageBtn = findViewById(R.id.manageBtn);

        java.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedTopicName = "java";

                java.setBackgroundResource(R.drawable.selection_background);
                php.setBackgroundResource(R.drawable.initial_selections);
                python.setBackgroundResource(R.drawable.initial_selections);
                js.setBackgroundResource(R.drawable.initial_selections);

            }
        });

        php.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedTopicName = "php";

                php.setBackgroundResource(R.drawable.selection_background);
                java.setBackgroundResource(R.drawable.initial_selections);
                python.setBackgroundResource(R.drawable.initial_selections);
                js.setBackgroundResource(R.drawable.initial_selections);
            }
        });

        python.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedTopicName = "python";

                python.setBackgroundResource(R.drawable.selection_background);
                java.setBackgroundResource(R.drawable.initial_selections);
                php.setBackgroundResource(R.drawable.initial_selections);
                js.setBackgroundResource(R.drawable.initial_selections);
            }
        });

        js.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedTopicName = "js";

                js.setBackgroundResource(R.drawable.selection_background);
                java.setBackgroundResource(R.drawable.initial_selections);
                python.setBackgroundResource(R.drawable.initial_selections);
                php.setBackgroundResource(R.drawable.initial_selections);
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedTopicName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please select the Topic!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                    intent.putExtra("selectedTopic",selectedTopicName);
                    startActivity(intent);
                }
            }
        });

        manageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedTopicName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please select category!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, QuestionManagePanel.class);
                    intent.putExtra("selectedTopic",selectedTopicName);
                    startActivity(intent);
                }
            }
        });

    }


}