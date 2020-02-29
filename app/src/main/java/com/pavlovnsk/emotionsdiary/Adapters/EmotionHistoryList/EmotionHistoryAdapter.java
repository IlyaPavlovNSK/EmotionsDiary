package com.pavlovnsk.emotionsdiary.Adapters.EmotionHistoryList;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pavlovnsk.emotionsdiary.R;

import javax.inject.Inject;

public class EmotionHistoryAdapter extends RecyclerView.Adapter<EmotionHistoryViewHolder> {

    private EmotionHistoryPresenter emotionHistoryPresenter;

    @Inject
    public EmotionHistoryAdapter(EmotionHistoryPresenter emotionHistoryPresenter) {
        this.emotionHistoryPresenter = emotionHistoryPresenter;
    }

    @NonNull
    @Override
    public EmotionHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EmotionHistoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.emotion_history_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EmotionHistoryViewHolder holder, int position) {
        emotionHistoryPresenter.onBindEmotionsRowViewAtPosition(position, holder);
    }

    @Override
    public int getItemCount() {
        return emotionHistoryPresenter.getEmotionsRowsCount();
    }
}
