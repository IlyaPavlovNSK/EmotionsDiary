package com.pavlovnsk.emotionsdiary.Adapters.EmotionListSmall;

import com.pavlovnsk.emotionsdiary.Room.AppDataBase6;
import com.pavlovnsk.emotionsdiary.Room.EmotionForItem;

import java.util.List;

import javax.inject.Inject;

public class EmotionsListPresenterSmall {

    private List<EmotionForItem> emotions;

    @Inject
    public EmotionsListPresenterSmall(AppDataBase6 db) {
        emotions = db.emotionForItemDao().getEmotionsItem();
    }

    void onBindEmotionsRowViewAtPosition(int position, EmotionRowViewSmall rowViewSmall) {
        EmotionForItem emotionItem = emotions.get(position);
        rowViewSmall.setEmotionId(emotionItem.getEmotionId());
        rowViewSmall.setEmotionName(emotionItem.getEmotionName());
        rowViewSmall.setDescription(emotionItem.getDescription());
    }

    int getEmotionsRowsCount() {
        return emotions.size();
    }

    List<EmotionForItem> getEmotions() {
        return emotions;
    }
}
