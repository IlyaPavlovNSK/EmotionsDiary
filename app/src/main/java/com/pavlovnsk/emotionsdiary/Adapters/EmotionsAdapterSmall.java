package com.pavlovnsk.emotionsdiary.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pavlovnsk.emotionsdiary.Data.DataBaseHelper;
import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;
import com.pavlovnsk.emotionsdiary.R;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

public class EmotionsAdapterSmall extends RecyclerView.Adapter<EmotionViewHolderSmall> implements ItemTouchHelperAdapter {

    private EmotionsListPresenterSmall presenterSmall;
    private ArrayList<EmotionItem> emotions;
    @Inject
    DataBaseHelper db;
    @Inject
    Context context;

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

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(emotions, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(emotions, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        db.deleteEmotionItem(emotions.get(position).getEmotionId());
        Toast.makeText(context, String.valueOf(emotions.get(position).getEmotionId()), Toast.LENGTH_SHORT).show();

        emotions.remove(position);
        notifyItemRemoved(position);

    }
}
