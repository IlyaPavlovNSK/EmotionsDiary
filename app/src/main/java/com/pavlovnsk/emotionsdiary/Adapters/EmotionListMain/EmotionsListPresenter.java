package com.pavlovnsk.emotionsdiary.Adapters.EmotionListMain;
import com.pavlovnsk.emotionsdiary.Room.AppDataBase6;
import com.pavlovnsk.emotionsdiary.Room.EmotionForItem;
import java.util.List;

import javax.inject.Inject;

public class EmotionsListPresenter {

    private List<EmotionForItem> emotions;

    @Inject
    EmotionsListPresenter(AppDataBase6 db) {
        emotions = db.emotionForItemDao().getEmotionsItem();
    }

    void onBindEmotionsRowViewAtPosition(int position, EmotionRowView rowView) {
        EmotionForItem emotionItem = emotions.get(position);
        rowView.setEmotionLevel(emotionItem.getEmotionLevel());
        rowView.setEmotionName(emotionItem.getEmotionName());
        rowView.setDescription(emotionItem.getDescription());
        rowView.setPictureBitmap(emotionItem.getEmotionPic());
    }

    int getEmotionsRowsCount() {
        return emotions.size();
    }
}
