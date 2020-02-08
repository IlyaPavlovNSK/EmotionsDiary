package com.pavlovnsk.emotionsdiary.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;
import com.pavlovnsk.emotionsdiary.R;

import java.util.ArrayList;

public class EmotionsAdapter extends RecyclerView.Adapter<EmotionsAdapter.EmotionViewHolder> {

    private ArrayList<EmotionItem> emotions;

    public EmotionsAdapter(ArrayList<EmotionItem> emotions) {
        this.emotions = emotions;
    }

    @NonNull
    @Override
    public EmotionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emotion_item, parent, false);
        return new EmotionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmotionViewHolder holder, int position) {
        EmotionItem emotionItem = emotions.get(position);

        holder.emotionLevel.setText(emotionItem.getEmotionLevel());
        holder.emotionName.setText(emotionItem.getEmotionName());
        holder.emotionPic.setImageResource(emotionItem.getEmotionPic());
    }

    @Override
    public int getItemCount() {
        return emotions.size();
    }

    static class EmotionViewHolder extends RecyclerView.ViewHolder {

        private TextView emotionLevel;
        private TextView emotionName;
        private ImageView emotionPic;

        public EmotionViewHolder(@NonNull View itemView) {
            super(itemView);
            emotionLevel = itemView.findViewById(R.id.emotion_level);
            emotionName = itemView.findViewById(R.id.emotion_name);
            emotionPic = itemView.findViewById(R.id.emotion_pic);
        }
    }
}
