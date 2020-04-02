package com.pavlovnsk.emotionsdiary.Adapters.EmotionListSmall;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pavlovnsk.emotionsdiary.Room.AppRoomDataBase;
import com.pavlovnsk.emotionsdiary.Room.EmotionForItem;
import com.pavlovnsk.emotionsdiary.R;

import java.util.Collections;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EmotionsAdapterSmall extends RecyclerView.Adapter<EmotionViewHolderSmall> implements ItemTouchHelperAdapter {

    private EmotionsListPresenterSmall presenterSmall;
    private List<EmotionForItem> emotions;
    private EmotionForItem deletedItem;
    private int keyPosition;
    private AppRoomDataBase db;

    public EmotionsAdapterSmall(EmotionsListPresenterSmall emotionsListPresenterSmall, List<EmotionForItem> emotions, AppRoomDataBase db) {
        this.presenterSmall = emotionsListPresenterSmall;
        this.emotions = emotions;
        this.db = db;
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
    public void returnItem(int position) {
        Completable.fromAction(() -> db.emotionForItemDao().addEmotionItem(deletedItem))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        emotions.add(keyPosition, deletedItem);
        //db.close();
        notifyDataSetChanged();
    }

    @Override
    public void removeItem() {
        Completable.fromAction(() -> db.emotionForItemDao().deleteEmotionItem(deletedItem))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        notifyDataSetChanged();
        //db.close();
    }
}
