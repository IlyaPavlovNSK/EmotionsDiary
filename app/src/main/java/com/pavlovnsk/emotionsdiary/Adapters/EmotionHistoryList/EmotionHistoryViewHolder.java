package com.pavlovnsk.emotionsdiary.Adapters.EmotionHistoryList;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pavlovnsk.emotionsdiary.R;

public class EmotionHistoryViewHolder extends RecyclerView.ViewHolder implements EmotionHistoryRowView {

    private TextView emotionName;
    private TextView emotionDescription;
    private TextView emotionDate;
    private TextView emotionTime;

    public EmotionHistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        emotionDate = itemView.findViewById(R.id.emotion_date_history);
        emotionTime = itemView.findViewById(R.id.emotion_time_history);
        emotionDescription = itemView.findViewById(R.id.emotion_description_history);
        emotionName = itemView.findViewById(R.id.emotion_name_history);
    }

    @Override
    public void setEmotionData(String data) {
        emotionDate.setText(data);
    }

    @Override
    public void setEmotionTime(String time) {
        emotionTime.setText(time);
    }

    @Override
    public void setEmotionName(String name) {
        emotionName.setText(name);
    }

    @Override
    public void setDescription(String description) {
        emotionDescription.setText(description);
    }
}
