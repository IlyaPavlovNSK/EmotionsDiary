package com.pavlovnsk.emotionsdiary.Adapters.EmotionListMain;

import com.pavlovnsk.emotionsdiary.Room.EmotionForItem;

import java.util.List;

public class EmotionsListPresenter {

    private List<EmotionForItem> emotions;

    public EmotionsListPresenter(List<EmotionForItem> emotions) {
        this.emotions = emotions;
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
