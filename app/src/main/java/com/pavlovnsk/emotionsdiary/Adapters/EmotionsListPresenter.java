package com.pavlovnsk.emotionsdiary.Adapters;

import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;

import java.util.ArrayList;

public class EmotionsListPresenter {

    private ArrayList<EmotionItem> emotions;

    public EmotionsListPresenter(ArrayList<EmotionItem> emotions) {
        this.emotions = emotions;
    }

    public void onBindEmotionsRowViewAtPosition(int position, EmotionRowView rowView) {
        EmotionItem emotionItem = emotions.get(position);
        rowView.setEmotionLevel(emotionItem.getEmotionLevel());
        rowView.setEmotionName(emotionItem.getEmotionName());
        rowView.setPictureId(emotionItem.getEmotionPic());
    }

    public int getEmotionsRowsCount() {
        return emotions.size();
    }
}
