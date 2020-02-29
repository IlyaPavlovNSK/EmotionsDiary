package com.pavlovnsk.emotionsdiary.Adapters.EmotionListSmall;

import com.pavlovnsk.emotionsdiary.Data.DataBaseHelper;
import com.pavlovnsk.emotionsdiary.ListEmotionsItemActivity;
import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;
import javax.inject.Named;

public class EmotionsListPresenterSmall implements ItemTouchHelperAdapter{

    @Inject
    @Named("item")
    ArrayList<EmotionItem> emotions;

    ListEmotionsItemActivity view = new ListEmotionsItemActivity();

    private EmotionsAdapterSmall emotionsAdapterSmall = new EmotionsAdapterSmall(this);

    @Inject DataBaseHelper db;

    @Inject
    public EmotionsListPresenterSmall(ArrayList<EmotionItem> emotions) {
        this.emotions = emotions;
    }

    void onBindEmotionsRowViewAtPosition(int position, EmotionRowViewSmall rowViewSmall) {
        EmotionItem emotionItem = emotions.get(position);
        rowViewSmall.setEmotionId(emotionItem.getEmotionId());
        rowViewSmall.setEmotionName(emotionItem.getEmotionName());
        rowViewSmall.setDescription(emotionItem.getDescription());
    }

    int getEmotionsRowsCount() {
        return emotions.size();
    }

    public ArrayList<EmotionItem> getEmotions() {
        return emotions;
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

        emotionsAdapterSmall.notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismissLeft(int position) {
        view.showSnackBar(emotions.get(position), position);
        db.deleteEmotionItem(emotions.get(position).getEmotionId());

        emotions.remove(position);

        emotionsAdapterSmall.notifyItemRemoved(position);
    }

    @Override
    public void onItemDismissRight(int position) {
    }

    public void returnItem(EmotionItem item, int position){
        db.addEmotionItem(item);
        emotionsAdapterSmall.notifyItemInserted(position);
    }
}
