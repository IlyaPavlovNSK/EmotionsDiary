package com.pavlovnsk.emotionsdiary.Adapters.EmotionListSmall;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.pavlovnsk.emotionsdiary.R;

import javax.inject.Inject;

public class EmotionViewHolderSmall extends RecyclerView.ViewHolder implements EmotionRowViewSmall {

    private TextView name;
    private TextView description;

    @Inject
    EmotionViewHolderSmall(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.text_view_emotion_item_name_small);
        description = itemView.findViewById(R.id.text_view_emotion_item_description_small);
    }

    @Override
    public void setEmotionName(String name) {
        this.name.setText(name);
    }

    @Override
    public void setDescription(String description) {
        this.description.setText(description);
    }

    @Override
    public void setEmotionId(int id) {
    }
}
