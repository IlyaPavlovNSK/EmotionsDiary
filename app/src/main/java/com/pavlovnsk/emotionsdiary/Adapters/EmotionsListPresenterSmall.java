package com.pavlovnsk.emotionsdiary.Adapters;

import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;

import java.util.ArrayList;

import javax.inject.Inject;

public class EmotionsListPresenterSmall {

    private ArrayList<EmotionItem> emotions;

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
}
