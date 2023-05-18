package com.example.quizgame.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizgame.R;
import com.example.quizgame.util.QuestionModel;
import com.example.quizgame.util.QuestionViewHolder;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionViewHolder> {
    private Context context;
    private List<QuestionModel> items;
    private Integer category;
    private ItemClickListener itemClickListener;

    public QuestionAdapter(Context context, List<QuestionModel> items, String category, ItemClickListener itemClickListener) {
        this.context = context;
        this.items = items;
        this.category = setCategoryImage(category);
        this.itemClickListener = itemClickListener;
    }

    private Integer setCategoryImage(String category) {
        switch (category) {
            case "java":
                return R.drawable.java_icon;
            case "python":
                return R.drawable.python_icon;
            case "php":
                return R.drawable.php_icon;
            case "js":
                return R.drawable.js_icon;
        }

        return null;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuestionViewHolder(LayoutInflater.from(context).inflate(R.layout.card_view_question, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        holder.textView.setText(items.get(position).getQuestion());
        holder.imageView.setImageResource(category);

        holder.itemView.setOnClickListener(view->{
            itemClickListener.onItemClick(items.get(position));
        });
    }

    public interface ItemClickListener {
        void onItemClick(QuestionModel questionModel);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}
