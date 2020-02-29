package com.pavlovnsk.emotionsdiary.Adapters.EmotionHistoryList;

import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;


public class EmotionHistoryPresenter {

    @Inject
    @Named("all")
    ArrayList<EmotionItem> emotions;

    @Inject
    public EmotionHistoryPresenter(ArrayList<EmotionItem> emotions) {
        this.emotions = emotions;
    }

    void onBindEmotionsRowViewAtPosition(int position, EmotionHistoryRowView rowView) {
        EmotionItem emotionItem = emotions.get(position);
        rowView.setEmotionData(emotionItem.getDate().substring(0,emotionItem.getDate().length()-6).trim());
        rowView.setEmotionTime(emotionItem.getDate().substring(10).trim());
        rowView.setEmotionName(emotionItem.getEmotionName());
        rowView.setDescription(emotionItem.getDescription());
    }

    int getEmotionsRowsCount() {
        return emotions.size();
    }
}
