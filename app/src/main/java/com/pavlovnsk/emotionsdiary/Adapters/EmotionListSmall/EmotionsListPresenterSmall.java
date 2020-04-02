package com.pavlovnsk.emotionsdiary.Adapters.EmotionListSmall;

import com.pavlovnsk.emotionsdiary.Room.EmotionForItem;
import java.util.List;

public class EmotionsListPresenterSmall {

    private List<EmotionForItem> emotions;

    public EmotionsListPresenterSmall(List<EmotionForItem> emotions) {
        this.emotions = emotions;
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
}
