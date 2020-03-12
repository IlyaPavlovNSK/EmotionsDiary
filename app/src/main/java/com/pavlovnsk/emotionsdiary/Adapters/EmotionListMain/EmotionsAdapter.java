package com.pavlovnsk.emotionsdiary.Adapters.EmotionListMain;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.pavlovnsk.emotionsdiary.R;

import javax.inject.Inject;

public class EmotionsAdapter extends RecyclerView.Adapter<EmotionViewHolder> {

    private EmotionsListPresenter presenter;

    @Inject
    public EmotionsAdapter(EmotionsListPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public EmotionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EmotionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.emotion_item_large, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EmotionViewHolder holder, int position) {
        presenter.onBindEmotionsRowViewAtPosition(position, holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getEmotionsRowsCount();
    }
}
