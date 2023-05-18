package com.example.quizgame.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizgame.R;

public class QuestionViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView textView;

    public QuestionViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.img1);
        textView = itemView.findViewById(R.id.nametext);

    }
}
