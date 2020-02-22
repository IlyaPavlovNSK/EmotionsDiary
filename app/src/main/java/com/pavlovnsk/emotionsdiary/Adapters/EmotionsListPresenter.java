package com.pavlovnsk.emotionsdiary.Adapters;

import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;

import java.util.ArrayList;

import javax.inject.Inject;

public class EmotionsListPresenter {

    private ArrayList<EmotionItem> emotions;

    @Inject
    EmotionsListPresenter(ArrayList<EmotionItem> emotions) {
        this.emotions = emotions;
    }

    void onBindEmotionsRowViewAtPosition(int position, EmotionRowView rowView) {
        EmotionItem emotionItem = emotions.get(position);
        rowView.setEmotionLevel(emotionItem.getEmotionLevel());
        rowView.setEmotionName(emotionItem.getEmotionName());
        rowView.setDescription(emotionItem.getDescription());
        rowView.setPictureBitmap(emotionItem.getEmotionPic());
    }

    int getEmotionsRowsCount() {
        return emotions.size();
    }
}
