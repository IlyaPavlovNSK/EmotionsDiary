package com.pavlovnsk.emotionsdiary.Adapters.EmotionListSmall;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;
import com.pavlovnsk.emotionsdiary.R;
import java.util.ArrayList;

import javax.inject.Inject;

public class EmotionsAdapterSmall extends RecyclerView.Adapter<EmotionViewHolderSmall> {

    private EmotionsListPresenterSmall presenterSmall;
    private ArrayList<EmotionItem> emotions;

    @Inject
    public EmotionsAdapterSmall(EmotionsListPresenterSmall emotionsListPresenterSmall) {
        this.presenterSmall = emotionsListPresenterSmall;
        this.emotions = emotionsListPresenterSmall.getEmotions();
    }

    @NonNull
    @Override
    public EmotionViewHolderSmall onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EmotionViewHolderSmall(LayoutInflater.from(parent.getContext()).inflate(R.layout.emotion_item_small, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EmotionViewHolderSmall holder, int position) {
        presenterSmall.onBindEmotionsRowViewAtPosition(position, holder);
    }

    @Override
    public int getItemCount() {
        return presenterSmall.getEmotionsRowsCount();
    }
}
