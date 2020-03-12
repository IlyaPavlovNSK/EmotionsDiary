package com.pavlovnsk.emotionsdiary.Adapters.EmotionListSmall;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pavlovnsk.emotionsdiary.Room.AppDataBase6;
import com.pavlovnsk.emotionsdiary.Room.EmotionForItem;
import com.pavlovnsk.emotionsdiary.R;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class EmotionsAdapterSmall extends RecyclerView.Adapter<EmotionViewHolderSmall> implements ItemTouchHelperAdapter {

    private EmotionsListPresenterSmall presenterSmall;
    private List<EmotionForItem> emotions;
    private EmotionForItem deletedItem;
    private int keyPosition;

    @Inject
    AppDataBase6 db;

    @Inject
    EmotionsAdapterSmall(EmotionsListPresenterSmall emotionsListPresenterSmall) {
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
    public void onItemDismissLeft(int position) {
        deletedItem = emotions.get(position);
        keyPosition = position;
        emotions.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemDismissRight(int position) {
    }

    @Override
    public void returnItem(int position){
        db.emotionForItemDao().addEmotionItem(deletedItem);
        emotions.add(keyPosition, deletedItem);
        notifyDataSetChanged();
    }

    @Override
    public void removeItem() {
        db.emotionForItemDao().deleteEmotionItem(deletedItem);
    }
}
